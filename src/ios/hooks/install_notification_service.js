const xcode = require("xcode");
const fs = require("fs");
module.exports = function (context) {

    const cordovaCommon = context.requireCordovaModule('cordova-common');
    const appConfig = new cordovaCommon.ConfigParser('config.xml');
    const appName = appConfig.name();
    const appBundleId = `${appConfig.doc._root.attrib.id}`;
    const iosPath = 'platforms/ios/';
    const projPath = `${iosPath}${appName}.xcodeproj/project.pbxproj`;
    const extName = 'CooeeNotificationServiceExtension';
    const extFiles = [
        'NotificationService.h',
        'NotificationService.m',
        `${extName}-Info.plist`,
        `${extName}.entitlements`,
    ];
    const sourceDir = `plugins/@letscooee/cordova-plugin/src/extensions/SupportFiles/`;
    const podEnd = '\nend';
    const podfileContent = `\n\n\ttarget \'${extName}\' do\n\t\tinherit! :search_paths\n\tend${podEnd}`;
    const argumentArray = process.argv;

    /**
     * Finds COOEE_APP_ID at process.argv.
     * Works while adding plugin to project.
     *
     * @returns {string|*}
     */
    function getAppIDFromCMD() {
        argumentArray.forEach(function (arg) {
            if (arg.startsWith("COOEE_APP_ID")) {
                return arg.split("=")[1];
            }
        });

        return null;
    }

    /**
     * Finds COOEE_APP_ID at package.json.
     * Works when plugin is installed and platform is getting add.
     *
     * @returns {string|*}
     */
    function getAppIDFromPackageJSON() {
        let json = JSON.parse(fs.readFileSync('package.json', 'utf8'))
        return json.cordova.plugins['@letscooee/cordova-plugin']?.COOEE_APP_ID;
    }

    /******************* Fetch COOEE_APP_ID *******************/
    let appId = getAppIDFromCMD() || getAppIDFromPackageJSON();
    if (!appId) {
        throw new cordovaCommon.CordovaError('Fail to find COOEE_APP_ID. Stopping the build.');
    }

    console.log('Found COOEE_APP_ID: ' + appId);

    console.log(`Adding ${extName} notification extension to ${appName}`);
    let proj = xcode.project(projPath);

    /**
     * Replace COOEE_APP_ID in the given file
     *
     * @param appId the app id to replace
     * @param path the path to the file in which to replace the app id
     */
    function updateSource(appId, path) {
        const fileContent = fs.readFileSync(path, 'utf8');
        const updatedFileContent = fileContent.replace('UPDATE_APP_ID', appId);
        fs.writeFileSync(path, updatedFileContent, 'utf8');

    }

    /**
     * Access xCode project and add the extension files to the project
     */
    proj.parse(function (err) {
        if (err) {
            console.log(`Error parsing iOS project: ${err}`);
        }

        console.log('Copying in the extension files to the iOS project');
        fs.existsSync(`${iosPath}${extName}`) || fs.mkdirSync(`${iosPath}${extName}`);
        extFiles.forEach(function (extFile) {
            if (extFile === `${extName}.entitlements` || extFile === `${extName}-Info.plist`) {
                updateSource(appId, `${sourceDir}${extFile}`);
            }
            let targetFile = `${iosPath}${extName}/${extFile}`;
            fs.createReadStream(`${sourceDir}${extFile}`)
                .pipe(fs.createWriteStream(targetFile));

        });

        /**
         * Create new PBXGroup for the extension
         */
        console.log('Creating new PBXGroup for the extension');
        const key = proj.findPBXGroupKey({path: `${extName}`});
        let extGroup = proj.getPBXGroupByKey(key) || proj.addPbxGroup(extFiles, extName, extName);

        /**
         * Add the new PBXGroup to the CustomTemplate group. This makes the
         * files appear in the file explorer in Xcode.
         */
        console.log('Adding new PBXGroup to CustomTemplate PBXGroup');
        if (!key) {
            let groups = proj.hash.project.objects['PBXGroup'];
            Object.keys(groups).forEach(function (key) {
                if (groups[key].name === 'CustomTemplate') {
                    proj.addToPbxGroup(extGroup.uuid, key);
                }
            });
        }

        /**
         * Add a target for the extension
         */
        console.log('Adding the new target');
        const targetKey = proj.findTargetKey(extName)
        if (targetKey) {
            console.log(`Target ${extName} already exists. Aborting Add target`);
            return;
        }

        let target = proj.addTarget(extName, 'app_extension', extName, `${appBundleId}.${extName}`);
        /**
         * Add build phases to the new target
         */
        console.log('Adding build phases to the new target');
        proj.addBuildPhase(['NotificationService.m'], 'PBXSourcesBuildPhase', 'Sources', target.uuid);
        proj.addBuildPhase([], 'PBXResourcesBuildPhase', 'Resources', target.uuid);
        proj.addBuildPhase([], 'PBXFrameworksBuildPhase', 'Frameworks', target.uuid);

        const pushEntitlement = "{com.apple.Push = {enabled = 1;};}";
        proj.addTargetAttribute("SystemCapabilities", pushEntitlement);

        console.log('Write the changes to the iOS project file');
        fs.writeFileSync(projPath, proj.writeSync());
        console.log(`Added ${extName} notification extension to project`);


    });

    /************** Updating Podfile *********************/
    const currentPodFieContent = fs.readFileSync(`${iosPath}/Podfile`, 'utf8');
    const newPodFile = currentPodFieContent.replace(podEnd, podfileContent);
    fs.writeFileSync(`${iosPath}/Podfile`, newPodFile, 'utf8');

    /************** Running terminal command 'pod install' *********************/
    const exec = require('child_process').exec;
    exec(`cd ${iosPath} && pod install`)

};
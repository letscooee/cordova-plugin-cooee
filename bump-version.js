#!/usr/bin/env node

const fs = require('fs');
const version = require('./package.json').version;

let data = fs.readFileSync('./plugin.xml', "utf8");
data = data.replace(/version="[^"]+"/, `version="${version}"`);
fs.writeFileSync('./plugin.xml', data);

const newVersionCode = parseInt(version.split('.').map(v => v.padStart(2, '0')).join(''));

bumpVersion('./src/android/Constants.java')
bumpVersion('./src/ios/Constants.m')

function bumpVersion(path) {
    let fileData = fs.readFileSync(path, "utf8");

    if (path.includes('android')) {
        fileData = fileData.replace(/VERSION_NAME = "[^"]+"/, `VERSION_NAME = "${version}"`);
    } else {
        fileData = fileData.replace(/VERSION_NAME = @"[^"]+"/, `VERSION_NAME = @"${version}"`);
    }

    fileData = fileData.replace(/VERSION_CODE = [^;]+/, `VERSION_CODE = ${newVersionCode}`);
    fs.writeFileSync(path, fileData);
}

#!/usr/bin/env node

const fs = require('fs');
const version = require('./package.json').version;

let data = fs.readFileSync('./plugin.xml', "utf8");
data = data.replace(/version="[^"]+"/, `version="${version}"`);
fs.writeFileSync('./plugin.xml', data);

bumpVersion('./src/android/Constants.java')
bumpVersion('./src/ios/Constants.swift')

function bumpVersion(path){
    let fileData = fs.readFileSync(path, "utf8");
    fileData = fileData.replace(/CORDOVA_PLUGIN_VERSION = "[^"]+"/, `CORDOVA_PLUGIN_VERSION = "${version}"`);
    fs.writeFileSync(path, fileData);
}

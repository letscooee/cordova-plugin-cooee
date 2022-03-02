#!/usr/bin/env node

const fs = require('fs');
const version = require('./package.json').version;

bumpVersion('./plugin.xml')
bumpVersion('./src/android/Constants.java')
bumpVersion('./src/ios/Constants.swift')

function bumpVersion(path){
    let fileData = fs.readFileSync(path, "utf8");
    fileData = fileData.replace(/version = "[^"]+"/, `version = "${version}"`);
    fs.writeFileSync(path, fileData);
}

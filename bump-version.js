#!/usr/bin/env node

const fs = require('fs');
const version = require('./package.json').version;

let data = fs.readFileSync('./plugin.xml', "utf8");
data = data.replace(/version="[^"]+"/, `version="${version}"`);
fs.writeFileSync('./plugin.xml', data);

let javaFileData = fs.readFileSync('./src/android/Constants.java', "utf8");
javaFileData = javaFileData.replace(/version = "[^"]+"/, `version = "${version}"`);
fs.writeFileSync('./src/android/Constants.java', javaFileData);

let swiftFileData = fs.readFileSync('./src/ios/Constants.swift', "utf8");
swiftFileData = swiftFileData.replace(/version = "[^"]+"/, `version = "${version}"`);
fs.writeFileSync('./src/ios/Constants.swift', swiftFileData);

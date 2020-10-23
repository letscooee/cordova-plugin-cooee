# Cordova Plugin for Cooee SDK

[![npm version](https://badge.fury.io/js/cordova-plugin-cooee.svg)](https://badge.fury.io/js/cordova-plugin-ziggeo)

## Platforms

- Android (Minimum Android 5.5/API level 21)
- iOS (Coming Soon)

## Installation

```shell script
cordova plugin add cordova-plugin-cooee
```

## Usage
   
1. Logging events

   This method would send custom events to the server. It takes two parameter `eventName` and `eventProperties` 

   ```js
   cordova.plugins.CooeeSdkPlugin.sendEvent(<string> eventName, <map> eventProperties, success, failure);
   ```

2. Setting user properties

   This method would update custom user properties and send it to server. It takes one parameter `userProperties`.

   ```js
   cordova.plugins.CooeeSdkPlugin.updateUserProperties(<map> userProperties, success, failure)
   ```

3. Setting user data

   This method would update custom user data and send it to server. It takes one parameter `userData`.

   ```js
   cordova.plugins.CooeeSdkPlugin.updateUserData(<map> userData, success, failure)
   ```

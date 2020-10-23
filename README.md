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

1. `init()`

   This initialises the plugin. This should be the first method to be called to use the plugin.

   ```js
   cordova.plugins.CooeeSdkPlugin.init(success, failure);
   ```
   
2. `sendEvent()`

   This method would send custom events to the server. It takes two parameter `eventName` and `eventProperties` 

   ```js
   cordova.plugins.CooeeSdkPlugin.sendEvent(<string> eventName, <map> eventProperties, success, failure);
   ```

3. `updateUserProperties()`

   This method would update custom user properties and send it to server. It takes one parameter `userProperties`.

   ```js
   cordova.plugins.CooeeSdkPlugin.updateUserProperties(<map> userProperties, success, failure)
   ```

4. `updateUserData()`

   This method would update custom user data and send it to server. It takes one parameter `userData`.

   ```js
   cordova.plugins.CooeeSdkPlugin.updateUserData(<map> userData, success, failure)
   ```

5. `updateUserProfile()`

   This method would update custom user data and user properties and send it to server. It takes two parameter `userData` and`userProperties`.

   ```js
   cordova.plugins.CooeeSdkPlugin.updateUserProfile(<map> userData,<map> userProperties, success, failure)
   ```

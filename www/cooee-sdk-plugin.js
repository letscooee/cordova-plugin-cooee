const exec = require("cordova/exec");

// Send events to the server
exports.sendEvent = function (eventName, eventProperties, success, error) {
    //formatting changes
    exec(success, error, "CooeeSdkPlugin", "sendEvent", [
        eventName,
        eventProperties,
    ]);
};

// Send user data to the server
exports.updateUserData = function (userData, success, error) {
    exec(success, error, "CooeeSdkPlugin", "updateUserData", [userData]);
};

// Send user properties to the server
exports.updateUserProperties = function (properties, success, error) {
    //formatting changes
    exec(success, error, "CooeeSdkPlugin", "updateUserProperties", [
        properties,
    ]);
};

// Send screen name provided by user to the server
exports.setCurrentScreen = function (screenName, success, error) {
    exec(success, error, "CooeeSdkPlugin", "setCurrentScreen", [screenName]);
};

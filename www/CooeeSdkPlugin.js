const exec = require("cordova/exec");

// Initialize the Cooee instance
exports.init = function (success, error) {
    exec(success, error, "CooeeSdkPlugin", "init", []);
};

// Send events to the server
exports.sendEvent = function (eventName, eventProperties, success, error) {
    exec(success, error, "CooeeSdkPlugin", "sendEvent", [eventName, eventProperties]);
};

// Send user data to the server
exports.updateUserData = function (userData, success, error) {
    exec(success, error, "CooeeSdkPlugin", "updateUserData", [userData]);
};

// Send user properties to the server
exports.updateUserProperties = function (properties, success, error) {
    exec(success, error, "CooeeSdkPlugin", "updateUserProperties", [properties]);
};

// Send user data and user properties to the server
exports.updateUserProfile = function (arg0, arg1, success, error) {
    exec(success, error, "CooeeSdkPlugin", "updateUserProfile", [arg0, arg1]);
};

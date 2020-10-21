var exec = require("cordova/exec");

exports.coolMethod = function (arg0, success, error) {
  exec(success, error, "CooeeSdkPlugin", "coolMethod", [arg0]);
};

exports.init = function (success, error) {
  exec(success, error, "CooeeSdkPlugin", "init", []);
};

exports.sendEvent = function (arg0, arg1, success, error) {
  exec(success, error, "CooeeSdkPlugin", "sendEvent", [arg0, arg1]);
};

exports.updateUserData = function (arg0, success, error) {
  exec(success, error, "CooeeSdkPlugin", "updateUserData", [arg0]);
};

exports.updateUserProperties = function (arg0, success, error) {
  exec(success, error, "CooeeSdkPlugin", "updateUserProperties", [arg0]);
};

exports.updateUserProfile = function (arg0, arg1, success, error) {
  exec(success, error, "CooeeSdkPlugin", "updateUserProfile", [arg0, arg1]);
};

var exec = require("cordova/exec");

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, "CooeeSdkPlugin", "coolMethod", [arg0]);
};

exports.init = function (success, error) {
    checkPermessions();
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

checkPermessions = function () {
    var permissions = cordova.plugins.permissions;
    var perm = [
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION",
    ];

    permissions.checkPermission(
        "android.permission.ACCESS_COARSE_LOCATION",
        function (status) {
            if (!status.hasPermission) {
                permissions.requestPermissions(
                    perm,
                    function (status) {
                        console.log("Permission Granted");
                    },
                    function (err) {
                        console.log("Permission Denied");
                    }
                );
            }
        },
        function (err) {
            console.log(err);
        }
    );
};

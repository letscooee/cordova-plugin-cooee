const exec = require("cordova/exec");

// Initialize the Cooee instance
exports.init = function (success, error) {
    checkPermissions();
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

// Check if the application has required permissions
checkPermissions = function () {
    const permissionsPlugin = cordova.plugins.permissions;
    const perm = [
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION"
    ];

    permissionsPlugin.checkPermission(
        "android.permission.ACCESS_COARSE_LOCATION",
        function (status) {
            if (!status.hasPermission) {
                permissionsPlugin.requestPermissions(
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

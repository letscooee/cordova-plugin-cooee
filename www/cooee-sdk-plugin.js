const exec = require("cordova/exec");

/**
 * Sends custom events to the server and returns with the campaign details(if any)
 * @param eventName Name of the event
 * @param eventProperties Properties of the event (Optional)
 * @param success Success callback
 * @param error Error callback
 */
exports.sendEvent = function (eventName, eventProperties, success, error) {
    exec(success, error, "CooeeSdkPlugin", "sendEvent", [
        eventName,
        eventProperties,
    ]);
};

/**
 * Send the given user data and user properties to the server.
 * @param userProfile User profile object
 * @param success Success callback
 * @param error Error callback
 */
exports.updateUserProfile = function (userProfile, success, error) {
    exec(success, error, "CooeeSdkPlugin", "updateUserProfile", [userProfile]);
};

/**
 * Send given user data to the server
 * @param userData User data to be sent
 * @param success Success callback
 * @param error Error callback
 * @deprecated Use updateUserProfile instead
 */
exports.updateUserData = function (userData, success, error) {
    exports.updateUserProfile(userData, success, error);
};

/**
 * Send given user properties to the server
 * @param properties User properties to be sent
 * @param success Success callback
 * @param error Error callback
 * @deprecated Use updateUserProfile instead
 */
exports.updateUserProperties = function (properties, success, error) {
    exports.updateUserProfile(properties, success, error);
};

/**
 * Send screen name provided by user to the server
 * @param screenName Screen name to be sent
 * @param success Success callback
 * @param error Error callback
 */
exports.setCurrentScreen = function (screenName, success, error) {
    exec(success, error, "CooeeSdkPlugin", "setCurrentScreen", [screenName]);
};

/**
 * Provides UserID assign by Cooee to the user
 * @param success Success callback
 * @param error Error callback
 */
exports.getUserID = function (success, error) {
    exec(success, error, "CooeeSdkPlugin", "getUserID");
};

/**
 * Launch Debug info screen from the SDK. This can be use for debug purposes.
 * <p>This screen holds user and device information.</p>
 * <p>This Screen is password protected and can be access only with Cooee representative.</p>
 *
 * <p><b>Note:This screen should not be directly accessible to the end user.</b></p>
 * @param success Success callback
 * @param error Error callback
 */
exports.showDebugInfo = function (success, error) {
    exec(success, error, "CooeeSdkPlugin", "showDebugInfo");
};

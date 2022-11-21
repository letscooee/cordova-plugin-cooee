import CooeeSDK
import Foundation

class CooeeSDKPlugin: CDVPlugin {

    var cooeeSDK: CooeeSDK?

    override func pluginInitialize() {
        super.pluginInitialize()
        self.commandDelegate.run(inBackground: {
            AppController.configure()
            self.cooeeSDK = CooeeSDK.getInstance()
            self.cooeeSDK?.setOnCTADelegate(self)
        })
    }

    @objc(sendEvent:)
    func sendEvent(command: CDVInvokedUrlCommand) {
        self.commandDelegate.run(inBackground: {
            guard let eventName = command.argument(at: 0) as? String else {
                self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Event name can't be empty"), callbackId: command.callbackId)
                return
            }

            let eventProperties = command.argument(at: 1) as? [String: Any]
            do {
                if let eventProperties = eventProperties {
                    try self.cooeeSDK?.sendEvent(eventName: eventName, eventProperties: eventProperties)
                } else {
                    try self.cooeeSDK?.sendEvent(eventName: eventName)
                }
                self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "Event sent"), callbackId: command.callbackId)
            } catch {
                self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Fail to update user profile: \(error)"), callbackId: command.callbackId)
            }
        })
    }

    @objc(updateUserProfile:)
    func updateUserProfile(command: CDVInvokedUrlCommand) {
        self.commandDelegate.run(inBackground: {
            guard let userData = command.argument(at: 0) as? [String: Any] else {
                self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "User profile data can't be empty"), callbackId: command.callbackId)
                return
            }

            do {
                try self.cooeeSDK?.updateUserProfile(userData)
                self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "User profile updated"), callbackId: command.callbackId)
            } catch {
                self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Fail to update user profile: \(error)"), callbackId: command.callbackId)
            }
        })
    }

    @objc(setCurrentScreen:)
    func setCurrentScreen(command: CDVInvokedUrlCommand) {
        guard let screenName = command.argument(at: 0) as? String else {
            self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Screen name can't be empty"), callbackId: command.callbackId)
            return
        }

        self.cooeeSDK?.setCurrentScreen(screenName: screenName)
        self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "Screen name updated"), callbackId: command.callbackId)
    }

    @objc(getUserID:)
    func getUserID(command: CDVInvokedUrlCommand) {
        if let userId = self.cooeeSDK?.getUserID() {
            self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_OK, messageAs: userId), callbackId: command.callbackId)
        } else {
            self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Fail to get Used ID"), callbackId: command.callbackId)
        }
    }

    @objc(showDebugInfo:)
    func showDebugInfo(command: CDVInvokedUrlCommand) {
            self.cooeeSDK?.showDebugInfo()
            self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "Displaying Debug Info"), callbackId: command.callbackId)
    }
}

extension CooeeSDKPlugin: CooeeCTADelegate {
    func onCTAResponse(payload: [String: Any]) {
        let jsonString = self._dict(toJson: payload)

        if let jsonString = jsonString {
            let js = "javascript:cordova.fireDocumentEvent('onCooeeCTAListener', \(jsonString));"
            commandDelegate.evalJs(js)
        }
    }

    func _dict(toJson dict: [String: Any]?) -> String? {
        var jsonData: Data?

        do {
            if let dict = dict {
                jsonData = try JSONSerialization.data(withJSONObject: dict, options: [])
            }
        } catch {
        }

        if let jsonData = jsonData {
            return String(data: jsonData, encoding: .utf8)
        }
        return nil
    }
}

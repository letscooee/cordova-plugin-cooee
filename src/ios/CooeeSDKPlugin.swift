import Foundation
import CooeeSDK

class CooeeSDKPlugin: CDVPlugin {

var cooeesdk: CooeeSDK?
override func pluginInitialize() {
    super.pluginInitialize()
    AppController.configure()
    DispatchQueue.main.async {
       self.cooeesdk = CooeeSDK.getInstance()
       self.cooeesdk?.setWrapper("cordova")
    }
}

@objc(sendEvent:)
func sendEvent(command: CDVInvokedUrlCommand) {
    let eventName = command.argument(at:0) as! String
    let eventProperties = command.argument(at:1) as? [String:Any]
    do{
        if let eventProperties = eventProperties {
			try cooeesdk?.sendEvent(eventName: eventName, eventProperties: eventProperties)
		} else {
			try cooeesdk?.sendEvent(eventName: eventName)
		}
    }catch{}
    //self.commandDelegate!.sendPluginResult("Event Sent");
}

@objc(updateUserProfile:)
func updateUserProfile(command: CDVInvokedUrlCommand) {
    let userData = command.argument(at:0) as! [String:Any]
    do{
        try self.cooeesdk?.updateUserProfile(userData)
    }catch{}
}

@objc(setCurrentScreen:)
func setCurrentScreen(command: CDVInvokedUrlCommand) {
   let screenName = command.argument(at:0) as! String
  self.cooeesdk?.setCurrentScreen(screenName: screenName)
}

    @objc(getUserID:)
    func getUserID(command: CDVInvokedUrlCommand) {
        if let userId = self.cooeeSDK?.getUserID() {
            self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_OK, messageAs: userId), callbackId: command.callbackId)
        } else {
            self.commandDelegate.send(CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Fail to get Used ID"), callbackId: command.callbackId)
        }
    }
}

extension CooeeSDKPlugin: CooeeCTADelegate{
    func onCTAResponse(payload: [String : Any]) {

    let jsonString = _dict(toJson: payload)

    if let jsonString = jsonString {
        let js = "javascript:cordova.fireDocumentEvent('onCooeeCTAListener', \(jsonString));"
        commandDelegate.evalJs(js)
    }
}

func _dict(toJson dict: [String : Any]?) -> String? {

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


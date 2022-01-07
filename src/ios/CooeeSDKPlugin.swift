import Foundation
import CooeeSDK

class CooeeSDKPlugin: CDVPlugin {

var cooeesdk: CooeeSDK?
override func pluginInitialize() {
    super.pluginInitialize()
    AppController.configure()
    cooeesdk = CooeeSDK.getInstance()
}

@objc(sendEvent:)
func sendEvent(command: CDVInvokedUrlCommand) {
    let eventName = command.argument(at:0) as! String
    let eventProperties = command.argument(at:1) as! [String:Any]
    do{
        try self.cooeesdk?.sendEvent(eventName: eventName,eventProperties: eventProperties);
    }catch{}
    //self.commandDelegate!.sendPluginResult("Event Sent");
}

@objc(updateUserData:)
func updateUserData(command: CDVInvokedUrlCommand) {
    let userData = command.argument(at:0) as! [String:Any]
    self.cooeesdk?.updateUserData(userData: userData)
}

@objc(updateUserProperties:)
func updateUserProperties(command: CDVInvokedUrlCommand) {
  let userData = command.argument(at:0) as! [String:Any]
    self.cooeesdk?.updateUserProperties(userProperties: userData);
    //commandDelegate!.sendPluginResult("User Data Updated");
}

@objc(setCurrentScreen:)
func setCurrentScreen(command: CDVInvokedUrlCommand) {
   let screenName = command.argument(at:0) as! String
  self.cooeesdk?.setCurrentScreen(screenName: screenName)
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


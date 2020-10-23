package org.letscooee.cooeesdk;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.io.*;

import com.letscooee.cooeesdk.CooeeSDK;
import android.content.Context;

/**
 * Main wrapper for Cooee Android SDK.
 *
 * @author Abhishek Taparia
 */
public class CooeeSdkPlugin extends CordovaPlugin {

    private CooeeSDK cooeesdk;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("init")) {
            try {
                this.cooeesdk = CooeeSDK.getDefaultInstance(this.cordova.getActivity().getApplicationContext());
                callbackContext.success("CooeeSDK Initilized");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("sendEvent")) {
            String eventName = args.getString(0);
            Map<String, String> eventProperties = toMap(args.getJSONObject(1));

            try {
                this.cooeesdk.sendEvent(eventName, eventProperties);
                callbackContext.success("Event Sent");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("updateUserData")) {
            try {
                Map<String, String> userData = toMap(args.getJSONObject(0));
                this.cooeesdk.updateUserData(userData);
                callbackContext.success("User Data Updated");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("updateUserProperties")) {
            try {
                Map<String, String> userProperties = toMap(args.getJSONObject(0));
                this.cooeesdk.updateUserProperties(userProperties);
                callbackContext.success("User Properties Updated");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("updateUserProfile")) {
            try {
                Map<String, String> userData = toMap(args.getJSONObject(0));
                Map<String, String> userProperties = toMap(args.getJSONObject(1));
                this.cooeesdk.updateUserProfile(userData, userProperties);
                callbackContext.success("User Profile Updated");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        return false;
    }

    private Map<String, String> toMap(JSONObject jsonobj) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<String> keys = jsonobj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = jsonobj.getString(key);
            map.put(key, value);
        }

        return map;
    }
}

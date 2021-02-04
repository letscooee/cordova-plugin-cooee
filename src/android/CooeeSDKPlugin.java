package org.letscooee.cooeesdk;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.io.*;

import com.letscooee.CooeeSDK;
import com.letscooee.utils.InAppNotificationClickListener;
import android.content.Context;

/**
 * Main wrapper for Cooee Android SDK.
 *
 * @author Abhishek Taparia
 */
public class CooeeSDKPlugin extends CordovaPlugin implements InAppNotificationClickListener {

    private CooeeSDK cooeesdk;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        try {
            this.cooeesdk = CooeeSDK.getDefaultInstance(this.cordova.getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("sendEvent")) {
            try {
                String eventName = args.getString(0);
                Map<String, String> eventProperties = toMap(args.getJSONObject(1));
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

        if (action.equals("setCurrentScreen")) {
            try {
                String screenName = args.getString(0);
                this.cooeesdk.setCurrentScreen(screenName);
                callbackContext.success("Screen Name Updated");
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

    @Override
    public void onInAppButtonClick(HashMap<String, String> payload) {
        JSONObject jsonPayload = new JSONObject(payload);
        final String json = "{'customExtras':" + jsonPayload.toString() + "}";

        webView.getView().post(new Runnable() {
            public void run() {
                webView.loadUrl("javascript:cordova.fireDocumentEvent('onCooeeInAppButtonClick'," + json + ");");
            }
        });
    }
}

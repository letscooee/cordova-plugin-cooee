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
import android.content.Context;
import android.util.Log;
import com.letscooee.utils.InAppNotificationClickListener;

/**
 * Main wrapper for Cooee Android SDK.
 *
 * @author Abhishek Taparia
 */
public class CooeeSDKPlugin extends CordovaPlugin {

    private CooeeSDK cooeesdk;

    private InAppNotificationClickListener listener = new InAppNotificationClickListener() {
        @Override
        public void onInAppButtonClick(HashMap<String, Object> payload) {
            JSONObject jsonPayload = new JSONObject(payload);
            final String json = "{'customExtras':" + jsonPayload.toString() + "}";

            webView.getView().post(new Runnable() {
                public void run() {
                    webView.loadUrl("javascript:cordova.fireDocumentEvent('onCooeeInAppButtonClick'," + json + ");");
                }
            });
        }
    };

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        try {
            this.cooeesdk = CooeeSDK.getDefaultInstance(cordova.getActivity().getApplicationContext());
            this.cooeesdk.setInAppNotificationButtonListener(this.listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("sendEvent")) {
            try {
                String eventName = args.getString(0);
                Map<String, Object> eventProperties = toMap(args.getJSONObject(1));
                this.cooeesdk.sendEvent(eventName, eventProperties);
                callbackContext.success("Event Sent");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("updateUserData")) {
            try {
                Map<String, Object> userData = toMap(args.getJSONObject(0));
                this.cooeesdk.updateUserData(userData);
                callbackContext.success("User Data Updated");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("updateUserProperties")) {
            try {
                Map<String, Object> userProperties = toMap(args.getJSONObject(0));
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

    private Map<String, Object> toMap(JSONObject jsonobj) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();
        Iterator<String> keys = jsonobj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonobj.getString(key);
            map.put(key, value);
        }

        return map;
    }
}

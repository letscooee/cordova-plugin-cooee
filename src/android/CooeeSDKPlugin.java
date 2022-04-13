package com.letscooee;

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
import com.letscooee.utils.CooeeCTAListener;
import com.letscooee.retrofit.APIClient;
import com.google.gson.Gson;
import android.text.TextUtils;

/**
 * Main wrapper for Cooee Android SDK.
 *
 * @author Abhishek Taparia
 */
public class CooeeSDKPlugin extends CordovaPlugin {

    private CooeeSDK cooeesdk;

    private final CooeeCTAListener listener = new CooeeCTAListener() {
        @Override
        public void onResponse(HashMap<String, Object> payload) {
            webView.getView().post(new Runnable() {
                public void run() {
                    webView.loadUrl("javascript:cordova.fireDocumentEvent('onCooeeCTAListener'," + new Gson().toJson(payload) + ");");
                }
            });
        }
    };

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        try {
            this.cooeesdk = CooeeSDK.getDefaultInstance(cordova.getActivity().getApplicationContext());
            this.cooeesdk.setCTAListener(this.listener);
            APIClient.setWrapperName("cordova");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("sendEvent")) {
            try {
                String eventName = args.getString(0);
                JSONObject jsonEventProperties = args.getJSONObject(1);

                if (jsonEventProperties == null) {
                    this.cooeesdk.sendEvent(eventName);
                } else {
                    Map<String, Object> eventProperties = toMap(jsonEventProperties);
                    this.cooeesdk.sendEvent(eventName, eventProperties);
                }

                callbackContext.success("Event sent");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("updateUserProfile")) {
            try {
                JSONObject jsonUserProfile = args.getJSONObject(0);

                if (jsonUserProfile == null) {
                    callbackContext.error("User profile cannot be null");
                    return true;
                }

                Map<String, Object> userProfile = toMap(jsonUserProfile);
                this.cooeesdk.updateUserProfile(userProfile);
                callbackContext.success("User profile updated");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("setCurrentScreen")) {
            try {
                String screenName = args.getString(0);
                this.cooeesdk.setCurrentScreen(screenName);
                callbackContext.success("Screen name updated");
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        if (action.equals("getUserID")) {
            try {
                String userId = this.cooeesdk.getUserID();
                if (TextUtils.isEmpty(userId)) {
                    callbackContext.error("No UserID assigned yet");
                    return true;
                }

                callbackContext.success(userId);
            } catch (Exception e) {
                callbackContext.error(e.toString());
            }

            return true;
        }

        return false;
    }

    private Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
        return new Gson().fromJson(jsonObject.toString(), HashMap.class);
    }
}

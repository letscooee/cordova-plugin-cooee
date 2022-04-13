package com.letscooee.cordova;

import com.letscooee.init.AppController;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.Intent;
import com.letscooee.cordova.utils.Constants;

/**
 * CooeePluginController is instance of {@link com.letscooee.init.AppController} which start with application.
 *
 * @author Ashish Gaikwad
 * @since 1.3.4
 */
public class CooeePluginController extends AppController {

    @Override
    public void onCreate() {
        registerFCMBroadcastListener();
        super.onCreate();
    }

    /**
     * It registers {@link FCMBroadcastListener} with {@link androidx.localbroadcastmanager.content.LocalBroadcastManager}
     * to receive push notification data broadcast by
     * <a href="https://www.npmjs.com/package/cordova-plugin-firebase-messaging">cordova-plugin-firebase-messaging</a>
     * plugin.
     *
     * <br/>
     * This class will be registered in <code>AndroidManifest.xml</code> as <code>android:name</code> attribute.
     *
     * Reason: As one application can have one instance of {@link com.google.firebase.messaging.FirebaseMessagingService}.
     * If any app is using
     * <a href="https://www.npmjs.com/package/cordova-plugin-firebase-messaging">cordova-plugin-firebase-messaging</a>
     * plugin then {@link com.letscooee.services.CooeeFirebaseMessagingService} from Cooee SDK is not registered in
     * <code>AndroidManifest.xml</code> file.
     *
     * Solution: To tackle this problem, we are registering {@link FCMBroadcastListener} with
     * {@link androidx.localbroadcastmanager.content.LocalBroadcastManager}. So that whenever
     * <a href="https://www.npmjs.com/package/cordova-plugin-firebase-messaging">cordova-plugin-firebase-messaging</a>
     * triggers {@link Constants.FCM_MESSAGE_ACTION} broadcast then {@link FCMBroadcastListener} will be called.
     *
     */
    private void registerFCMBroadcastListener() {
        FCMBroadcastListener broadcastReceiver = new FCMBroadcastListener();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constants.FCM_MESSAGE_ACTION));
    }

}
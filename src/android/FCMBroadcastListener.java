package com.letscooee.cordova;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.messaging.RemoteMessage;
import com.letscooee.services.CooeeFirebaseMessagingService;
import com.letscooee.cordova.utils.Constants;

/**
 * FCMBroadcastListener is listen to {@link Constants#FCM_MESSAGE_ACTION} action which is fired by
 * <a href="https://www.npmjs.com/package/cordova-plugin-firebase-messaging">cordova-plugin-firebase-messaging</a> plugin.
 * <p>
 * Once data recieved, It sends it to {@link CooeeFirebaseMessagingService} to process.
 *
 * @author Ashish Gaikwad
 * @since 1.3.4
 */
public class FCMBroadcastListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteMessage remoteMessage = intent.getParcelableExtra(Constants.FCM_MESSAGE_DATA);
        if (remoteMessage == null || remoteMessage.getData().size() == 0) {
            return;
        }

        new CooeeFirebaseMessagingService(context).handleTriggerData(remoteMessage.getData().get("triggerData"));
    }
}
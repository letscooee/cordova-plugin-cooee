package org.letscooee.cooeesdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.messaging.RemoteMessage;
import com.letscooee.services.CooeeFirebaseMessagingService;

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
        System.out.println("FCMBroadcast.onReceive()");
        RemoteMessage remoteMessage = intent.getParcelableExtra(Constants.FCM_MESSAGE_DATA);
        if (remoteMessage == null || remoteMessage.getData().size() == 0) {
            return;
        }
        System.out.println("FCMBroadcast.notEmpty()" + remoteMessage.getData().get("triggerData"));
        new CooeeFirebaseMessagingService(context).handleTriggerData(remoteMessage.getData().get("triggerData"));
    }
}
package org.letscooee.cooeesdk;

import com.letscooee.init.AppController;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.content.Intent;

/**
 * CooeePluginController is instance of {@link com.letscooee.init.AppController} which start with application.
 * It registers {@link androidx.localbroadcastmanager.content.LocalBroadcastManager} to receive push notification data
 * broadcast by
 * <a href="https://www.npmjs.com/package/cordova-plugin-firebase-messaging">cordova-plugin-firebase-messaging</a> plugin
 * and gives control to {@link com.letscooee.init.AppController} for further process.
 * <p>
 * <br/>
 * This class will be registered in <code>AndroidManifest.xml</code> as <code>android:name</code> attribute.
 * <p>
 * Reason to add this class is to know once app is activated in background due to any reason and we can register
 * {@link  FCMBroadcastListener} with {@link androidx.localbroadcastmanager.content.LocalBroadcastManager}.
 *
 * @author Ashish Gaikwad
 * @since 1.3.4
 */
public class CooeePluginController extends AppController {

    @Override
    public void onCreate() {
        FCMBroadcastListener broadcastReceiver = new FCMBroadcastListener();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constatnts.FCM_MESSAGE_ACTION));
        super.onCreate();
    }

}
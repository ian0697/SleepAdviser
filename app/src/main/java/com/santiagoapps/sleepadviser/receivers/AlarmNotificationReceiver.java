package com.santiagoapps.sleepadviser.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.santiagoapps.sleepadviser.R;

/**
 * This receiver handle the Notification
 * builder and alarm notifications
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {
    public final String TAG = "Dormie (" + AlarmNotificationReceiver.class.getSimpleName() + ")" ;

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d(TAG,"Broadcast recieved!");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo_dormie_happy)
                .setContentTitle("Dormie Reminder")
                .setContentText("How are you? It's sleeping time! - update")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info");

        NotificationManager notificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,builder.build());

        //For alarm:
//        MediaPlayer mediaPlayer = MediaPlayer.create(context,
//                Settings.System.DEFAULT_RINGTONE_URI);
//        mediaPlayer.start();


    }
}

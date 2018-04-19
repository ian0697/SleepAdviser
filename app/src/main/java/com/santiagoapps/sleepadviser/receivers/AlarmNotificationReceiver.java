package com.santiagoapps.sleepadviser.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.santiagoapps.sleepadviser.R;
import com.santiagoapps.sleepadviser.activities.NavigationActivity;

/**
 * This receiver handle the Notification
 * builder and alarm notifications
 */
public class AlarmNotificationReceiver extends BroadcastReceiver {
    public final String TAG = "Dormie (" + AlarmNotificationReceiver.class.getSimpleName() + ")" ;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG,"Broadcast recieved!");

        Intent myIntent = new Intent(context, NavigationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                Intent.FLAG_ACTIVITY_NEW_TASK);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo_dormie_happy)
                .setContentTitle("Sleep Reminder")
                .setContentText("How's your day? It's sleeping time!")
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

package com.example.sohaibshahid.journal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Sohaib shahid on 2/6/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification mNotifyBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher_logo)
                .setContentTitle("Daily Log")
                .setContentText("You seem to have forgotten to log in today").setSound(alarmSound)
                .setAutoCancel(true).setWhen(when)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .build();
    }
}

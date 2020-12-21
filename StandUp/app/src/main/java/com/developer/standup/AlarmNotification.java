package com.developer.standup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmNotification {

    private static final String NOTIFICATION_CHANNEL_ID = "ALARM_NOTIFICATION_CHANNEL_ID";
    private static final String NOTIFICATION_CHANNEL_NAME = "ALARM_NOTIFICATION_CHANNEL";
    private static NotificationManagerCompat notificationManagerCompat;

    public static void send(Context context, int notificationId, String s, int icon) {
        notificationManagerCompat = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        //on notification click open the main activity
        Intent intentMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(context, notificationId, intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntentMainActivity);
        //build the notification
        notificationBuilder.setContentTitle(s);
        notificationBuilder.setSmallIcon(icon);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLights(Color.RED, 500, 500);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);

        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }

    public static void cancel() {
        notificationManagerCompat.cancelAll();
    }
}

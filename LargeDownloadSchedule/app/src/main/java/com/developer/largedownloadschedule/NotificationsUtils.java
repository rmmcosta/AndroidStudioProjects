package com.developer.largedownloadschedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationsUtils {
    public static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_1234";
    public static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_DOWNLOAD";
    public static final int NOTIFICATION_ID = 1111;

    public static void sendNotification(String title, String text, Context context) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_new_notification);
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(text);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}

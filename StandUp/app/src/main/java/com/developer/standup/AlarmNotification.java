package com.developer.standup;

import android.app.AlarmManager;
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
    public static final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_TITLE = "EXTRA_NOTIFICATION_TITLE";
    public static final String EXTRA_NOTIFICATION_ICON = "EXTRA_NOTIFICATION_ICON";
    public static final String EXTRA_ALARM_TIME = "EXTRA_ALARM_TIME";

    private static final String NOTIFICATION_CHANNEL_ID = "ALARM_NOTIFICATION_CHANNEL_ID";
    private static final String NOTIFICATION_CHANNEL_NAME = "ALARM_NOTIFICATION_CHANNEL";
    private final NotificationManagerCompat notificationManagerCompat;
    private final NotificationCompat.Builder notificationBuilder;
    private final int notificationId;
    private final Context context;
    private final String alarmTime;

    public AlarmNotification(Context context, int notificationId, String title, int icon, String alarmTime) {
        this.alarmTime = alarmTime;
        this.context = context;
        this.notificationId = notificationId;
        notificationManagerCompat = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        //on notification click open the main activity
        Intent intentMainActivity = new Intent(context, MainActivity.class);
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(context, notificationId, intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntentMainActivity);
        //build the notification
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setSmallIcon(icon);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLights(Color.RED, 500, 500);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);

        //Todo define action to cancel the broadcast for repeated events
    }

    public void defineAction(int icon, CharSequence title, PendingIntent pendingIntent) {
        notificationBuilder.addAction(icon, title, pendingIntent);
    }

    public void send() {
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
    }

    public void sendAndRepeat() {
        send();
        if (alarmTime.isEmpty()) {
            return;
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            long nextTimeInMilli = DateTimeUtils.getNextDateTimeInMilli(alarmTime);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTimeInMilli, getBroadcastPendingIntent(context, notificationId, alarmTime));
        } catch (DateTimeUtils.WrongTimeException e) {
            e.printStackTrace();
        }
    }

    public static PendingIntent getBroadcastPendingIntent(Context context, int notificationId, String time) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra(EXTRA_NOTIFICATION_ID, notificationId);
        alarmIntent.putExtra(EXTRA_NOTIFICATION_TITLE, "Time to wake up!");
        alarmIntent.putExtra(EXTRA_NOTIFICATION_ICON, R.drawable.ic_alarm);
        alarmIntent.putExtra(EXTRA_ALARM_TIME, time);
        return PendingIntent.getBroadcast(context, notificationId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void cancel() {
        notificationManagerCompat.cancelAll();
    }
}

package com.hfad.notifyme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String NOTIFICATION_CHANNEL_ID = "PRIMARY_NOTIFICATION_CHANNEL_ID";
    private static final String NOTIFICATION_CHANNEL = "Mascot Notification";
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, MainActivity.class);
        initializeNotificationManager();
        initializeNotificationBuilder();
    }

    private void initializeNotificationBuilder() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder = new NotificationCompat
                .Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_notify)
                .setContentTitle("My Notification")
                .setContentText("This is my notification to you")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

    public void notifyMe(View view) {
        Notification notification = notificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private void initializeNotificationManager() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from mascot");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void updateMe(View view) {
    }

    public void cancelMe(View view) {
    }
}
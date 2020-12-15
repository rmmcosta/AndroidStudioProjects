package com.hfad.joke;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String NOTIFICATION_CHANNEL_ID = "MY_NOTIFICATION_CHANNEL_123";
    private static final String NOTIFICATION_CHANNEL_NAME = "MY_NOTIFICATION_CHANNEL";
    private static final int NOTIFICATION_ID = 1234;
    private Intent bindIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void triggerService(View view) {
        Intent intent = new Intent(this, DelayedMessageService.class);
        intent.putExtra(DelayedMessageService.EXTRA_MESSAGE, getString(R.string.extra_message));
        startService(intent);
    }

    public void sendNotification(View view) {
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra(NotificationService.EXTRA_MESSAGE, "My Notification");
        startService(intent);
    }

    public void stopBindService(View view) {
        stopService(bindIntent);
    }

    public void startBindService(View view) {
        bindIntent = new Intent(this, MyBindService.class);
        startService(bindIntent);
    }

    public void sendNotification26Plus(View view) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notify26Plus();
        } else {
            sendNotification(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notify26Plus() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = null;
        notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = builder
                .setSmallIcon(R.drawable.ic_stat_notify)
                .setContentTitle("The Title")
                .setContentText("This is the content text")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
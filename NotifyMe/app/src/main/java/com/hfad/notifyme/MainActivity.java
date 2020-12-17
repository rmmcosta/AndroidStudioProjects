package com.hfad.notifyme;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.behavior.SwipeDismissBehavior;

public class MainActivity extends AppCompatActivity {

    private static final String NOTIFICATION_CHANNEL_ID = "PRIMARY_NOTIFICATION_CHANNEL_ID";
    private static final String NOTIFICATION_CHANNEL = "Mascot Notification";
    private static final int NOTIFICATION_ID = 0;
    private static final String NOTIFICATION_RECEIVER_ACTION = "com.hfad.notifyme.NOTIFICATION_RECEIVER_ACTION";
    private static final String NOTIFICATION_DISMISS_ACTION = "com.hfad.notifyme.NOTIFICATION_DISMISS_ACTION";
    private static final String LOG_TAG = "LOG_MainActivity";
    private NotificationManager notificationManager;
    private Intent intent;
    private Button btnNotify;
    private Button btnUpdate;
    private Button btnCancel;
    private BroadcastReceiver notificationReceiver;
    private BroadcastReceiver dismissReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotify = findViewById(R.id.btnNotifyMe);
        btnUpdate = findViewById(R.id.btnUpdateMe);
        btnCancel = findViewById(R.id.btnCancelMe);

        intent = new Intent(this, MainActivity.class);
        notificationManager = getNotificationManager();
        updateNotificationButtonsVisibility(true, false, false);

        notificationReceiver = new NotificationReceiver();
        IntentFilter notificationIntentFilter = new IntentFilter(NOTIFICATION_RECEIVER_ACTION);
        registerReceiver(notificationReceiver, notificationIntentFilter);

        dismissReceiver = new DismissReceiver();
        IntentFilter dismissIntentFilter = new IntentFilter(NOTIFICATION_DISMISS_ACTION);
        registerReceiver(dismissReceiver, dismissIntentFilter);
    }

    private void updateNotificationButtonsVisibility(boolean isNotifyVisible, boolean isUpdateVisible, boolean isCancelVisible) {
        if (isNotifyVisible) {
            btnNotify.setVisibility(View.VISIBLE);
        } else {
            btnNotify.setVisibility(View.INVISIBLE);
        }

        if (isUpdateVisible) {
            btnUpdate.setVisibility(View.VISIBLE);
        } else {
            btnUpdate.setVisibility(View.INVISIBLE);
        }

        if (isCancelVisible) {
            btnCancel.setVisibility(View.VISIBLE);
        } else {
            btnCancel.setVisibility(View.INVISIBLE);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Intent updateNotificationIntent = new Intent(NOTIFICATION_RECEIVER_ACTION);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateNotificationIntent, PendingIntent.FLAG_ONE_SHOT);
        Intent dismissIntent = new Intent(NOTIFICATION_DISMISS_ACTION);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return builder
                .setSmallIcon(R.drawable.ic_stat_notify)
                .setContentTitle("My Notification")
                .setContentText("This is my notification to you")
                //.setStyle(getNotificationStyle("Big Notification"))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_update_notify, getString(R.string.notification_action_update), actionPendingIntent)
                .setDeleteIntent(dismissPendingIntent)
                .setContentIntent(pendingIntent);
    }

    private NotificationCompat.Style getNotificationStyle(String bigTitle) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        return new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .setBigContentTitle(bigTitle);
    }

    public void notifyMe(View view) {
        Notification notification = getNotificationBuilder().build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        updateNotificationButtonsVisibility(false, true, true);
    }

    private NotificationManager getNotificationManager() {
        NotificationManager localNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from mascot");
            localNotificationManager.createNotificationChannel(notificationChannel);
        }
        return localNotificationManager;
    }


    public void updateMe(View view) {
        updateNotification();
    }

    private void updateNotification() {
        NotificationCompat.Builder localNotificationBuilder = getNotificationBuilder();
        localNotificationBuilder.setStyle(getNotificationStyle("Big update"));
        localNotificationBuilder.setContentTitle("Update!");
        notificationManager.notify(NOTIFICATION_ID, localNotificationBuilder.build());
    }

    public void cancelMe(View view) {
        notificationManager.cancel(NOTIFICATION_ID);
        updateNotificationButtonsVisibility(true, false, false);
    }

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Notification update broadcast received");
            if (intent.getAction().equals(NOTIFICATION_RECEIVER_ACTION)) {
                updateNotification();
            } else {
                String msg = String.format("%s not handled!", intent.getAction());
                Log.d(LOG_TAG, msg);
            }
        }
    }

    class DismissReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(LOG_TAG, "Dismiss received");
            if (intent.getAction().equals(NOTIFICATION_DISMISS_ACTION)) {
                updateNotificationButtonsVisibility(true, false, false);
            } else {
                String msg = String.format("%s not handled", intent.getAction());
                Log.d(LOG_TAG, msg);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationReceiver);
        unregisterReceiver(dismissReceiver);
    }
}
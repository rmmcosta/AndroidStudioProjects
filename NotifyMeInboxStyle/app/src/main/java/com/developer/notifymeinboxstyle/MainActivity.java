package com.developer.notifymeinboxstyle;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {
    private static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_1234";
    private static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_INBOX_STYLE";
    private static final int NOTIFICATION_ID = 1234;
    private static final String INTENT_DISMISS_ACTION = "com.developer.notifymeinboxstyle.DISMISS_ACTION";
    private static final String LOG_TAG = "LOG_MainActivity";

    private Button btnNotify;
    private BroadcastReceiver dismissReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNotify = findViewById(R.id.btnNotify);
        btnNotify.setOnClickListener(new NotifyClickListener());
        dismissReceiver = new DismissReceiver();
        IntentFilter dismissIntentFilter = new IntentFilter(INTENT_DISMISS_ACTION);
        registerReceiver(dismissReceiver, dismissIntentFilter);
    }

    public void updateNotificationInboxStyle(View view) {

    }

    class NotifyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            notifyMe();
        }
    }

    private void notifyMe() {
        btnNotify.setEnabled(false);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_droid);
        builder.setContentTitle("Notification - Inbox Style");
        builder.setContentText("This is my notification for you all!");
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setDefaults(Notification.DEFAULT_ALL);
        Intent intentDismiss = new Intent(INTENT_DISMISS_ACTION);
        PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intentDismiss, PendingIntent.FLAG_ONE_SHOT);
        builder.setDeleteIntent(pendingIntentDismiss);
        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    class DismissReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INTENT_DISMISS_ACTION)) {
                btnNotify.setEnabled(true);
            } else {
                String msg = String.format("Action %s not being handled");
                Log.d(LOG_TAG, msg);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dismissReceiver);
    }
}
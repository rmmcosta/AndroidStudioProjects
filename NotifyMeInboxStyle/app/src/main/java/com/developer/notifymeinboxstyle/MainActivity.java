package com.developer.notifymeinboxstyle;

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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_1234";
    private static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_INBOX_STYLE";
    private static final String IMAGE_NOTIFICATION_CHANNEL_ID = "IMAGE_NOTIFICATION_CHANNEL_1111";
    private static final String IMAGE_NOTIFICATION_CHANNEL_NAME = "IMAGE_NOTIFICATION_CHANNEL";
    private static final int NOTIFICATION_ID = 1234;
    private static final int IMAGE_NOTIFICATION_ID = 4444;
    private static final String INTENT_DISMISS_ACTION = "com.developer.notifymeinboxstyle.dismiss_action";
    private static final String INTENT_SHOWTOAST_ACTION = "com.developer.notifymeinboxstyle.showtoast_action";
    private static final String LOG_TAG = "LOG_MainActivity";

    private static final String BUTTON_NOTIFY_STATE = "BUTTON_NOTIFY_STATE";
    private static final String BUTTON_UPDATE_STATE = "BUTTON_UPDATE_STATE";

    private Button btnNotify;
    private Button btnUpdate;
    private BroadcastReceiver dismissReceiver;
    private BroadcastReceiver showToastReceiver;
    private NotificationCompat.Builder mainNotificationBuilder;
    private NotificationManager notificationManager;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNotify = findViewById(R.id.btnNotify);
        btnNotify.setOnClickListener(new NotifyClickListener());
        btnUpdate = findViewById(R.id.btnUpdate);
        manageSavedInstanceState(savedInstanceState);
        dismissReceiver = new DismissReceiver();
        IntentFilter dismissIntentFilter = new IntentFilter(INTENT_DISMISS_ACTION);
        registerReceiver(dismissReceiver, dismissIntentFilter);
        showToastReceiver = new ShowToastReceiver();
        IntentFilter showToastIntentFilter = new IntentFilter(INTENT_SHOWTOAST_ACTION);
        registerReceiver(showToastReceiver, showToastIntentFilter);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 1);
    }

    private void manageSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "saved instance state is not null");
            boolean btnNotifyIsEnabled = savedInstanceState.getBoolean(BUTTON_NOTIFY_STATE);
            boolean btnUpdateIsEnabled = savedInstanceState.getBoolean(BUTTON_UPDATE_STATE);
            updateNotificationButtons(btnNotifyIsEnabled, btnUpdateIsEnabled);
        } else {
            updateNotificationButtons(true, false);
        }
    }

    public void updateNotificationInboxStyle(View view) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .addLine("This is the 1st line of the update")
                .addLine("2nd line")
                .addLine("3rd and last line")
                .setSummaryText("+3 more");
        mainNotificationBuilder.setStyle(inboxStyle);
        notification = mainNotificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void sendImageNotification(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(IMAGE_NOTIFICATION_CHANNEL_ID, IMAGE_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, IMAGE_NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_image);
        builder.setContentTitle("You've received a new image");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(Notification.PRIORITY_MAX);
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.artisticwallpaper);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle()
                .bigPicture(image);
        builder.setStyle(bigPictureStyle);
        //add download action
        Intent downloadIntent = DownloadServiceWithDM.getIntent(this, "https://images4.alphacoders.com/118/118664.jpg");
        PendingIntent pendingIntentDownloadImage = PendingIntent.getService(this, IMAGE_NOTIFICATION_ID, downloadIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(R.drawable.ic_download, "Download Image", pendingIntentDownloadImage);
        
        notificationManager.notify(IMAGE_NOTIFICATION_ID, builder.build());
    }

    class NotifyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            notifyMe();
        }
    }

    private void notifyMe() {
        updateNotificationButtons(false, true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        mainNotificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        mainNotificationBuilder.setSmallIcon(R.drawable.ic_droid);
        mainNotificationBuilder.setContentTitle("Notification - Inbox Style");
        mainNotificationBuilder.setContentText("This is my notification for you all!");
        mainNotificationBuilder.setPriority(Notification.PRIORITY_MAX);
        mainNotificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        //action open main activity
        Intent intentOpenMainActivity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntentOpenMainActivity = PendingIntent.getActivity(this, NOTIFICATION_ID, intentOpenMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        mainNotificationBuilder.addAction(R.drawable.ic_open_app, "Open MainActivity", pendingIntentOpenMainActivity);
        //action show toast
        Intent intentShowToast = new Intent(INTENT_SHOWTOAST_ACTION);
        PendingIntent pendingIntentShowToast = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intentShowToast, PendingIntent.FLAG_UPDATE_CURRENT);
        mainNotificationBuilder.addAction(R.drawable.ic_show_toast, "Show toast", pendingIntentShowToast);
        //dismiss handler
        Intent intentDismiss = new Intent(INTENT_DISMISS_ACTION);
        PendingIntent pendingIntentDismiss = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intentDismiss, PendingIntent.FLAG_ONE_SHOT);
        mainNotificationBuilder.setDeleteIntent(pendingIntentDismiss);
        notification = mainNotificationBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void downloadImageDM(View view) {
        Intent intent = DownloadServiceWithDM.getIntent(this, "https://images4.alphacoders.com/118/118664.jpg");
        startService(intent);
    }

    class DismissReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INTENT_DISMISS_ACTION)) {
                updateNotificationButtons(true, false);
            } else {
                String msg = String.format("Action %s not being handled", intent.getAction());
                Log.d(LOG_TAG, msg);
            }
        }
    }

    class ShowToastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(INTENT_SHOWTOAST_ACTION)) {
                Toast toast = Toast.makeText(getApplicationContext(), "This is the toast", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                String msg = String.format("Action %s not being handled", intent.getAction());
                Log.d(LOG_TAG, msg);
            }
        }
    }


    private void updateNotificationButtons(boolean isNotifyEnabled, boolean isUpdateEnabled) {
        btnNotify.setEnabled(isNotifyEnabled);
        btnUpdate.setEnabled(isUpdateEnabled);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(LOG_TAG, "on save instance state");
        outState.putBoolean(BUTTON_NOTIFY_STATE, btnNotify.isEnabled());
        outState.putBoolean(BUTTON_UPDATE_STATE, btnUpdate.isEnabled());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dismissReceiver);
        unregisterReceiver(showToastReceiver);
    }
}
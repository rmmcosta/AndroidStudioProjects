package com.developer.standup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "LOG_MainActivity";
    private static final String NOTIFICATION_CHANNEL_ID = "ALARM_NOTIFICATION_CHANNEL_ID";
    private static final String NOTIFICATION_CHANNEL_NAME = "ALARM_NOTIFICATION_CHANNEL";
    private static final int NOTIFICATION_ID = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleAlarm = findViewById(R.id.toggleAlarm);
        toggleAlarm.setOnCheckedChangeListener(new OnToggleChange());
        ToggleButton toggleAlarmNotification = findViewById(R.id.toggleAlarmNotification);
        toggleAlarmNotification.setOnCheckedChangeListener(new OnToggleChange());
    }

    class OnToggleChange implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.d(LOG_TAG, "on checked changed");
            if (compoundButton.getId() == R.id.toggleAlarm) {
                showMessage(b ? getString(R.string.alarm_on_text) : getString(R.string.alarm_off_text));
                return;
            }
            if (compoundButton.getId() == R.id.toggleAlarmNotification) {
                sendNotification(b ? getString(R.string.alarm_on_text) : getString(R.string.alarm_off_text));
            }
        }
    }

    private void sendNotification(String s) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        //on notification click open the main activity
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, NOTIFICATION_ID, intentMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntentMainActivity);
        //build the notification
        notificationBuilder.setContentTitle(s);
        notificationBuilder.setSmallIcon(R.drawable.ic_alarm);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLights(Color.RED, 500, 500);
        notificationBuilder.setPriority(Notification.PRIORITY_MAX);

        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void showMessage(String msg) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(findViewById(R.id.mainLayout), msg, BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

}
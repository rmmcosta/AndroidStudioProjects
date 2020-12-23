package com.developer.standup;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

public class CancelAlarmReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "LOG_CancelAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int notificationId = intent.getIntExtra(AlarmNotification.EXTRA_NOTIFICATION_ID, 0);
        String time = intent.getStringExtra(AlarmNotification.EXTRA_ALARM_TIME);
        alarmManager.cancel(AlarmNotification.getBroadcastPendingIntent(context, notificationId, time));
        Log.d(LOG_TAG, "CancelAlarmReceiver - cancel alarms");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Log.d(LOG_TAG, String.format("Notification Id: %d", notificationId));
        notificationManagerCompat.cancel(notificationId);
    }
}

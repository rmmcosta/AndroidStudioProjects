package com.developer.standup;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends NotificationsReceiver {
    private static final String LOG_TAG = "LOG_AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "AlarmReceiver - on receive");
        AlarmNotification alarmNotification = getAlarmNotification(context, intent);
        Intent intentCancel = new Intent(context, CancelAlarmReceiver.class);
        String time = intent.getStringExtra(AlarmNotification.EXTRA_ALARM_TIME);
        intentCancel.putExtra(AlarmNotification.EXTRA_ALARM_TIME, time);
        int notificationId = intent.getIntExtra(AlarmNotification.EXTRA_NOTIFICATION_ID, 0);
        intentCancel.putExtra(AlarmNotification.EXTRA_NOTIFICATION_ID, notificationId);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, notificationId, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmNotification.defineAction(R.drawable.ic_notification_off, "Cancel Alarms", pendingIntentCancel);
        alarmNotification.sendAndRepeat();
    }
}

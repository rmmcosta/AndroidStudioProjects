package com.developer.standup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationsReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "LOG_NotificationsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "NotificationsReceiver - on receive");
        AlarmNotification alarmNotification = getAlarmNotification(context, intent);
        alarmNotification.send();
    }

    protected final AlarmNotification getAlarmNotification(Context context, Intent intent) {
        int notificationId = intent.getIntExtra(AlarmNotification.EXTRA_NOTIFICATION_ID, 0);
        String title = intent.getStringExtra(AlarmNotification.EXTRA_NOTIFICATION_TITLE);
        int icon = intent.getIntExtra(AlarmNotification.EXTRA_NOTIFICATION_ICON, R.drawable.ic_notification);
        String time = intent.getStringExtra(AlarmNotification.EXTRA_ALARM_TIME);
        return new AlarmNotification(context, notificationId, title, icon, time);
    }
}

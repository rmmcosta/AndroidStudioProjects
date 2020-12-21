package com.developer.standup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationsReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "LOG_AlarmReceiver";
    public static final String EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID";
    public static final String EXTRA_NOTIFICATION_TITLE = "EXTRA_NOTIFICATION_TITLE";
    public static final String EXTRA_NOTIFICATION_ICON = "EXTRA_NOTIFICATION_ICON";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "on receive");
        int notificationId = intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0);
        String title = intent.getStringExtra(EXTRA_NOTIFICATION_TITLE);
        int icon = intent.getIntExtra(EXTRA_NOTIFICATION_ICON, R.drawable.ic_notification);
        AlarmNotification.send(context, notificationId, title, icon);
    }
}

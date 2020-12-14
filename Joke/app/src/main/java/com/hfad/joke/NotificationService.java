package com.hfad.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

public class NotificationService extends IntentService {
    private Handler handler;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String LOG_TAG = "LOG_NotificationService";
    private static final int TIMEOUT = 1000;
    public static final int NOTIFICATION_ID = 5555;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        assert intent != null;
        String msg = intent.getStringExtra(EXTRA_MESSAGE);
        synchronized (this) {
            try {
                wait(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        postMessage(msg);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    private void postMessage(String msg) {
        handler.post(() -> {
            notifyMessage(msg);
        });
    }

    private void notifyMessage(String msg) {
        Log.d(LOG_TAG, msg);
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification
                .Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}

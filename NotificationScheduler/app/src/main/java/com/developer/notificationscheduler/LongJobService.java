package com.developer.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class LongJobService extends JobService {
    private static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_LONG_SERVICE_123";
    private static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_LONG_SERVICE";
    private static final int NOTIFICATION_ID = 4455;
    private static final String LOG_TAG = "LOG_LongJobService";

    private final Handler handler;

    public LongJobService() {
        handler = new Handler();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(LOG_TAG, "on start job");
        handler.post(() -> {
            Log.d(LOG_TAG, "handler post");
            try {
                Thread.sleep(5000);
                sendNotification("Long Job Start", "Service performed!");
                jobFinished(jobParameters, false);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(LOG_TAG, String.format("Exception on performing the handler post: %s", e.getMessage()));
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        sendNotification("Long Job Stop", "Service stopped!");
        return false;
    }

    private void sendNotification(String title, String text) {
        Log.d(LOG_TAG, String.format("send notification: %s, %s", title, text));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_new_notification);
        builder.setContentTitle(title);
        builder.setContentText(text);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setAutoCancel(true);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}

package com.developer.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationJobService extends JobService {
    private static final String LOG_TAG = "LOG_MyJobService";
    private static final String NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_12321";
    private static final String NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_JOB_SERVICE";
    private static final int NOTIFICATION_ID = 3322;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(LOG_TAG, "on start job");
        sendNotification("Job Service", "Your Job is completed");
        return false;
    }

    private void sendNotification(String title, String text) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(LOG_TAG, "on stop job");
        sendNotification("Job stopped", "It won't run anymore!");
        return true; //returns true, because if the job fails, you want the job to be rescheduled instead of dropped.
    }
}

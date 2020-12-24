package com.developer.largedownloadschedule;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.developer.largedownloadschedule.FakeDownload.fakeDownloadAndSendNotification;
import static com.developer.largedownloadschedule.NotificationsUtils.sendNotification;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_ID = 4321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void downloadNow(View view) {
        sendNotification("Performing Work", "Download in progress...", this);
        downloadInBackgroundThread();
    }

    public void downloadViaService(View view) {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getPackageName(), DownloadJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        builder.setRequiresCharging(true);
        builder.setRequiresDeviceIdle(true);
        builder.setPeriodic(24 * 60 * 60 * 1000);
        jobScheduler.schedule(builder.build());
    }

    private void downloadInBackgroundThread() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new MainActivity.FinishDownloadRunnable());
    }

    class FinishDownloadRunnable implements Runnable {
        @Override
        public void run() {
            fakeDownloadAndSendNotification(getApplicationContext());
        }
    }

    public void showToast(View view) {
        Toast.makeText(this, "I can show a toast!", Toast.LENGTH_SHORT).show();
    }
}
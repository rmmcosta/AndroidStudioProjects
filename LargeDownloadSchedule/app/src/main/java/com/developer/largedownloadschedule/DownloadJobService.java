package com.developer.largedownloadschedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.developer.largedownloadschedule.NotificationsUtils.sendNotification;
import static com.developer.largedownloadschedule.FakeDownload.fakeDownloadAndSendNotification;

public class DownloadJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        sendNotification("Job started", "Download in progress...", getApplicationContext());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new FinishDownloadRunnable());
        jobFinished(jobParameters, false);
        return true;
    }

    class FinishDownloadRunnable implements Runnable {
        @Override
        public void run() {
            fakeDownloadAndSendNotification(getApplicationContext());
        }
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

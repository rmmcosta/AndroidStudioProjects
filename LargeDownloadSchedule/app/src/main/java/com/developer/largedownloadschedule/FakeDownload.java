package com.developer.largedownloadschedule;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.developer.largedownloadschedule.NotificationsUtils.sendNotification;

public class FakeDownload {
    public static void fakeDownloadAndSendNotification(Context context) {
        boolean success = fakeDownload();
        if (success)
            sendNotification("Work performed", "Download complete!", context);
        else
            sendNotification("Work failed!", "Download with error!", context);
    }

    private static boolean fakeDownload() {
        try {
            Thread.sleep(10000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}

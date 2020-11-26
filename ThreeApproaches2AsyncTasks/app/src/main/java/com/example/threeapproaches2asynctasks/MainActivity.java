package com.example.threeapproaches2asynctasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String HANDLER_THREAD = "DOWNLOAD_FILES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void asyncTask(View view) {
        AsyncDownload asyncDownload = new AsyncDownload(this, 10);
        asyncDownload.execute();
    }

    public void handlerThread(View view) {
        String LOG_TAG = "HANDLER_THREAD";
        HandlerThread handlerThread = new HandlerThread(HANDLER_THREAD);
        handlerThread.start();
        int numFiles = 10;
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Object result = msg.obj;
                showDownloadSuccess(result);
            }
        };
        Runnable runnable = () -> {
            Message message = new Message();
            try {
                for (int i = 1; i <= numFiles; i++) {
                    Thread.sleep(100);
                    Log.i(LOG_TAG, getString(R.string.progress_log, i));
                }
                message.obj = LOG_TAG + ": " + getString(R.string.download_files_success, numFiles);
            } catch (Exception e) {
                message.obj = e.getMessage();
            }
            handler.sendMessage(message);
        };
        handler.post(runnable);
    }

    private void showDownloadSuccess(Object result) {
        Handler uiThread = new Handler(Looper.getMainLooper());
        uiThread.post(() -> {
            String message = String.valueOf(result);
            showToast(message);
        });

    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public void executors(View view) {
        String LOG_TAG = "EXECUTOR";
        int numFiles = 10;
        ExecutorService executors = Executors.newFixedThreadPool(1);
        Runnable runnable = () -> {
            // your async code goes here.
            String message;
            try {
                for (int i = 1; i <= numFiles; i++) {
                    Thread.sleep(100);
                    Log.i(LOG_TAG, getString(R.string.progress_log, i));
                }
                // create message and pass any object here doesn't matter
                // for a simple example I have used a simple string
                message = LOG_TAG + ": " + getString(R.string.download_files_success, numFiles);
            } catch (Exception e) {
                message = e.getMessage();
            }
            showDownloadSuccess(message);
        };
        executors.submit(runnable);
    }
}
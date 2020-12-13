package com.hfad.joke;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DelayedMessageService extends IntentService {

    private static final String LOG_TAG = "LOG_DelayedMessageService";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final int TIMEOUT = 1000;
    private Handler handler;

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    //this method runs on a different thread
    @Override
    protected void onHandleIntent(Intent intent) {
        String logMsg = intent.toString();
        Log.d(LOG_TAG, logMsg);
        synchronized (this) {
            try {
                wait(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, e.getMessage());
            }
        }
        logExtraMessage(intent);
    }

    //the onStartCommand runs on the main thread
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    private void logExtraMessage(Intent intent) {
        String extraMessage = intent.getStringExtra(EXTRA_MESSAGE);
        Log.d(LOG_TAG, extraMessage);
        handler.post(() -> showText(extraMessage));
    }

    private void showText(String extraMessage) {
        Toast toast = Toast.makeText(getApplicationContext(), extraMessage, Toast.LENGTH_SHORT);
        toast.show();
    }
}
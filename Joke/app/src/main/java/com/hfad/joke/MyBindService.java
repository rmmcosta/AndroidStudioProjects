package com.hfad.joke;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyBindService extends Service {
    private static final String LOG_TAG = "LOG_MyBindService";

    public MyBindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "My bind service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "My bind service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "My bind service destroyed!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "My bind service bound");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class InternetReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "LOG_InternetReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = "Action not handled";
        if (intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
            boolean connectivityStatus = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
            if (connectivityStatus) {
                msg = "You're back online";
            } else {
                msg = "You're offline!";
            }
        }
        Log.d(LOG_TAG, msg);
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}

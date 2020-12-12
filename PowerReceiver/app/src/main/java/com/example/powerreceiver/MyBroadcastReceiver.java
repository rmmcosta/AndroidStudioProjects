package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "LOG_MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Action:");
        stringBuilder.append(intent.getAction());
        stringBuilder.append("\n");
        stringBuilder.append(MainActivity.BROADCAST_DATA + ":");
        stringBuilder.append(intent.getStringExtra(MainActivity.BROADCAST_DATA));
        stringBuilder.append("\n");
        stringBuilder.append("URI:");
        stringBuilder.append(intent.toUri(Intent.URI_INTENT_SCHEME));
        stringBuilder.append("\n");
        String msg = stringBuilder.toString();
        Log.d(LOG_TAG, msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

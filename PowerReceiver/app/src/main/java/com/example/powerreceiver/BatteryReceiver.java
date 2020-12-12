package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg="Action not handled";
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            msg = "Charging...";
        }
        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            msg = "Charging ended!";
        }
        showMessage(context, msg);
    }

    private void showMessage(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}

package com.example.powerreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String BROADCAST_DATA = "BROADCAST_DATA";
    public static final String BROADCAST_ACTION = BuildConfig.APPLICATION_ID + ".MyBroadcast";
    public static final String LOCAL_BROADCAST_ACTION = BuildConfig.APPLICATION_ID + ".MyOtherBroadcast";
    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver localBroadcastReceiver;
    private BroadcastReceiver internetReceiver;
    private BroadcastReceiver powerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        this.registerReceiver(broadcastReceiver, intentFilter);
        localBroadcastReceiver = new MyReceiver();
        intentFilter = new IntentFilter(LOCAL_BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReceiver, intentFilter);
        internetReceiver = new InternetReceiver();
        intentFilter = new IntentFilter(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        this.registerReceiver(internetReceiver, intentFilter);
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        powerReceiver = new BatteryReceiver();
        this.registerReceiver(powerReceiver, intentFilter);
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra(BROADCAST_DATA, "My broadcast to the world of apps!");
        sendBroadcast(intent);
    }

    public void sendOtherBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(LOCAL_BROADCAST_ACTION);
        intent.putExtra(BROADCAST_DATA, "My local broadcast");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(broadcastReceiver);
        this.unregisterReceiver(localBroadcastReceiver);
        this.unregisterReceiver(internetReceiver);
        this.unregisterReceiver(powerReceiver);
        super.onDestroy();
    }
}
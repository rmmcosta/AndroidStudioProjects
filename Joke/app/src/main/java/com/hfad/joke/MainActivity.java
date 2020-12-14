package com.hfad.joke;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Intent bindIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void triggerService(View view) {
        Intent intent = new Intent(this, DelayedMessageService.class);
        intent.putExtra(DelayedMessageService.EXTRA_MESSAGE, getString(R.string.extra_message));
        startService(intent);
    }

    public void sendNotification(View view) {
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra(NotificationService.EXTRA_MESSAGE, "My Notification");
        startService(intent);
    }

    public void stopBindService(View view) {
        stopService(bindIntent);
    }

    public void startBindService(View view) {
        bindIntent = new Intent(this, MyBindService.class);
        startService(bindIntent);
    }
}
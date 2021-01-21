package com.developer.livedatasimple.simpleChronometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

import com.developer.livedatasimple.R;

public class MainActivity extends AppCompatActivity {

    private static final String CHRONOMETER_BASE = "CHRONOMETER_BASE";
    private static final String CHRONOMETER_IS_RUNNING = "CHRONOMETER_IS_RUNNING";
    private Chronometer chronometer;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        if (savedInstanceState != null) {
            long base = savedInstanceState.getLong(CHRONOMETER_BASE);
            chronometer.setBase(base);
            boolean wasRunning = savedInstanceState.getBoolean(CHRONOMETER_IS_RUNNING);
            if (wasRunning) {
                chronometer.start();
                isRunning = true;
            }
        }
    }

    public void start(View view) {
        chronometer.start();
        isRunning = true;
    }

    public void stop(View view) {
        chronometer.stop();
        isRunning = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CHRONOMETER_BASE, chronometer.getBase());
        outState.putBoolean(CHRONOMETER_IS_RUNNING, isRunning);
    }
}
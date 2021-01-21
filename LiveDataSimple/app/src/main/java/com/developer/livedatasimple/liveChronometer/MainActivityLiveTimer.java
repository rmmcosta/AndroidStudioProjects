package com.developer.livedatasimple.liveChronometer;

import android.os.Bundle;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.livedatasimple.R;

public class MainActivityLiveTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_live_timer);
        Chronometer chronometer = findViewById(R.id.chronometerLive);
        chronometer.start();
        LiveTimer liveTimer = new LiveTimer();
        liveTimer.getLiveTimer().observe(this, aLong -> chronometer.setBase(aLong));
    }
}
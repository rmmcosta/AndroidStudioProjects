package com.developer.livedatasimple.liveElapsedTime;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.developer.livedatasimple.R;

public class MainActivityLiveTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_live_timer);
        TextView tvTimer = findViewById(R.id.tvTimer);
        LiveTimer liveTimer = new LiveTimer();
        liveTimer.getLiveTimer().observe(this, aLong -> tvTimer.setText(String.valueOf(aLong)));
    }
}
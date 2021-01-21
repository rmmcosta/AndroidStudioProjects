package com.developer.livedatasimple.liveElapsedTimeViewModel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.developer.livedatasimple.R;

public class MainActivityLiveTimerViewModel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_live_timer);
        TextView tvTimer = findViewById(R.id.tvTimer);
        LiveTimerViewModel liveTimerViewModel = new ViewModelProvider(this).get(LiveTimerViewModel.class);
        liveTimerViewModel.getLiveTimer().observe(this, aLong -> tvTimer.setText(String.valueOf(aLong)));
    }
}
package com.example.stopwatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String LOG = "MyLog";
    private final String SAVE_STOPWATCH_ISACTIVE = "SaveStopwatchIsActive";
    private final String SAVE_STOPWATCH_SECS = "SaveStopwatchSecs";
    private final String SAVE_STOPWATCH_MINS = "SaveStopwatchMins";
    private final String SAVE_STOPWATCH_HOURS = "SaveStopwatchHours";
    private boolean is2IncTimer, was2IncTimer;
    private int secs, mins, hours;

    public static String leftPadding(int number, char ch, int L) {
        String result = String.format("%" + L + "s", number).replace(' ', ch);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG, "on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStopwatch();
        initTimer();
        if (savedInstanceState != null) {
            loadSaveStopwatch(savedInstanceState);
        }
    }

    private void initTimer() {
        final Handler every1sec = new Handler();
        Runnable runTimer = new Runnable() {
            @Override
            public void run() {
                if (is2IncTimer) {
                    incTimer();
                }
                every1sec.postDelayed(this, 1000);
            }
        };
        every1sec.post(runTimer);
    }

    private void loadSaveStopwatch(Bundle savedInstanceState) {
        is2IncTimer = savedInstanceState.getBoolean(SAVE_STOPWATCH_ISACTIVE);
        secs = savedInstanceState.getInt(SAVE_STOPWATCH_SECS);
        mins = savedInstanceState.getInt(SAVE_STOPWATCH_MINS);
        hours = savedInstanceState.getInt(SAVE_STOPWATCH_HOURS);
        updateTVStopWatch();
    }

    private void initStopwatch() {
        secs = 0;
        mins = 0;
        hours = 0;
        updateTVStopWatch();
    }

    private void updateTVStopWatch() {
        TextView tvStopwatch = findViewById(R.id.tvStopwatch);
        String stopwatch = leftPadding(hours, '0', 2) + ':'
                + leftPadding(mins, '0', 2) + ':'
                + leftPadding(secs, '0', 2);
        tvStopwatch.setText(stopwatch);
    }

    private void incTimer() {
        if (secs == 59) {
            secs = 0;
            if (mins == 59) {
                mins = 0;
                hours++;
            } else
                mins++;
        } else
            secs++;
        updateTVStopWatch();
    }

    public void startStopwatch(View view) {
        is2IncTimer = true;
    }

    public void stopStopwatch(View view) {
        is2IncTimer = false;
    }

    public void resetStopwatch(View view) {
        initStopwatch();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(LOG, "on save instance state");
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_STOPWATCH_ISACTIVE, was2IncTimer);
        outState.putInt(SAVE_STOPWATCH_SECS, secs);
        outState.putInt(SAVE_STOPWATCH_MINS, mins);
        outState.putInt(SAVE_STOPWATCH_HOURS, hours);
    }

    @Override
    protected void onStop() {
        Log.d(LOG, "on stop");
        super.onStop();
    }

    private void pauseTimerAndSaveState() {
        was2IncTimer = is2IncTimer;
        is2IncTimer = false;
    }

    @Override
    protected void onStart() {
        Log.d(LOG, "on start");
        super.onStart();
    }

    private void checkIsIncTimer() {
        if (!is2IncTimer)
            is2IncTimer = was2IncTimer;
    }

    @Override
    protected void onPause() {
        Log.d(LOG, "on pause");
        super.onPause();
        pauseTimerAndSaveState();
    }

    @Override
    protected void onResume() {
        Log.d(LOG, "on resume");
        super.onResume();
        checkIsIncTimer();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "My title");
        intent.putExtra(Intent.EXTRA_SUBJECT, "My subject");
        intent.putExtra(Intent.EXTRA_TEXT, "The message");
        startActivity(intent);
    }
}
package com.example.workoutfragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StopwatchFragment extends Fragment {

    private Stopwatch stopwatch;
    private static int INC_STEP_IN_MS = 1000;
    private TextView tvStopwatch;
    private Button btnStart;
    private Button btnStop;
    private Button btnReset;
    private static final String LOG_TAG = "STOPWATCH_FRAGMENT";
    private static final String STOPWATCH_STATE_KEY = "STOPWATCH_STATE_KEY";
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        btnStart = view.findViewById(R.id.btnStart);
        btnStop = view.findViewById(R.id.btnStop);
        btnReset = view.findViewById(R.id.btnReset);
        btnStart.setOnClickListener(view1 -> startWatch());
        btnStop.setOnClickListener(view1 -> stopWatch());
        btnReset.setOnClickListener(view1 -> resetWatch());
        tvStopwatch = view.findViewById(R.id.tvStopwatch);
        if (savedInstanceState != null && savedInstanceState.getSerializable(STOPWATCH_STATE_KEY) != null) {
            stopwatch = (Stopwatch) savedInstanceState.getSerializable(STOPWATCH_STATE_KEY);
        } else {
            stopwatch = new Stopwatch(0, 0, 0);
        }
        updateStopwatch();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        startHandler();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopwatch.stopCounting();
    }

    @Override
    public void onResume() {
        super.onResume();
        stopwatch.resume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STOPWATCH_STATE_KEY, stopwatch);
    }

    private void startWatch() {
        stopwatch.startCounting();
    }

    private void stopWatch() {
        stopwatch.stopCounting();
    }

    private void resetWatch() {
        stopwatch.reset();
        updateStopwatch();
    }

    private void startHandler() {
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "run runnable");
                if (stopwatch.isCounting()) {
                    incStopwatchAndUpdateUI();
                }
                handler.postDelayed(this, INC_STEP_IN_MS);
            }
        };
        handler.post(runnable);
    }

    private void incStopwatchAndUpdateUI() {
        if (stopwatch.isCounting()) {
            stopwatch.incSeconds();
            updateStopwatch();
        }
    }

    private void updateStopwatch() {
        tvStopwatch.setText(stopwatch.toString());
    }
}
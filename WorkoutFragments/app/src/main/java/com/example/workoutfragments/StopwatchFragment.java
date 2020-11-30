package com.example.workoutfragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class StopwatchFragment extends Fragment {

    private Stopwatch stopwatch;
    private boolean isStopwatchCounting;
    private static int INC_STEP_IN_MS = 1000;
    private TextView tvStopwatch;
    private Button btnStart;
    private Button btnStop;
    private Button btnReset;
    private static final String LOG_TAG = "STOPWATCH_FRAGMENT";
    AsyncStopwatch asyncStopwatch;

    private class AsyncStopwatch extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(LOG_TAG, "do in background");
            try {
                while (isStopwatchCounting) {
                    Thread.sleep(INC_STEP_IN_MS);
                    publishProgress();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            if (!isStopwatchCounting) {
                return;
            }
            stopwatch.incSeconds();
            updateStopwatch();
        }
    }

    private void updateStopwatch() {
        tvStopwatch.setText(stopwatch.toString());
    }


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
        stopwatch = new Stopwatch(0, 0, 0);
        updateStopwatch();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    private void startWatch() {
        isStopwatchCounting = true;
        asyncStopwatch = new AsyncStopwatch();
        asyncStopwatch.execute();
    }

    private void stopWatch() {
        isStopwatchCounting = false;
    }

    private void resetWatch() {
        stopwatch.reset();
        updateStopwatch();
    }
}
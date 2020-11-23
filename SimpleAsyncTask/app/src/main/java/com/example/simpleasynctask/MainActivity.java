package com.example.simpleasynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    private TextView tvOperation;
    private static final String TV_OPERATION_TEXT = "TV_OPERATION_TEXT";
    private ProgressBar progressBar;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        tvOperation = findViewById(R.id.tvOperation);
        if (savedInstanceState != null && !savedInstanceState.getString(TV_OPERATION_TEXT).equals("")) {
            tvOperation.setText(savedInstanceState.getString(TV_OPERATION_TEXT));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TV_OPERATION_TEXT, tvOperation.getText().toString());
    }

    public void startTask(View view) {
        int sleepInMs = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setMin(0);
        }
        progressBar.setMax(sleepInMs);
        new MyAsyncTask(sleepInMs, this, tvOperation, progressBar).execute();
    }

    private static class MyAsyncTask extends AsyncTask<Void, Integer, String> {
        private final int sleepInMs;
        private final Context context;
        private final WeakReference<TextView> tvResult;
        private final WeakReference<ProgressBar> pb1;

        private MyAsyncTask(int sleepInMs, Context context, TextView tvResult, ProgressBar pb1) {
            this.sleepInMs = sleepInMs;
            this.context = context;
            this.tvResult = new WeakReference<>(tvResult);
            this.pb1 = new WeakReference<>(pb1);
        }

        @Override
        protected void onPreExecute() {
            tvResult.get().setText(R.string.while_running_async_task);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                int count = 0;
                while (count++ < sleepInMs) {
                    Thread.sleep(1);
                    publishProgress(count);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return String.format(context.getString(R.string.async_task_result), sleepInMs);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvResult.get().setText(String.format(context.getString(R.string.while_running_async_task), values[0]));
            pb1.get().setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            tvResult.get().setText(s);
            Toast toast = Toast.makeText(context, s, LENGTH_LONG);
            toast.show();
        }
    }
}
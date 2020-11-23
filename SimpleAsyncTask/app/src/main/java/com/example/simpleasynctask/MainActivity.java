package com.example.simpleasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.net.URL;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTask(View view) {
        new MyAsyncTask(900, this).execute();
    }

    private static class MyAsyncTask extends AsyncTask<URL, Integer, String> {
        private int sleepInMs;
        private Context context;

        private MyAsyncTask(int sleepInMs, Context context) {
            this.sleepInMs = sleepInMs;
            this.context = context;
        }

        @Override
        protected String doInBackground(URL... urls) {
            try {
                Thread.sleep(sleepInMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Finished";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast toast = Toast.makeText(context, s, LENGTH_LONG);
            toast.show();
        }
    }
}
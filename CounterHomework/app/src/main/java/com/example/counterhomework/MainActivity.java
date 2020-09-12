package com.example.counterhomework;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String SAVED_COUNT_KEY = "SavedCount";
    private int currCount;
    TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCount = findViewById(R.id.tvCount);
        if (savedInstanceState != null) {
            recoverCount(savedInstanceState);
        }
    }

    private void recoverCount(Bundle savedInstanceState) {
        currCount = savedInstanceState.getInt(SAVED_COUNT_KEY);
        tvCount.setText(String.valueOf(currCount));
    }

    public void incCount(View view) {
        tvCount.setText(String.valueOf(++currCount));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_COUNT_KEY, currCount);
    }
}
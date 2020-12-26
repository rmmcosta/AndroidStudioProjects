package com.developer.hellosharedprefs;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int COUNT_INITIAL = 0;
    private static final int COLOR_DEFAULT = 0;
    private static final String LOG_TAG = "LOG_MainActivity";
    private static final String SAVE_COLOR_BUTTON_ID = "COLOR_BUTTON_ID";
    private static final String SAVE_COUNT = "COUNT";
    private int count = COUNT_INITIAL;
    private int currentColorButtonId;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCount = findViewById(R.id.tvCount);
        if (savedInstanceState != null) {
            changeColor(savedInstanceState.getInt(SAVE_COLOR_BUTTON_ID));
            count = savedInstanceState.getInt(SAVE_COUNT);
            updateCountTextView();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVE_COLOR_BUTTON_ID, currentColorButtonId);
        outState.putInt(SAVE_COUNT, count);
        super.onSaveInstanceState(outState);
    }

    public void changeColor(View view) {
        changeColor(view.getId());
    }

    private void changeColor(int id) {
        currentColorButtonId = id;
        if (id == COLOR_DEFAULT) {
            resetColor();
        } else {
            Button clickedButton = findViewById(id);
            ColorStateList color = clickedButton.getBackgroundTintList();
            tvCount.setBackgroundTintList(color);
        }
    }

    private void updateCountTextView() {
        tvCount.setText(String.valueOf(count));
    }

    public void incrementCount(View view) {
        ++count;
        updateCountTextView();
    }

    private void resetColor() {
        tvCount.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.gray)));
    }

    private void resetCount() {
        count = COUNT_INITIAL;
        updateCountTextView();
    }

    public void resetUI(View view) {
        changeColor(COLOR_DEFAULT);
        resetCount();
    }
}
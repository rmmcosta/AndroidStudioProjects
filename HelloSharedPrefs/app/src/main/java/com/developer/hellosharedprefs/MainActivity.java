package com.developer.hellosharedprefs;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
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
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tvCount);
        checkSavedState(savedInstanceState);

        preferences = getPreferences(MODE_PRIVATE); //mode_private - the file can only be accessed by the calling application
        checkSavedPreferences();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVE_COLOR_BUTTON_ID, currentColorButtonId);
        outState.putInt(SAVE_COUNT, count);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        savePreferences();
        super.onPause();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SAVE_COLOR_BUTTON_ID, currentColorButtonId);
        editor.putInt(SAVE_COUNT, count);
        boolean success = editor.commit();
        Log.d(LOG_TAG, String.format("preferences committed with success: %s", success ? "True" : "False"));
    }

    private void checkSavedPreferences() {
        if (preferences.contains(SAVE_COLOR_BUTTON_ID)) {
            changeColor(preferences.getInt(SAVE_COLOR_BUTTON_ID, COLOR_DEFAULT));
        }
        if (preferences.contains(SAVE_COUNT)) {
            count = preferences.getInt(SAVE_COUNT, COUNT_INITIAL);
            updateCountTextView();
        }
    }

    private void checkSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            changeColor(savedInstanceState.getInt(SAVE_COLOR_BUTTON_ID));
            count = savedInstanceState.getInt(SAVE_COUNT);
            updateCountTextView();
        }
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
package com.example.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvScoreTeam1;
    private TextView tvScoreTeam2;
    private final String LOG_TAG = "MYLOG";
    private final String SCORE_TEAM1 = "SCORE_TEAM1", SCORE_TEAM2 = "SCORE_TEAM2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvScoreTeam1 = findViewById(R.id.tvTeam1Score);
        Log.d(LOG_TAG, "tvScoreTeam1 = " + tvScoreTeam1.toString());
        tvScoreTeam2 = findViewById(R.id.tvTeam2Score);
        Log.d(LOG_TAG, "tvScoreTeam2 = " + tvScoreTeam2.toString());
        checkInitialScore(savedInstanceState);
    }

    private void checkInitialScore(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            tvScoreTeam1.setText(String.valueOf(savedInstanceState.getInt(SCORE_TEAM1)));
            tvScoreTeam2.setText(String.valueOf(savedInstanceState.getInt(SCORE_TEAM2)));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE_TEAM1, getScore(tvScoreTeam1));
        outState.putInt(SCORE_TEAM2, getScore(tvScoreTeam2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES)
            menu.removeItem(R.id.optionDay);
        else
            menu.removeItem(R.id.optionNight);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        switch (option) {
            case R.id.optionDay:
                applyDayMode();
                return true;
            case R.id.optionNight:
                applyNighMode();
                return true;
            default:
                return false;
        }
    }

    private void applyNighMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    private void applyDayMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void increaseScoreTeam1(View view) {
        int score = getScore(tvScoreTeam1);
        tvScoreTeam1.setText(String.valueOf(++score));
    }

    public void increaseScoreTeam2(View view) {
        int score = getScore(tvScoreTeam2);
        tvScoreTeam2.setText(String.valueOf(++score));
    }

    public void decreaseScoreTeam1(View view) {
        int score = getScore(tvScoreTeam1);
        tvScoreTeam1.setText(String.valueOf(--score));
    }

    public void decreaseScoreTeam2(View view) {
        int score = getScore(tvScoreTeam2);
        tvScoreTeam2.setText(String.valueOf(--score));
    }

    private int getScore(TextView textView) {
        int score = Integer.parseInt(textView.getText().toString());
        Log.d(LOG_TAG, "score = " + score);
        return score;
    }
}
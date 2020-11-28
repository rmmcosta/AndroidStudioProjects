package com.example.workoutfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    private int workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        workoutId = getIntent().getIntExtra(MainActivity.WORKOUT_EXTRA, -1);
        WorkoutDetailFragment workoutDetailFragment = (WorkoutDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        workoutDetailFragment.setWorkoutId(workoutId);
    }
}
package com.example.workoutfragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class WorkoutDetailFragment extends Fragment {

    private int workoutId = -1;
    private static final String SAVED_WORKOUT_KEY = "SAVED_WORKOUT_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getInt(SAVED_WORKOUT_KEY);
        }
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_WORKOUT_KEY, workoutId);
    }

    private void refreshData() {
        View fragmentView = getView();
        if (workoutId != -1) {
            TextView tvWorkoutName = fragmentView.findViewById(R.id.tvWorkoutName);
            TextView tvWorkoutDescription = fragmentView.findViewById(R.id.tvWorkoutDescription);
            Workout workout = Workout.workouts[workoutId];
            tvWorkoutName.setText(workout.getName());
            tvWorkoutDescription.setText(workout.getDescription());
        }
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }
}
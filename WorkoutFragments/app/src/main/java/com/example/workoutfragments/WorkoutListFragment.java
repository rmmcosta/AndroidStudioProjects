package com.example.workoutfragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.stream.Stream;

public class WorkoutListFragment extends ListFragment {

    private static final String LOG_TAG = "WORKOUT_LIST_FRAGMENT";

    interface WorkoutItemClick {
        void onItemClick(int id);
    }

    private WorkoutItemClick workoutItemClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] workouts = Stream.of(Workout.workouts).map(Workout::toString).toArray(String[]::new);
        ArrayAdapter<String> workoutsAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, workouts);
        setListAdapter(workoutsAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        workoutItemClick = (WorkoutItemClick) context;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (workoutItemClick != null)
            workoutItemClick.onItemClick(position);
    }
}
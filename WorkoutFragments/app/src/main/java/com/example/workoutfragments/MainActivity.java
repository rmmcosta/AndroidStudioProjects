package com.example.workoutfragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutItemClick {

    public static final String WORKOUT_EXTRA = "WORKOUT_EXTRA";
    private static final String LOG_TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClick(int id) {
        if (findViewById(R.id.detailFragmentContainer) != null) {
            replaceFragment(id);
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(WORKOUT_EXTRA, id);
            startActivity(intent);
        }
    }

    private void replaceFragment(int id) {
        WorkoutDetailFragment workoutDetailFragment = new WorkoutDetailFragment();
        workoutDetailFragment.setWorkoutId(id);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.detailFragmentContainer, workoutDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
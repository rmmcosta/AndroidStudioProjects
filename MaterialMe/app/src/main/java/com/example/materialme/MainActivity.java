package com.example.materialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;
    public static final String SELECTED_SPORT_ITEM = "SELECTED_SPORT_ITEM";
    public static final String SAVED_SPORTS_DATA = "SAVE_SPORTS_DATA";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_SPORTS_DATA, mSportsData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rvSports);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSportsData = new ArrayList<>();

        mAdapter = new SportsAdapter(mSportsData, this);
        mRecyclerView.setAdapter(mAdapter);

        initializeOrRecoverData(savedInstanceState);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        Log.d("MyLog", "Move");
                        int from = viewHolder.getAdapterPosition();
                        int to = target.getAdapterPosition();
                        Collections.swap(mSportsData, from, to);
                        mAdapter.notifyItemMoved(from, to);
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Log.d("MyLog", "Swiped - direction: " + direction);
                        int position = viewHolder.getAdapterPosition();
                        mSportsData.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initializeOrRecoverData(Bundle savedInstanceState) {
        if(savedInstanceState != null && !savedInstanceState.getParcelableArrayList(SAVED_SPORTS_DATA).isEmpty())
            recoverData(savedInstanceState);
        else
            initializeData();
    }

    private void recoverData(Bundle savedInstanceState) {
        ArrayList<Sport> savedSportsData = savedInstanceState.getParcelableArrayList(SAVED_SPORTS_DATA);
        mSportsData.clear();
        mSportsData.addAll(savedSportsData);
    }


    /**
     * Method for initializing the sports data from resources.
     */
    private void initializeData() {
        //Get the resources from the XML file
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        TypedArray sportsBanners = getResources().obtainTypedArray(R.array.sports_banners);

        for (int i = 0; i < sportsBanners.length(); i++) {
            Log.d("MyLog", "id: " + sportsBanners.getResourceId(i, 0));
        }

        //Clear the existing data (to avoid duplication)
        mSportsData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i], sportsBanners.getResourceId(i, 0)));
        }

        //Notify the adapter of the change
        mAdapter.notifyDataSetChanged();
    }

    public void loadOriginalNews(View view) {
        initializeData();
    }
}
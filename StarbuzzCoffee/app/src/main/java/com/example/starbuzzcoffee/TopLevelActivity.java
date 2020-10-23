package com.example.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.starbuzzcoffee.adapters.OptionsListAdapter;

public class TopLevelActivity extends AppCompatActivity {
    private String[] optionsList;
    private RecyclerView rvOptionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        initializeOptions();
        initializeRVOptionsList();
    }

    private void initializeRVOptionsList() {
        rvOptionsList = findViewById(R.id.rvOptionsList);
        RecyclerView.Adapter optionsListAdapter = new OptionsListAdapter(this, optionsList);
        rvOptionsList.setAdapter(optionsListAdapter);
        rvOptionsList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeOptions() {
        optionsList = getResources().getStringArray(R.array.top_options);
    }
}
package com.developer.appwithsettings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

public class SettingsActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        RecyclerView recyclerView = findViewById(R.id.rvSettings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] settingsArray = getResources().getStringArray(R.array.settingsList);
        Adapter adapter = new SettingsAdapter(settingsArray, Settings.icons, this);
        recyclerView.setAdapter(adapter);
    }
}
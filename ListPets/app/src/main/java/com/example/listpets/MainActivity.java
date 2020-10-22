package com.example.listpets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPets;
    private String[] petsNames;
    private String[] petsDescriptions;
    private int[] petsImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeArrays();
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerViewPets = findViewById(R.id.rvPets);
        RecyclerView.Adapter adapter = new PetsAdapter(petsNames, petsDescriptions, petsImages, this);
        recyclerViewPets.setAdapter(adapter);
        recyclerViewPets.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeArrays() {
        petsNames = getResources().getStringArray(R.array.pets_name);
        petsDescriptions = getResources().getStringArray(R.array.pets_description);
        petsImages = new int[]{R.drawable.cat, R.drawable.dog, R.drawable.cat, R.drawable.dog, R.drawable.cat, R.drawable.dog, R.drawable.cat, R.drawable.dog};
    }
}
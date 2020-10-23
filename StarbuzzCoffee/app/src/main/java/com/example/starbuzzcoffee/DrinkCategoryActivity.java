package com.example.starbuzzcoffee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.starbuzzcoffee.adapters.DrinksAdapter;

public class DrinkCategoryActivity extends AppCompatActivity {

    private String[] drinksList;
    private RecyclerView rvDrinkOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        initializeDrinksList();
        initializeRVDrinkOptions();
    }

    private void initializeRVDrinkOptions() {
        rvDrinkOptions = findViewById(R.id.rvDrinkOptions);
        RecyclerView.Adapter drinksAdapter = new DrinksAdapter(this, drinksList);
    }

    private void initializeDrinksList() {
        drinksList = getResources().getStringArray(R.array.drink_options);
    }
}
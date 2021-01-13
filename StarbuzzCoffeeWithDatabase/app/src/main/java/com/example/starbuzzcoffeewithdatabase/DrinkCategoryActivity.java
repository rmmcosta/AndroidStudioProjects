package com.example.starbuzzcoffeewithdatabase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbuzzcoffeewithdatabase.adapters.DrinksAdapter;

public class DrinkCategoryActivity extends AppCompatActivity {

    private DrinkEntity[] drinksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        initializeDrinksList();
        initializeRVDrinkOptions();
    }

    private void initializeRVDrinkOptions() {
        RecyclerView rvDrinkOptions = findViewById(R.id.rvDrinkOptions);
        RecyclerView.Adapter drinksAdapter = new DrinksAdapter(this, drinksList);
        rvDrinkOptions.setAdapter(drinksAdapter);
        rvDrinkOptions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeDrinksList() {
        SBuzzSQLiteHelper sqLiteHelper = SBuzzSQLiteHelper.getInstance(this);
        drinksList = sqLiteHelper.getDrinks();
    }
}
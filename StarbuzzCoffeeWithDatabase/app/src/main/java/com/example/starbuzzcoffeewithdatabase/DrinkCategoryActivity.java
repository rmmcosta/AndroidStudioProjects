package com.example.starbuzzcoffeewithdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.starbuzzcoffeewithdatabase.adapters.DrinksAdapter;

public class DrinkCategoryActivity extends AppCompatActivity {

    private Drink[] drinksList;
    public static final String SELECTED_DRINK = "SELECTED_DRINK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        initializeDrinksList();
        initializeRVDrinkOptions();
    }

    private void initializeRVDrinkOptions() {
        RecyclerView rvDrinkOptions = findViewById(R.id.rvDrinkOptions);
        RecyclerView.Adapter drinksAdapter = new DrinksAdapter(this, drinksList, new OnDrinkClickListener());
        rvDrinkOptions.setAdapter(drinksAdapter);
        rvDrinkOptions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeDrinksList() {
        drinksList = Drink.getDrinks();
    }

    class OnDrinkClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            TextView tvDrink = view.findViewById(R.id.tvDrinksItem);
            String drink = (String) tvDrink.getText();
            showDrinkDetail(drink);
        }
    }

    private void showDrinkDetail(String drink) {
        Intent intent = new Intent(this, DrinkActivity.class);
        Product product = Drink.getSelectedDrink(drink);
        intent.putExtra(SELECTED_DRINK, product);
        startActivity(intent);
    }
}
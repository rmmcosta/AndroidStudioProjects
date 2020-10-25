package com.example.starbuzzcoffee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DrinkActivity extends AppCompatActivity {
    private TextView tvDrinkDescription;
    private ImageView ivDrinkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        initializeLocals();
        fillData(getIntent());
    }

    private void fillData(Intent intent) {
        Product product = (Product) intent.getSerializableExtra(DrinkCategoryActivity.SELECTED_DRINK);
        tvDrinkDescription.setText(getResources().getText(product.getResourceDescription()));
        ivDrinkImage.setImageDrawable(getResources().getDrawable(product.getDrawable()));
    }

    private void initializeLocals() {
        ivDrinkImage = findViewById(R.id.ivDrink);
        tvDrinkDescription = findViewById(R.id.tvDrinkDescription);
    }
}
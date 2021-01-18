package com.example.starbuzzcoffeewithdatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.starbuzzcoffeewithdatabase.adapters.DrinksAdapter;

public class DrinkActivity extends AppCompatActivity {
    private TextView tvDrinkDescription;
    private TextView tvDrinkName;
    private ImageView ivDrinkImage;
    private CheckBox checkFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        initializeLocals();
        fillData(getIntent());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void fillData(Intent intent) {
        SBuzzSQLiteHelper sqLiteHelper = new SBuzzSQLiteHelper(this);
        int _id = intent.getIntExtra(DrinksAdapter.SELECTED_DRINK_ID, 0);
        DrinkEntity drinkEntity = sqLiteHelper.getDrink(_id);
        assert drinkEntity != null;
        tvDrinkDescription.setText(getResources().getText(drinkEntity.getDescriptionId()));
        tvDrinkName.setText(drinkEntity.getName());
        ivDrinkImage.setImageDrawable(getResources().getDrawable(drinkEntity.getDrawableId()));
        checkFavorite.setChecked(drinkEntity.isFavorite());
        checkFavorite.setOnCheckedChangeListener((compoundButton, b) -> {
            sqLiteHelper.updateFavorite(_id, b);
        });
    }

    private void initializeLocals() {
        ivDrinkImage = findViewById(R.id.ivDrink);
        tvDrinkDescription = findViewById(R.id.tvDrinkDescription);
        tvDrinkName = findViewById(R.id.tvDrinkName);
        checkFavorite = findViewById(R.id.checkFavorite);
    }
}
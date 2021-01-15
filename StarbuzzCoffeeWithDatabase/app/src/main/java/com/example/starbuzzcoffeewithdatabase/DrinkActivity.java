package com.example.starbuzzcoffeewithdatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.starbuzzcoffeewithdatabase.adapters.DrinksAdapter;

public class DrinkActivity extends AppCompatActivity {
    private TextView tvDrinkDescription;
    private TextView tvDrinkName;
    private ImageView ivDrinkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        initializeLocals();
        fillData(getIntent());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void fillData(Intent intent) {
        SBuzzSQLiteHelper sqLiteHelper = SBuzzSQLiteHelper.getInstance(this);
        SQLiteDatabase sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        int _id = intent.getIntExtra(DrinksAdapter.SELECTED_DRINK_ID, 0);
        DrinkEntity drinkEntity = sqLiteHelper.getDrink(sqLiteDatabase, _id);
        assert drinkEntity != null;
        tvDrinkDescription.setText(getResources().getText(drinkEntity.getDescriptionId()));
        tvDrinkName.setText(drinkEntity.getName());
        ivDrinkImage.setImageDrawable(getResources().getDrawable(drinkEntity.getDrawableId()));
    }

    private void initializeLocals() {
        ivDrinkImage = findViewById(R.id.ivDrink);
        tvDrinkDescription = findViewById(R.id.tvDrinkDescription);
        tvDrinkName = findViewById(R.id.tvDrinkName);
    }
}
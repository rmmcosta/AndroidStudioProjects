package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingItems extends AppCompatActivity {
    public static final String SHOPPING_ITEM = "ShoppingItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_items);
    }

    public void addTomatoes(View view) {
        replyWithItem("Tomatoes");
    }

    public void addApples(View view) {
        replyWithItem("Apples");
    }

    public void addRice(View view) {
        replyWithItem("Rice");
    }

    public void addCheese(View view) {
        replyWithItem("Cheese");
    }

    private void replyWithItem(String item) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(SHOPPING_ITEM, item);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
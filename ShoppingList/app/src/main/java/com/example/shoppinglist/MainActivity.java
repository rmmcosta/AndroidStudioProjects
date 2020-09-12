package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_SHOPPING_ITEM_CODE = 1;
    private static final String SAVED_CART = "SavedCart";
    private List<String> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartItems = new ArrayList<>();
        validateSavedState(savedInstanceState);
    }

    private void validateSavedState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<String> savedItems = savedInstanceState.getStringArrayList(SAVED_CART);
            for (String item : savedItems) {
                populateCart(item);
            }
        }
    }

    public void go2ShoppingItems(View view) {
        Intent intent = new Intent(this, ShoppingItems.class);
        startActivityForResult(intent, REQUEST_SHOPPING_ITEM_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOPPING_ITEM_CODE && resultCode == RESULT_OK) {
            String shoppingItem = data.getStringExtra(ShoppingItems.SHOPPING_ITEM);
            populateCart(shoppingItem);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_CART, (ArrayList<String>) cartItems);
    }

    private void populateCart(String shoppingItem) {
        TextView cartTextView;
        switch (cartItems.size() % 10) {
            case 1:
                cartTextView = findViewById(R.id.tvItem2);
                break;
            case 2:
                cartTextView = findViewById(R.id.tvItem3);
                break;
            case 3:
                cartTextView = findViewById(R.id.tvItem4);
                break;
            case 4:
                cartTextView = findViewById(R.id.tvItem5);
                break;
            case 5:
                cartTextView = findViewById(R.id.tvItem6);
                break;
            case 6:
                cartTextView = findViewById(R.id.tvItem7);
                break;
            case 7:
                cartTextView = findViewById(R.id.tvItem8);
                break;
            case 8:
                cartTextView = findViewById(R.id.tvItem9);
                break;
            case 9:
                cartTextView = findViewById(R.id.tvItem10);
                break;
            default:
                cartTextView = findViewById(R.id.tvItem1);
                break;
        }
        cartTextView.setText(shoppingItem);
        cartItems.add(shoppingItem);
    }
}
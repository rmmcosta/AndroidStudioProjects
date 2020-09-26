package com.example.droidcafe_withoriginalbasicactivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.Set;

public class ConfimPurchase extends AppCompatActivity {
    private RecyclerView rvCart;

    private void showCartItems(Context context) {
        Set<Map.Entry<String, Integer>> cartItems = MainActivity.getCartItems();
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(cartItems);
        rvCart.setAdapter(cartItemsAdapter);
        rvCart.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confim_purchase);
        rvCart = findViewById(R.id.rvCart);
        showCartItems(this);
    }

    public void buy(View view) {
        Snackbar snackbar = Snackbar.make(view.getRootView(), "Buy confirmed with success!", BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
    }
}
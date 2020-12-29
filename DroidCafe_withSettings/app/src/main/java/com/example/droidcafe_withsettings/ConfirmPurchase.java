package com.example.droidcafe_withsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.Set;

public class ConfirmPurchase extends AppCompatActivity {
    private RecyclerView rvCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confim_purchase);
        rvCart = findViewById(R.id.rvCart);
        showCartItems(this);
        //SharedPreferences sharedPreferences = getSharedPreferences("com.example.droidcafe_withsettings_preferences", MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String market = sharedPreferences.getString("market", "US");
        TextView tvMarket = findViewById(R.id.tvMarket);
        tvMarket.setText(market);
    }

    private void showCartItems(Context context) {
        Set<Map.Entry<String, Integer>> cartItems = MainActivity.getCartItems();
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(cartItems);
        rvCart.setAdapter(cartItemsAdapter);
        rvCart.setLayoutManager(new LinearLayoutManager(context));
    }

    public void buy(View view) {
        Snackbar snackbar = Snackbar.make(view, "Buy confirmed with success!", BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
    }
}
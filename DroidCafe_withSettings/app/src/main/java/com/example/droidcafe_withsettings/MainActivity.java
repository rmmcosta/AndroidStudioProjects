package com.example.droidcafe_withsettings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String LOG = "MyLog";
    private static HashMap<String, Integer> cart;

    public static void initializeCart() {
        cart = new HashMap<>();
    }

    public static void addToCart(String item) {
        int qty = 1;
        if (cart.containsKey(item))
            qty += cart.get(item);
        cart.put(item, qty);
    }

    //returns true if the item was removed from the cart
    public static boolean subtractToCart(String item) {
        if (cart.containsKey(item)) {
            int qty = cart.get(item);
            if (qty > 1) {
                cart.put(item, qty - 1);
                return false;
            } else {
                cart.remove(item);
                return true;
            }
        }
        return false;
    }

    public static Set<Map.Entry<String, Integer>> getCartItems() {
        return cart.entrySet();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPurchase(view);
            }
        });

        initializeCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmPurchase(View view) {
        if (cart.isEmpty()) {
            Snackbar snackbar = Snackbar.make(view, "Your cart is empty!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.RED);
            snackbar.show();
        } else {
            Intent intent = new Intent(this, ConfirmPurchase.class);
            startActivity(intent);
        }
    }
}
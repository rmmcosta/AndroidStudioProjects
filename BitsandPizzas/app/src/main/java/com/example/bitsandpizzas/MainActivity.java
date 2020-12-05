package com.example.bitsandpizzas;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    private ShareActionProvider shareActionProvider;

    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    private NavigationView nvDrawer;
    private String[] drawerCategoriesArray;

    class DrawerOnClick implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            int itemId = item.getItemId();
            if (itemId == R.id.niPasta) {
                fragment = new PastaFragment();
            } else if (itemId == R.id.niPizzas) {
                fragment = new PizzaFragment();
            } else if (itemId == R.id.niStores) {
                fragment = new StoreFragment();
            } else {
                fragment = new TopFragment();
            }
            loadFragment(fragment);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDrawer();
        initializeDefaultFragment();
    }

    private void initializeDefaultFragment() {
        loadFragment(new TopFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void initializeDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        frameLayout = findViewById(R.id.frameLayout);
        nvDrawer = findViewById(R.id.navigationView);
        nvDrawer.setNavigationItemSelectedListener(new DrawerOnClick());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem shareItem = menu.findItem(R.id.shareActionMenuOption);
        shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        defineIntent();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.createOrderMenuOption) {
            showToast("Create Order");
            launchOrderActivity();
            return true;
        } else if (itemId == R.id.settingsMenuOption) {
            showToast("Settings");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void defineIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "My share");
        intent.putExtra(Intent.EXTRA_SUBJECT, "The subject");
        intent.putExtra(Intent.EXTRA_TEXT, "This is my share to the world");
        shareActionProvider.setShareIntent(intent);
    }

    private void launchOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }
}
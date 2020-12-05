package com.example.bitsandpizzas;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    private NavigationView nvDrawer;
    private ActionBar actionBar;
    private Menu optionsMenu;
    private CharSequence prevActionBarTitle;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Fragment fragment;
    private static final String ACTION_BAR_TITLE_KEY = "ACTION_BAR_TITLE_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private FragmentManager fragmentManager;
    private static final String LOG_TAG = "LOG_MAIN_ACTIVITY";
    private static final String CURRENT_FRAGMENT_TAG = "CURRENT_FRAGMENT_TAG";
    private int position = -1;
    private int prevPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFragmentManager();
        initializeDrawer();
        initializeDrawerListener();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        initializeDefaultFragment();
        if (savedInstanceState != null && savedInstanceState.getInt(POSITION_KEY) != -1) {
            position = savedInstanceState.getInt(POSITION_KEY);
        }
        if (savedInstanceState != null && savedInstanceState.getString(ACTION_BAR_TITLE_KEY) != null) {
            String title = savedInstanceState.getString(ACTION_BAR_TITLE_KEY);
            changeActionBarTitle(title);
            loadFragment(title);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTION_BAR_TITLE_KEY, (String) actionBar.getTitle());
        outState.putInt(POSITION_KEY, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        optionsMenu = menu;
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
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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

    private void initializeFragmentManager() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(() -> {
            Fragment currVisibleFragment = fragmentManager.findFragmentByTag(CURRENT_FRAGMENT_TAG);
            int itemId = R.id.niHome;
            if (currVisibleFragment instanceof PastaFragment) {
                itemId = R.id.niPasta;
            } else if (currVisibleFragment instanceof PizzaFragment) {
                itemId = R.id.niPizzas;
            } else if (currVisibleFragment instanceof StoreFragment) {
                itemId = R.id.niStores;
            }
            if (position != getPositionByItemId(itemId)) {
                prevPosition = position;
                position = getPositionByItemId(itemId);
                updateNavItemChecked();
            }
            changeActionBarTitle(itemId);
        });
    }

    private void initializeDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.navigationView);
        nvDrawer.setNavigationItemSelectedListener(new DrawerOnClick());
    }

    private void initializeDrawerListener() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                changeActionBarTitle(getString(R.string.app_name));
                optionsMenu.findItem(R.id.shareActionMenuOption).setVisible(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                changeActionBarTitle(prevActionBarTitle);
                optionsMenu.findItem(R.id.shareActionMenuOption).setVisible(true);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    class DrawerOnClick implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            prevPosition = position;
            position = getPositionByItemId(itemId);
            fragment = getFragmentByItemId(itemId);
            loadFragment(fragment);
            changeActionBarTitle(itemId);
            drawerLayout.closeDrawer(nvDrawer);
            return true;
        }
    }

    private int getPositionByItemId(int itemId) {
        if (itemId == R.id.niPasta) {
            return 1;
        } else if (itemId == R.id.niPizzas) {
            return 2;
        } else if (itemId == R.id.niStores) {
            return 3;
        } else {
            return 0;
        }
    }

    private Fragment getFragmentByItemId(int itemId) {
        if (itemId == R.id.niPasta) {
            return new PastaFragment();
        } else if (itemId == R.id.niPizzas) {
            return new PizzaFragment();
        } else if (itemId == R.id.niStores) {
            return new StoreFragment();
        } else {
            return new TopFragment();
        }
    }

    private void initializeDefaultFragment() {
        if (fragment == null) {
            fragment = new TopFragment();
            position = 0;
        }
        loadFragment(fragment);
    }

    private void loadFragment(String title) {
        if (getString(R.string.pasta_nav_item).equals(title)) {
            fragment = new PastaFragment();
        } else if (getString(R.string.pizzas_nav_item).equals(title)) {
            fragment = new PizzaFragment();
        } else if (getString(R.string.stores_nav_item).equals(title)) {
            fragment = new StoreFragment();
        } else {
            fragment = new TopFragment();
        }
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        updateNavItemChecked();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frameLayout, fragment, CURRENT_FRAGMENT_TAG);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void updateNavItemChecked() {
        if (position > -1)
            nvDrawer.getMenu().getItem(position).setChecked(true);
        if (prevPosition > -1)
            nvDrawer.getMenu().getItem(prevPosition).setChecked(false);
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

    private void changeActionBarTitle(CharSequence newTitle) {
        prevActionBarTitle = actionBar.getTitle();
        actionBar.setTitle(newTitle);
    }

    private void changeActionBarTitle(int itemId) {
        int newTitle = R.string.home_nav_item;
        if (itemId == R.id.niHome) {
            changeActionBarTitle(getString(R.string.app_name));
            return;
        } else if (itemId == R.id.niPasta) {
            newTitle = R.string.pasta_nav_item;
        } else if (itemId == R.id.niPizzas) {
            newTitle = R.string.pizzas_nav_item;
        } else if (itemId == R.id.niStores) {
            newTitle = R.string.stores_nav_item;
        }
        actionBar.setTitle(newTitle);
        prevActionBarTitle = actionBar.getTitle();
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }
}
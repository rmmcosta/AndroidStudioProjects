package com.example.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem shareMenuItem = menu.findItem(R.id.shareActionMenuOption);
        shareActionProvider = (ShareActionProvider) shareMenuItem.getActionProvider();
        defineIntent();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.createOrderMenuOption:
                showToast("Create Order");
                launchOrderActivity();
                return true;
            case R.id.settingsMenuOption:
                showToast("Settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
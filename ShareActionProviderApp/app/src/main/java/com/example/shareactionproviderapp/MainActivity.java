package com.example.shareactionproviderapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem shareItem = menu.findItem(R.id.shareOptionMenu);
        ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareViaEmail(myShareActionProvider);
        return super.onCreateOptionsMenu(menu);
    }

    private void shareViaEmail(ShareActionProvider shareActionProvider) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "My share");
        intent.putExtra(Intent.EXTRA_SUBJECT, "The subject");
        intent.putExtra(Intent.EXTRA_TEXT, "This is my share to the world");
        shareActionProvider.setShareIntent(intent);
    }
}
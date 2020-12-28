package com.developer.appwithsettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "LOG_MainActivity";
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = Settings.getSettingsInstance();
        TextView tvSettingsOptionStatus = findViewById(R.id.tvSettingsOptionStatus);
        tvSettingsOptionStatus.setText(String.format(getString(R.string.settings_option_feedback_message), settings.isSettingsOptionText()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String msg = String.format("Menu %s with id = %s", item.getTitle(), item.getItemId());
        Log.d(LOG_TAG, msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Class<?> cls;
        if (item.getItemId() == R.id.settingsMenuItem) {
            cls = SettingsActivity.class;
        } else if (item.getItemId() == R.id.settings2MenuItem) {
            cls = SettingsActivity2.class;
        } else if (item.getItemId() == R.id.settings3MenuItem) {
            cls = SettingsActivity3.class;
        } else {
            cls = MainActivity.class;
        }
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
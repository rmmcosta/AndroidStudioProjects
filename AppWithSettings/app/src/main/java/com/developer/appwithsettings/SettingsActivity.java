package com.developer.appwithsettings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopBarColorChangeListener.applyPreferenceTheme(this);
        setContentView(R.layout.activity_settings);
        settings = Settings.getSettingsInstance();
        SwitchCompat switchCompat = findViewById(R.id.switchSettingsOption);
        switchCompat.setChecked(settings.isSettingsOption());
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            settings.setSettingsOption(b);
            String msg = String.format(getString(R.string.settings_option_feedback_message), settings.isSettingsOptionText());
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }
}
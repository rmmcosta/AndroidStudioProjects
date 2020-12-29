package com.developer.appwithsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class TopBarColorChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String LOG_TAG = "LOG_tbColorChange";
    private final Context context;
    private final AppCompatActivity activity;

    public TopBarColorChangeListener(Context context, AppCompatActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.d(LOG_TAG, String.format("onSharedPreferenceChanged - preference: %s", s));
        if (!s.equals("top_bar_color")) {
            Log.d(LOG_TAG, "preference not the top bar color");
            return;
        }
        applyPreferenceTheme(context);
        activity.recreate();
    }

    public static void applyPreferenceTheme(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String preference = sharedPreferences.getString("top_bar_color", "#FF6200EE");
        if (preference == null) {
            return;
        }
        Log.d(LOG_TAG, String.format("Preference: %s", preference));
        int styleId = R.style.Theme_AppWithSettings;
        if (preference.equals("#2196F3"))
            styleId = R.style.Theme_AppWithSettings_Blue;
        if (preference.equals("#F44336"))
            styleId = R.style.Theme_AppWithSettings_Red;
        if (preference.equals("#4CAF50"))
            styleId = R.style.Theme_AppWithSettings_Green;
        if (preference.equals("#000000"))
            styleId = R.style.Theme_AppWithSettings_Black;
        if (preference.equals("#FFFFFF"))
            styleId = R.style.Theme_AppWithSettings_White;
        context.getTheme().applyStyle(styleId, true);
    }
}

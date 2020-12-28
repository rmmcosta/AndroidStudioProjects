package com.developer.appwithsettings;

public class Settings {
    private boolean settingsOption;
    private static Settings settings;

    public static int[] icons = new int[]{R.drawable.ic_general_settings, R.drawable.ic_notifications_settings, R.drawable.ic_sync_settings};

    private Settings() {
        //singleton
    }

    public static Settings getSettingsInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public boolean isSettingsOption() {
        return settingsOption;
    }

    public String isSettingsOptionText() {
        return isSettingsOption() ? "True" : "False";
    }

    public void setSettingsOption(boolean settingsOption) {
        this.settingsOption = settingsOption;
    }
}

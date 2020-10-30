package com.example.materialme;

import android.util.Log;

public class Sport {
    private final String mSportsTitle, mSportsInfo;
    private final int mBanner;

    public Sport(String sportsTitle, String sportsInfo, int banner) {
        mSportsTitle = sportsTitle;
        mSportsInfo = sportsInfo;
        mBanner = banner;
    }

    public String getSportsTitle() {
        return mSportsTitle;
    }

    public String getSportsInfo() {
        return mSportsInfo;
    }

    public int getBanner() {
        Log.d("MyLog", "mBanner: " + mBanner);
        return mBanner;
    }
}

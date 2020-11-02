package com.example.materialme;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

public class Sport implements Parcelable {
    private final String mSportsTitle, mSportsInfo;
    private final int mBanner;

    public Sport(String sportsTitle, String sportsInfo, int banner) {
        mSportsTitle = sportsTitle;
        mSportsInfo = sportsInfo;
        mBanner = banner;
    }

    protected Sport(Parcel in) {
        mSportsTitle = in.readString();
        mSportsInfo = in.readString();
        mBanner = in.readInt();
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSportsTitle);
        parcel.writeString(mSportsInfo);
        parcel.writeInt(mBanner);
    }
}

package com.example.websource;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class WebPageLoader extends AsyncTaskLoader<String> {
    private String url;
    private boolean isSecureConnection;
    public WebPageLoader(@NonNull Context context, String url, boolean isSecureConnection) {
        super(context);
        this.url = url;
        this.isSecureConnection = isSecureConnection;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getWebPageSource(url, isSecureConnection);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

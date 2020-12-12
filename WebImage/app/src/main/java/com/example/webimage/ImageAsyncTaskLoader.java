package com.example.webimage;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class ImageAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {
    private final String url;

    public ImageAsyncTaskLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public Bitmap loadInBackground() {
        return NetworkUtils.getBitmap(url);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}

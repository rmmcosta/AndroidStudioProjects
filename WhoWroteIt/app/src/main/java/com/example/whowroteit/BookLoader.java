package com.example.whowroteit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {
    private String queryString;
    private int maxResults;

    public BookLoader(@NonNull Context context, String queryString, int maxResults) {
        super(context);
        this.queryString = queryString;
        this.maxResults = maxResults;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(queryString, maxResults);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

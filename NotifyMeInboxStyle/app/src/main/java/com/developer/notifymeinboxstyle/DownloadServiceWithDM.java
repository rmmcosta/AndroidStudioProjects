package com.developer.notifymeinboxstyle;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class DownloadServiceWithDM extends IntentService {
    private static final String SERVICE_NAME = "DownloadServiceWithDM";
    private static final String EXTRA_URL = "EXTRA_URL";
    private static final String LOG_TAG = "LOG_DownloadServiceWithDM";

    public DownloadServiceWithDM() {
        super(SERVICE_NAME);
    }

    public static Intent getIntent(@NonNull final Context context, @NonNull final String url) {
        return new Intent(context, DownloadServiceWithDM.class)
                .putExtra(EXTRA_URL, url);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(LOG_TAG, "on handle intent");
        Objects.requireNonNull(intent);
        assert (intent.getStringExtra(EXTRA_URL) != null);
        String url = intent.getStringExtra(EXTRA_URL);
        downloadImage(url);
    }

    private void downloadImage(String url) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("Download image");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, uri.getLastPathSegment());
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
}

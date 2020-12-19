package com.developer.notifymeinboxstyle;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class DownloadIntentService extends IntentService {
    private static final String LOG_TAG = "LOG_DownloadService";
    private static final String DOWNLOAD_SERVICE_NAME = "DownloadIntentService";
    private Handler handler;
    private RequestQueue requestQueue;
    private static final String REQUEST_TAG = "REQUEST_TAG";

    public DownloadIntentService() {
        super(DOWNLOAD_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "Handle download intent...");
        String imageUrl = "https://images4.alphacoders.com/118/118664.jpg";
        //the write file must happen on the main thread
        downloadAndSaveImage(imageUrl);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadAndSaveImage(String imageUrl) {
        Log.d(LOG_TAG, "download and save image");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        ImageRequest imageRequest = new ImageRequest(
                imageUrl,
                bitmap -> handler.post(() -> {
                    writeFileOnInternalStorage("Pictures", bitmap);
                }),
                3000,
                2000,
                ImageView.ScaleType.FIT_XY,
                Bitmap.Config.RGB_565,
                error -> {
                    String msg = String.format("Failed to get the image with the following exception:\n%s", error.getMessage());
                    Log.d(LOG_TAG, msg);
                });
        imageRequest.setTag(REQUEST_TAG);
        requestQueue.add(imageRequest);
    }

    private void writeFileOnInternalStorage(String dir, Bitmap image) {
        Log.d(LOG_TAG, "writeFileOnInternalStorage");
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/" + dir);

            boolean existsDirectory = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                existsDirectory = Files.exists(myDir.toPath()) && Files.isDirectory(myDir.toPath());
            } else {
                existsDirectory = myDir.exists();
            }

            if (!existsDirectory) {
                myDir.mkdirs();
            }

            String name = "download.jpg";
            myDir = new File(myDir, name);
            FileOutputStream out = new FileOutputStream(myDir);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();
            Log.d(LOG_TAG, "Image saved");
            //Toast.makeText(getApplicationContext(), myDir.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            String msg = String.format("Failed to save the image with the following exception:\n%s", e.getMessage());
            Log.d(LOG_TAG, msg);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(REQUEST_TAG);
        }
    }
}
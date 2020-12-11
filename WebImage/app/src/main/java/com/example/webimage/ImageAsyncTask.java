package com.example.webimage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    WeakReference<ImageView> ivResult;

    public ImageAsyncTask(ImageView ivResult) {
        this.ivResult = new WeakReference<>(ivResult);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ivResult.get().setImageBitmap(bitmap);
    }
}

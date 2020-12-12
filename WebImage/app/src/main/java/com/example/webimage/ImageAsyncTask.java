package com.example.webimage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    WeakReference<ImageView> ivResult;

    public ImageAsyncTask(ImageView ivResult) {
        this.ivResult = new WeakReference<>(ivResult);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return NetworkUtils.getBitmap(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ivResult.get().setImageBitmap(bitmap);
    }
}

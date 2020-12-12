package com.example.webimage;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {

    private static final String BUNDLE_URL_KEY = "BUNDLE_URL_KEY";
    private ImageView ivResult;
    private TextView tvFeedback;
    private EditText etImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivResult = findViewById(R.id.imageView);
        tvFeedback = findViewById(R.id.textView);
        etImageUrl = findViewById(R.id.etImageUrl);
        if (LoaderManager.getInstance(this).getLoader(0) != null)
            LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    public void getImage(View view) {
        hideKeyboard();
        if (!hasNetwork()) {
            showMessage(getString(R.string.no_network_message));
            return;
        }
        String url = etImageUrl.getText().toString();
        if (url.isEmpty()) {
            showMessage("It's mandatory to input the url for the image!");
            return;
        }
        showMessage(getString(R.string.network_ok_message));
        //getImageWithVolley(url);
        //getImageWithAsyncTask(url);
        getImageWithAsyncTaskLoader(url);
    }

    private void getImageWithAsyncTaskLoader(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_URL_KEY, url);
        LoaderManager.getInstance(this).restartLoader(0, bundle, this);
    }

    private void getImageWithAsyncTask(String url) {
        ImageAsyncTask imageAsyncTask = new ImageAsyncTask(ivResult);
        imageAsyncTask.execute(url);
    }

    private void getImageWithVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(url
                , response -> ivResult.setImageBitmap(response)
                , 0
                , 0
                , ImageView.ScaleType.FIT_XY
                , Bitmap.Config.RGB_565
                , error -> showMessage(error.getMessage())
        );
        requestQueue.add(imageRequest);
    }

    private void showMessage(String string) {
        Snackbar snackbar = Snackbar.make(tvFeedback, string, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private boolean hasNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void hideKeyboard() {
        toggleKeyboard(true, this.getCurrentFocus());
    }

    private void showKeyboard(View view) {
        toggleKeyboard(false, view);
    }

    private void toggleKeyboard(Boolean is2Hide, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (is2Hide)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        else
            inputMethodManager.showSoftInput(view, InputMethod.SHOW_FORCED);
    }

    public void clearInput(View view) {
        etImageUrl.setText("");
        showKeyboard(etImageUrl);
    }

    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        assert args != null;
        String url = args.getString(BUNDLE_URL_KEY);
        return new ImageAsyncTaskLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        ivResult.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }
}
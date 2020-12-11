package com.example.webimage;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ImageView ivResult;
    TextView tvFeedback;
    EditText etImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivResult = findViewById(R.id.imageView);
        tvFeedback = findViewById(R.id.textView);
        etImageUrl = findViewById(R.id.etImageUrl);
    }

    public void getImage(View view) {
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
        hideKeyboard();
        //getImageWithVolley(url);
        getImageWithAsyncTask(url);
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
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
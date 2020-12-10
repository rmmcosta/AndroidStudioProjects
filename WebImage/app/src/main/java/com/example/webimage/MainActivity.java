package com.example.webimage;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
    }

    public void getImage(View view) {
        if (!hasNetwork()) {
            showMessage(getString(R.string.no_network_message));
            return;
        }
        String url = textView.getText().toString();
        if (url.isEmpty()) {
            showMessage("It's mandatory to input the url for the image!");
            return;
        }
        showMessage(getString(R.string.network_ok_message));
        getImageWithVolley();
    }

    private void getImageWithVolley() {
        ImageRequest imageRequest = new ImageRequest();
    }

    private void showMessage(String string) {
        Snackbar snackbar = Snackbar.make(textView, string, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private boolean hasNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
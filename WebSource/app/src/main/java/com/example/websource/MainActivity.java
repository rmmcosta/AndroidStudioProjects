package com.example.websource;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String BUNDLE_URL_KEY = "BUNDLE_URL_KEY";
    private static final String LOG_TAG = "LOG_MAIN_ACTIVITY";
    private static final String SECURE_PROTOCOL = "HTTPS";
    private TextView tvResult;
    private EditText etInputtedUrl;
    private Spinner spinner;
    private Button btnGetPageSource;

    private Loader.OnLoadCompleteListener completeListener = (loader, data) -> showResult((String) data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        etInputtedUrl = findViewById(R.id.etURL);
        spinner = findViewById(R.id.spinner);
        btnGetPageSource = findViewById(R.id.btnGetPageSource);
        if (LoaderManager.getInstance(this).getLoader(0) != null)
            LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    public void getPageSource(View view) {
        clearResult();
        if (etInputtedUrl.getText().toString().isEmpty()) {
            showResult("the url is mandatory!");
            return;
        }
        if (!hasNetwork()) {
            showResult("No network connection available!");
            return;
        }
        showLoading();
        String url = getInputtedUrl();
        //pageVolley(url);
        //pageLoader(url);
        pageLoader2(url);
    }

    private boolean hasNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void pageLoader2(String url) {
        WebPageLoader2 webPageLoader2 = new WebPageLoader2(this, url);
        webPageLoader2.registerListener(0, completeListener);
        webPageLoader2.forceLoad();
    }

    private void showLoading() {
        btnGetPageSource.setEnabled(false);
        btnGetPageSource.setText(R.string.loading_text);
    }

    private void stopLoading() {
        btnGetPageSource.setEnabled(true);
        btnGetPageSource.setText(R.string.get_page_source_btn);
    }

    private String getInputtedUrl() {
        String protocol = spinner.getSelectedItem().toString();
        String url = String.format("%s%s", protocol, etInputtedUrl.getText().toString());
        Log.d(LOG_TAG, String.format("url: %s", url));
        return url;
    }

    private void showResult(String response) {
        tvResult.setText(response);
        stopLoading();
    }

    private void clearResult() {
        tvResult.setText("");
    }

    private boolean isSecureProtocol() {
        String protocol = spinner.getSelectedItem().toString();
        return protocol.toUpperCase().contains(SECURE_PROTOCOL.toUpperCase());
    }

    private void pageVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                (Response.Listener<String>) response -> {
                    showResult(response);
                }, error -> {
            showResult(error.getMessage());
        });
        requestQueue.add(stringRequest);
    }

    private void pageLoader(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_URL_KEY, url);
        LoaderManager.getInstance(this).restartLoader(0, bundle, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "on create loader");
        String url = null;
        if (args != null) {
            url = args.getString(BUNDLE_URL_KEY);
        }
        return new WebPageLoader(this, url, isSecureProtocol());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d(LOG_TAG, "on load finish");
        showResult(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


}
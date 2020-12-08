package com.example.whowroteit;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private EditText etBookName;
    private TextView tvAuthor;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private Button btnSearch;
    private static final String LOG_TAG = "LOG_MAIN_ACTIVITY";
    private static final String JSON_ITEMS_KEY = "items";
    private static final String JSON_TITLE_KEY = "title";
    private static final String JSON_SUBTITLE_KEY = "subtitle";
    private static final String JSON_AUTHOR_KEY = "authors";
    private static final String JSON_VOLUME_INFO_KEY = "volumeInfo";
    private static final String BUNDLE_QUERY_STRING = "QUERY_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etBookName = findViewById(R.id.etBookName);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        btnSearch = findViewById(R.id.btnSearchBooks);
        if (LoaderManager.getInstance(this) != null) {
            LoaderManager.getInstance(this).initLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString(BUNDLE_QUERY_STRING);
        }
        BookLoader bookLoader = new BookLoader(this, queryString, 10);
        ;
        return bookLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        updateResult(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void searchBooks(View view) {
        hideKeyboard(view);
        clearResult();
        if (!hasConnectivity()) {
            updateResult("No internet");
            Log.d(LOG_TAG, "No internet");
            return;
        }
        showLoading();
        String inputtedBookName = etBookName.getText().toString();
        //String finalUrl = String.format("%s%s", BASE_URL, inputtedBookName);
        //volleyRequest(finalUrl);
        //searchBookAsyncRequest(finalUrl);
        //asyncFetchBook(inputtedBookName);
        loaderFetchBook(inputtedBookName);
    }

    private void loaderFetchBook(String inputtedBookName) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_QUERY_STRING, inputtedBookName);
        LoaderManager.getInstance(this).restartLoader(0, bundle, this);
    }

    private boolean hasConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean hasConnectivity = networkInfo != null && networkInfo.isConnected();
        Log.d(LOG_TAG, String.format("Has connectivity: %b", hasConnectivity));
        return hasConnectivity;
    }

    private void showLoading() {
        btnSearch.setText(R.string.btn_loading);
        btnSearch.setEnabled(false);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void clearResult() {
        tvTitle.setText("");
        tvSubtitle.setText("");
        tvAuthor.setText("");
    }

    private void asyncFetchBook(String inputtedBookName) {
        new AsyncFetchBook().execute(inputtedBookName);
    }

    private void searchBookAsyncRequest(String finalUrl) {
        new SearchBooksAsyncTask().execute(finalUrl);
    }
    //Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster.

    private void volleyRequest(String finalUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
                response -> {
                    // Display the first 500 characters of the response string.
                    updateResult(response);
                }, error -> updateResult("That didn't work!"));
        requestQueue.add(stringRequest);
    }

    private class SearchBooksAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer response = new StringBuffer();
            try {
                URL obj = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                Log.d(LOG_TAG, "GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } else {
                    response.append("GET request not worked");
                }
            } catch (Exception e) {
                response.append(e.getMessage());
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            updateResult(s);
        }

    }

    private class AsyncFetchBook extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d(LOG_TAG, "Async fetch book");
            return NetworkUtils.getBookInfo(strings[0], 10);
        }

        @Override
        protected void onPostExecute(String s) {
            updateResult(s);
        }

    }

    private void updateResult(String s) {
        Book book = parseJsonBook(s);
        if (book == null) {
            tvTitle.setText((s == null || s.isEmpty()) ? "Book not found" : s);
        } else {
            tvTitle.setText(book.getTitle());
            tvSubtitle.setText(book.getSubtitle());
            tvAuthor.setText(book.getAuthors());
        }
        stopLoading();
    }

    private void stopLoading() {
        btnSearch.setText(R.string.search_books_button);
        btnSearch.setEnabled(true);
    }

    private Book parseJsonBook(String s) {
        if (s == null || s.isEmpty()) {
            Log.d(LOG_TAG, "Response is empty");
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_ITEMS_KEY);
            Log.d(LOG_TAG, String.format("jsonArray length: %d", jsonArray.length()));
            //return the first record found with author and title
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObject = jsonArray.getJSONObject(i);
                JSONObject volumeInfoObject = itemObject.getJSONObject(JSON_VOLUME_INFO_KEY);
                try {
                    String title = volumeInfoObject.getString(JSON_TITLE_KEY);
                    String authors = volumeInfoObject.getString(JSON_AUTHOR_KEY);
                    String subtitle = "N/A";
                    if (volumeInfoObject.has(JSON_SUBTITLE_KEY))
                        subtitle = volumeInfoObject.getString(JSON_SUBTITLE_KEY);
                    Log.d(LOG_TAG, String.format("title: %s, subtitle: %s, authors: %s", title, subtitle, authors));
                    if (!title.isEmpty() && !authors.isEmpty()) {
                        return new Book(title, subtitle, authors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, e.getMessage());
            return null;
        }
    }
}
package com.example.whowroteit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private EditText etBookName;
    private TextView tvResult;
    private static final String LOG_TAG = "LOG_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etBookName = findViewById(R.id.etBookName);
        tvResult = findViewById(R.id.tvResult);
    }

    public void searchBooks(View view) {
        String inputtedBookName = etBookName.getText().toString();
        String finalUrl = String.format("%s%s", BASE_URL, inputtedBookName);
        //volleyRequest(finalUrl);
        new SearchBooksAsyncTask().execute(finalUrl);
    }
    //Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster.
    private void volleyRequest(String finalUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrl,
                response -> {
                    // Display the first 500 characters of the response string.
                    updateResult("Response is: " + response.substring(0, 500));
                }, error -> updateResult("That didn't work!"));
        requestQueue.add(stringRequest);
    }

    private void updateResult(String s) {
        tvResult.setText(s);
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
                Log.d(LOG_TAG,"GET Response Code :: " + responseCode);
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

}
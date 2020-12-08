package com.example.websource;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    private static final String REQUEST_METHOD = "GET";
    private static final String LOG_TAG = "LOG_NETWORK_UTILS";

    public static String getWebPageSource(String webPage, boolean isSecure) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String result;
        try {
            URL url = new URL(webPage);
            Log.d(LOG_TAG, String.format("url: ", url.toString()));
            if (isSecure) {
                urlConnection = (HttpsURLConnection) url.openConnection();
            } else {
                urlConnection = (HttpURLConnection) url.openConnection();
            }
            urlConnection.setRequestMethod(REQUEST_METHOD);
            urlConnection.connect();
            Log.d(LOG_TAG, "connected");
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
            Log.d(LOG_TAG, String.format("result: %s", result.substring(0, 100)));
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            urlConnection.disconnect();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = e.getMessage();
            }
        }
        return result;
    }
}

package com.example.websource;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.loader.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebPageLoader2 extends Loader<String> {
    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    private final Context context;
    private final String url;

    public WebPageLoader2(@NonNull Context context, @NonNull String url) {
        super(context);
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onForceLoad() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                (Response.Listener<String>) this::deliverResult, error -> {
            deliverResult(error.getMessage());
        });
        requestQueue.add(stringRequest);
    }
}

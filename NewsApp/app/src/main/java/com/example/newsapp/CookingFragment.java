package com.example.newsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class CookingFragment extends Fragment {
    public CookingFragment() {
        // Required empty public constructor
        Log.d("MyLog","cooking fragment constructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("MyLog", "On create view cooking fragment");
        return inflater.inflate(R.layout.fragment_cooking, container, false);
    }
}
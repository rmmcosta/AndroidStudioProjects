package com.example.bitsandpizzas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.ListFragment;

public class StoreFragment extends ListFragment {

    public StoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] stores = getResources().getStringArray(R.array.stores);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, stores);
        setListAdapter(arrayAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
package com.example.bitsandpizzas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PizzaFragment extends ListFragment {

    public PizzaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] pizzas = getResources().getStringArray(R.array.pizzas);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, pizzas);
        setListAdapter(arrayAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
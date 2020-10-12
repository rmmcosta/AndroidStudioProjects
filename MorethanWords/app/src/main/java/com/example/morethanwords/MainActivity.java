package com.example.morethanwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mainRecyclerView = findViewById(R.id.MainRecyclerView);
        RecyclerView.Adapter wordsAdapter = new WordsAdapter();
        mainRecyclerView.setAdapter(wordsAdapter);
    }

    public void addWord(View view) {
    }
}
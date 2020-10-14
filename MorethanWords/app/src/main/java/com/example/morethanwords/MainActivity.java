package com.example.morethanwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mWords;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeWords(5);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        mRecyclerView = findViewById(R.id.MainRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
    }

    private void setAdapter() {
        WordsAdapter mWordsAdapter = new WordsAdapter(mWords, this);
        mRecyclerView.setAdapter(mWordsAdapter);
    }

    private void initializeWords(int count) {
        mWords = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            mWords.add("My word " + i);
        }
    }

    public void addWord(View view) {
        int prevSize = mWords.size();
        mWords.add("+ My word " + prevSize);
        mRecyclerView.getAdapter().notifyItemInserted(prevSize);
        mRecyclerView.scrollToPosition(prevSize);
    }
}
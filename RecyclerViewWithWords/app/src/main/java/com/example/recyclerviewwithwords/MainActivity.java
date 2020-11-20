package com.example.recyclerviewwithwords;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final String SAVED_WORDS_KEY = "SAVED_WORDS_KEY";
    public LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    public static final int INITIAL_WORDS_NUM = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWord();
            }
        });

        if (mWordList.isEmpty())
            initializeWordsList(INITIAL_WORDS_NUM);

        mRecyclerView = findViewById(R.id.rvWords);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter();
    }

    private void setAdapter() {
        WordListAdapter wordListAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(wordListAdapter);
    }

    private void addWord() {
        int prevSize = mWordList.size();
        String word = "+ Word " + prevSize;
        mWordList.add(word);
        mRecyclerView.getAdapter().notifyItemInserted(prevSize);
        mRecyclerView.scrollToPosition(prevSize);
    }

    private void initializeWordsList(int num) {
        if (!mWordList.isEmpty()) {
            mWordList.clear();
        }
        for (int i = 0; i < num; i++) {
            mWordList.add("Word " + i);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<String> tempArrayList = savedInstanceState.getStringArrayList(SAVED_WORDS_KEY);
        for (int i = 0; i < tempArrayList.size(); i++) {
            if (i < mWordList.size()) {
                mWordList.set(i, tempArrayList.get(i));
            } else {
                mWordList.add(tempArrayList.get(i));
            }
        }
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_WORDS_KEY, new ArrayList<>(mWordList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reset) {
            initializeWordsList(INITIAL_WORDS_NUM);
            mRecyclerView.getAdapter().notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
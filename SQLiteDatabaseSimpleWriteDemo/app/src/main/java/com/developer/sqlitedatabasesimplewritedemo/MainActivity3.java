package com.developer.sqlitedatabasesimplewritedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity3 extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursorAllSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(this);
        sqLiteDatabase = simpleWriteSQLite.getReadableDatabase();
        cursorAllSimple = simpleWriteSQLite.getAllSimpleCursor(sqLiteDatabase);
        MyListCursorAdapter myListCursorAdapter = new MyListCursorAdapter(this, cursorAllSimple);
        RecyclerView rvSimpleRecords = findViewById(R.id.rvSimpleRecords2);
        rvSimpleRecords.setAdapter(myListCursorAdapter);
        rvSimpleRecords.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursorAllSimple.close();
        sqLiteDatabase.close();
    }
}
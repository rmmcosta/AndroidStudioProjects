package com.developer.sqlitedatabasesimplewritedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class MainActivity2 extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursorAllSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SimpleWriteSQLite simpleWriteSQLite = new SimpleWriteSQLite(this);
        sqLiteDatabase = simpleWriteSQLite.getReadableDatabase();
        cursorAllSimple = simpleWriteSQLite.getAllSimpleCursor(sqLiteDatabase);
        String[] from = new String[]{"_id", "NAME", "DESCRIPTION", "CREATED_ON", "FAVORITE"};
        int[] to = new int[]{R.id.tvId, R.id.tvName, R.id.tvDescription, R.id.tvTimestamp, R.id.tvFavorite};
        CursorAdapter cursorAdapterAllSimple = new SimpleCursorAdapter(this, R.layout.simple_detail, cursorAllSimple, from, to, 0);
        ListView lvSimpleRecords = findViewById(R.id.lvSimpleRecords);
        lvSimpleRecords.setAdapter(cursorAdapterAllSimple);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursorAllSimple.close();
        sqLiteDatabase.close();
    }
}
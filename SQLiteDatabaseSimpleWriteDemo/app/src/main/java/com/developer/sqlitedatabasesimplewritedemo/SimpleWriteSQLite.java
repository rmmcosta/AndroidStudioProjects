package com.developer.sqlitedatabasesimplewritedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SimpleWriteSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SIMPLE_WRITE";
    private static final int VERSION = 1;
    private static final String SIMPLE_WRITE_TABLE = "simple_write_table";

    public SimpleWriteSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSimpleWriteTable = String.format("CREATE TABLE %S (_id INTEGER PRIMARY KEY, NAME TEXT NOT NULL, DESCRIPTION TEXT, CREATED_ON TIMESTAMP DEFAULT CURRENT_TIMESTAMP)", SIMPLE_WRITE_TABLE);
        sqLiteDatabase.execSQL(createSimpleWriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertSimple(SQLiteDatabase sqLiteDatabase, String name, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("DESCRIPTION", description);
        return sqLiteDatabase.insert(SIMPLE_WRITE_TABLE, null, contentValues);
    }
}

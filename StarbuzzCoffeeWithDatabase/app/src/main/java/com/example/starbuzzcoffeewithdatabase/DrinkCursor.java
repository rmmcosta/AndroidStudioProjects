package com.example.starbuzzcoffeewithdatabase;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteQuery;

public class DrinkCursor extends SQLiteCursor {
    public DrinkCursor(SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
        super(driver, editTable, query);
    }
}

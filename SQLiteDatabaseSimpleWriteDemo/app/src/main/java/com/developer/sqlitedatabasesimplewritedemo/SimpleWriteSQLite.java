package com.developer.sqlitedatabasesimplewritedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SimpleWriteSQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SIMPLE_WRITE";
    private static final int OLD_VERSION = 1;
    private static final int VERSION = 2;
    private static final String SIMPLE_WRITE_TABLE = "simple_write_table";

    public SimpleWriteSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSimpleWriteTable = String.format("CREATE TABLE %S (_id INTEGER PRIMARY KEY, NAME TEXT NOT NULL, DESCRIPTION TEXT, CREATED_ON TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FAVORITE INTEGER DEFAULT 0)", SIMPLE_WRITE_TABLE);
        sqLiteDatabase.execSQL(createSimpleWriteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i == OLD_VERSION) {
            String alterSimpleWriteTable = String.format("ALTER TABLE %S ADD COLUMN FAVORITE INTEGER DEFAULT 0", SIMPLE_WRITE_TABLE);
            sqLiteDatabase.execSQL(alterSimpleWriteTable);
        }
    }

    public long insertSimple(String name, String description) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long id = insertSimple(sqLiteDatabase, name, description);
        sqLiteDatabase.close();
        return id;
    }

    public long insertSimple(SQLiteDatabase sqLiteDatabase, String name, String description) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("DESCRIPTION", description);
        return sqLiteDatabase.insert(SIMPLE_WRITE_TABLE, null, contentValues);
    }

    public long countSimple() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sqlCount = String.format("Select count(1) from %s", SIMPLE_WRITE_TABLE);
        Cursor cursor = sqLiteDatabase.rawQuery(sqlCount, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

    public List<Simple> getSimpleList() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        Cursor cursor = sqLiteDatabase.query(SIMPLE_WRITE_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy);
        List<Simple> simpleList = new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            simpleList.add(new Simple(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4) == 1));
            cursor.moveToNext();
        }
        cursor.close();
        sqLiteDatabase.close();
        return simpleList;
    }

    public Cursor getAllSimpleCursor(SQLiteDatabase sqLiteDatabase) {
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        return sqLiteDatabase.query(SIMPLE_WRITE_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public void updateFavorite(int id, boolean favorite) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FAVORITE", favorite ? 1 : 0);
        sqLiteDatabase.update(SIMPLE_WRITE_TABLE, contentValues, "_id=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}

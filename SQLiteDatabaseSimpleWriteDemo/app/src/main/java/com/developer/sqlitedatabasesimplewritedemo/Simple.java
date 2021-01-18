package com.developer.sqlitedatabasesimplewritedemo;

import android.database.Cursor;

public class Simple {
    private static final int CURSOR_COLUMN_COUNT = 5;
    private final long id;
    private final String name;
    private String description;
    private final String timestamp;
    private boolean favorite;

    public Simple(long id, String name, String description, String timestamp, boolean favorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public static Simple fromCursor(Cursor cursor) {
        assert (cursor.getColumnCount() == CURSOR_COLUMN_COUNT);
        assert (!cursor.isClosed());
        assert (!cursor.isAfterLast());
        assert (cursor.getCount() > 0);
        return new Simple(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4) == 1);
    }
}

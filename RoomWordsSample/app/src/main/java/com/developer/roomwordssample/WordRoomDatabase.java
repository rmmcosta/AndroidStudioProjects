package com.developer.roomwordssample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase wordRoomDatabaseInstance;

    public abstract WordDao wordDao();

    public static WordRoomDatabase getWordRoomDatabaseInstance(final Context context) {
        if (wordRoomDatabaseInstance == null) {
            synchronized (WordRoomDatabase.class) {
                if (wordRoomDatabaseInstance == null) {
                    wordRoomDatabaseInstance = Room
                            .databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return wordRoomDatabaseInstance;
    }
}

package com.developer.roomwordssample;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase wordRoomDatabaseInstance;
    private static final String[] initialWords = new String[]{"Ana", "Rui", "Ricardo", "Carmélia"};

    public abstract WordDao wordDao();

    public static WordRoomDatabase getWordRoomDatabaseInstance(final Context context) {
        if (wordRoomDatabaseInstance == null) {
            synchronized (WordRoomDatabase.class) {
                if (wordRoomDatabaseInstance == null) {
                    wordRoomDatabaseInstance = Room
                            .databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    Executor executor = Executors.newSingleThreadExecutor();
                                    executor.execute(() -> {
                                        SharedPreferences sharedPreferencesDefault = PreferenceManager.getDefaultSharedPreferences(context);
                                        restoreDatabase(sharedPreferencesDefault);
                                    });
                                }


                            })
                            .build();
                }
            }
        }
        return wordRoomDatabaseInstance;
    }

    public static SharedPreferences.OnSharedPreferenceChangeListener getOnSharedPreferenceChangeListener() {
        return (sharedPreferences, s) -> restoreDatabase(sharedPreferences);
    }

    private static void restoreDatabase(SharedPreferences sharedPreferencesDefault) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean restoreDatabase = sharedPreferencesDefault.getBoolean("restore_database", false);
            boolean useDatabase = sharedPreferencesDefault.getBoolean("use_database", false);
            if (!restoreDatabase || !useDatabase) {
                return;
            }
            wordRoomDatabaseInstance.wordDao().deleteAll();
            for (String each : initialWords) {
                wordRoomDatabaseInstance.wordDao().insert(new Word(each));
            }
        });

    }
}

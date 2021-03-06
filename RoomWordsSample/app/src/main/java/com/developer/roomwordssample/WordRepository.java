package com.developer.roomwordssample;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WordRepository {
    private static final String LOG_TAG = "LOG_WordRepository";
    private final WordDao wordDao;
    private final LiveData<List<Word>> wordLiveList;

    public WordRepository(Application application) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        boolean useDatabase = sharedPreferences.getBoolean("use_database", false);
        if (useDatabase) {
            Log.d(LOG_TAG, "Use database");
            WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getWordRoomDatabaseInstance(application);
            wordDao = wordRoomDatabase.wordDao();
        } else {
            Log.d(LOG_TAG, "Don't use the database");
            wordDao = new WordEntityLiveData(WordEntity.getInstance());
        }
        wordLiveList = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return wordLiveList;
    }

    public void insert(Word word) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> wordDao.insert(word));
    }

    public Word getWord(String word) {
        try {
            return wordDao.getWord(word).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteWord(String word) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> wordDao.delete(word));
    }

    public void deleteAll() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(wordDao::deleteAll);
    }
}

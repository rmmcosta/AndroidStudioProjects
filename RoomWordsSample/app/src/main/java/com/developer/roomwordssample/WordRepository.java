package com.developer.roomwordssample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WordRepository {
    private final WordDao wordDao;
    private final LiveData<List<Word>> wordLiveList;

    public WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getWordRoomDatabaseInstance(application);
        wordDao = wordRoomDatabase.wordDao();
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
        executor.execute(() -> {
            wordDao.delete(word);
        });
    }
}

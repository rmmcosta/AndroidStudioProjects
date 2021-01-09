package com.developer.roomwordssample;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WordEntityLiveData extends LiveData<List<Word>> implements WordDao {
    private static final String LOG_TAG = "LOG_WordEntityLiveData";
    private final WordEntity wordEntity;

    public WordEntityLiveData(WordEntity wordEntity) {
        super(wordEntity.getAllWords());
        this.wordEntity = wordEntity;
    }

    @Nullable
    @Override
    public List<Word> getValue() {
        return wordEntity.getAllWords();
    }

    @Override
    public void insert(Word word) {
        wordEntity.insert(word);
    }

    @Override
    public void deleteAll() {
        wordEntity.deleteAll();
    }

    @Override
    public void delete(String word) {
        wordEntity.delete(word);
    }

    @Override
    public LiveData<List<Word>> getAllWords() {
        return this;
    }

    @Override
    public ListenableFuture<Word> getWord(String word) {
        return new ListenableFuture<Word>() {
            @Override
            public void addListener(Runnable listener, Executor executor) {

            }

            @Override
            public boolean cancel(boolean b) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public Word get() throws ExecutionException, InterruptedException {
                Log.d(LOG_TAG, "listenable future get");
                if (wordEntity.getAllWords() == null) {
                    return null;
                }
                for (Word each :
                        wordEntity.getAllWords()) {
                    if (each.getWord().equals(word)) {
                        return each;
                    }
                }
                return null;
            }

            @Override
            public Word get(long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return null;
            }
        };
    }
}

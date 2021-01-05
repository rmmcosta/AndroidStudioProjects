package com.developer.roomwordssample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private final WordRepository wordRepository;
    private final LiveData<List<Word>> wordLiveList;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        wordLiveList = wordRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return wordLiveList;
    }

    public void insert(Word word) {
        wordRepository.insert(word);
    }

    public Word getWord(String word) {
        return wordRepository.getWord(word);
    }
}

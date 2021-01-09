package com.developer.roomwordssample;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

public class WordEntity {
    private static final String LOG_TAG = "LOG_WordEntity";
    private List<Word> words;
    private static WordEntity wordEntity;

    private WordEntity() {
        //singleton pattern
    }

    public static WordEntity getInstance() {
        Log.d(LOG_TAG, "get word entity instance");
        if (wordEntity == null) {
            wordEntity = new WordEntity();
        }
        return wordEntity;
    }

    public void insert(Word word) {
        Log.d(LOG_TAG, "word entity - insert word");
        if (words == null) {
            words = new ArrayList<>();
        }
        words.add(word);
    }

    public void deleteAll() {
        words.clear();
    }

    public List<Word> delete(String word) {
        Log.d(LOG_TAG, "word entity - delete " + word);
        Log.d(LOG_TAG, "number of words before delete:" + words.size());
        int position = -1;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWord().equals(word)) {
                Log.d(LOG_TAG, "word entity - found to delete word " + word);
                position = i;
            }
        }
        if (position != -1) {
            words.remove(position);
        }
        Log.d(LOG_TAG, "number of words after delete:" + words.size());
        return words;
    }

    public List<Word> getAllWords() {
        Log.d(LOG_TAG, "word entity - get all words");
        return words;
    }

    public Word getWord(String word) {
        for (Word each :
                words) {
            if (each.getWord().equals(word)) {
                return each;
            }
        }
        return null;
    }

    public Word getWord(int position) {
        if (words == null) {
            throw new EmptyStackException();
        }
        if (position >= words.size()) {
            throw new IndexOutOfBoundsException();
        }
        return words.get(position);
    }
}

package com.developer.roomwordssample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;

public class WordEntity {
    private List<Word> words;
    private static WordEntity wordEntity;

    private WordEntity() {
        //singleton pattern
    }

    public static WordEntity getInstance() {
        if (wordEntity == null) {
            wordEntity = new WordEntity();
        }
        return wordEntity;
    }

    public void addWord(Word word) {
        if (words == null) {
            words = new ArrayList<>();
        }
        words.add(word);
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

    public List<Word> getWords() {
        if (words == null) {
            return Collections.emptyList();
        }
        return words;
    }
}

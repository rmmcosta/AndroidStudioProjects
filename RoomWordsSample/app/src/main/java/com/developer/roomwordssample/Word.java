package com.developer.roomwordssample;

import androidx.annotation.NonNull;

public class Word {
    private final String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @NonNull
    @Override
    public String toString() {
        return getWord();
    }
}

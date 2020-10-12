package com.example.morethanwords;

import java.util.ArrayList;
import java.util.List;

public class Words {
    private List<String> words;

    public Words() {
        this.words = new ArrayList<>();
    }

    public void addWord(String word) {
        words.add(word);
    }

    public String getWord(int position) {
        return words.get(position);
    }

    public int getSize() {
        return words.size();
    }
}

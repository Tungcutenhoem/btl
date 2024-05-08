package com.example.demo;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList();

    public Dictionary() {
    }

    public void add_Word(String word_target, String word_explain) {
        Word new_word = new Word(word_target, word_explain);
        this.words.add(new_word);
    }

    public void setNew_world(ArrayList<Word> newWorld) {
        this.words = this.words;
    }

    public ArrayList<Word> getNew_world() {
        return this.words;
    }
}

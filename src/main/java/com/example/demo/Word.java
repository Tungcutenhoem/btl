
package com.example.demo;

public class Word {
    private String word_target;
    private String word_explain;

    public Word(String word_target, String word_explain) {
        this.word_explain = word_explain;
        this.word_target = word_target;
    }

    public void setWord_target() {
        this.word_target = this.word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_target() {
        return this.word_target;
    }

    public String getWord_explain() {
        return this.word_explain;
    }
}
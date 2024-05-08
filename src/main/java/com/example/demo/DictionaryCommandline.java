package com.example.demo;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryCommandline;

    public DictionaryCommandline() {
    }

    public void showAllWorlds() {
        ArrayList<Word> new_word = this.dictionaryCommandline.getNew_world();
        Collections.sort(new_word, Comparator.comparing(Word::getWord_target));
        Iterator var2 = new_word.iterator();

        while(var2.hasNext()) {
            Word word = (Word)var2.next();
            PrintStream var10000 = System.out;
            String var10001 = word.getWord_explain();
            var10000.println(var10001 + " " + word.getWord_target());
        }

    }
}

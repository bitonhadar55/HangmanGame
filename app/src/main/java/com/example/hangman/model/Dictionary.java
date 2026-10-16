package com.example.hangman.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class Dictionary {
    private ArrayList<String> wordList;
    private ListIterator<String> current;

    public Dictionary() {
        wordList = new ArrayList<String>();
        addWord("BANANA");
        addWord("ANDROID");
        addWord("JAVA");
        addWord("ACTIVITY");
        addWord("LAYOUT");
        addWord("BUTTON");

        Collections.shuffle(wordList);
        this.current = this.wordList.listIterator();
    }

    public String getRandomWord(){
        if(!this.current.hasNext()){
            Collections.shuffle(wordList);
            this.current = this.wordList.listIterator();
        }
        return this.current.next();
    }

    public void addWord(String word){
        this.wordList.add(word.toUpperCase());
    }
}

package com.example.hangman.model;

public class Word {
    public String word;
    public char[] displayWord; // The word to display to the user

    // Constructor
    public Word(String word) {
        this.word = word;
        this.displayWord = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            this.displayWord[i] = '_';
        }
    }

    // Method to check if a letter is in the word
    public boolean checkLetter(char letter) {
        boolean found = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                displayWord[i] = letter;
                found = true;
            }
        }
        return found;
    }

    // Method to check if the word has been guessed
    public boolean check4Victory() {
        for (char c : displayWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    public String getDisplayWord() {
        return new String(this.displayWord);
    }
}

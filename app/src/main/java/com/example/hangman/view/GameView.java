package com.example.hangman.view;

public interface GameView {

    // misses = number of wrong guesses (0..9)
    void drawHungMan(int misses);

    // one cell per letter: 0 = not guessed, 1 = correct, -1 = wrong
    void updateLetters(int[] guessed);

    // the word, with '_' for every letter not guessed yet
    void updateDisplayWord(String displayWord);
}


package com.example.hangman.ViewModel;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hangman.model.Dictionary;
import com.example.hangman.model.Word;
import com.example.hangman.view.GameView;

public class GameViewModel {
    public static final int MAX_MISSES = 9;  // 10 images: 0..9
    private GameView view;          // the screen (MainActivity)
    private Dictionary dictionary;  // all the words
    private Word current;           // the word of this game
    private int misses;             // wrong guesses so far
    private String displayWord;     // e.g. "BA_A_A"
    private char status;            // 'P' playing, 'V' won, 'L' lost
    private int[] guessed;          // 26 cells: 0 / 1 / -1

    public GameViewModel(GameView view) {
        this.view = view;
        this.dictionary = new Dictionary();
        restart();
    }
    public void restart() {
        current = new Word(dictionary.getRandomWord());
        misses = 0;
        guessed = new int[26];      // all cells are 0
        status = 'P';
        displayWord = current.getDisplayWord();

        // tell the View to redraw the screen
        view.drawHungMan(misses);
        view.updateLetters(guessed);
        view.updateDisplayWord(displayWord);
    }

    public boolean checkLetter(char c) {
        boolean found = current.checkLetter(c);  // updates the Word
        int index = c - 'A';                     // 'A' -> 0 ... 'Z' -> 25
        if (found) {
            guessed[index] = 1;
            displayWord = current.getDisplayWord();
            view.updateDisplayWord(displayWord);
            if (current.check4Victory()) {
                status = 'V';
            }
        } else {
            guessed[index] = -1;
            misses++;
            if (misses == MAX_MISSES) {
                status = 'L';
            }
            view.drawHungMan(misses);
        }
        view.updateLetters(guessed);             // in any case
        return found;
    }


    public char getGameStatus() {
    return status;}

    public String getWord() {
        return current.getWord();
    }
    public int[] getGuessed() {
        return guessed;
    }

    public void restore(String word, int[] savedGuessed) {
        current = new Word(word);
        guessed = savedGuessed;
        misses = 0;
        for (int i = 0; i < 26; i++) {
            if (guessed[i] == 1) {
                current.checkLetter((char) ('A' + i));  // reveal again
            } else if (guessed[i] == -1) {
                misses++;
            }
        }
        displayWord = current.getDisplayWord();
        status = 'P';
        if (current.check4Victory()) { status = 'V'; }
        if (misses == MAX_MISSES)    { status = 'L'; }

        view.drawHungMan(misses);
        view.updateLetters(guessed);
        view.updateDisplayWord(displayWord);
    }

}


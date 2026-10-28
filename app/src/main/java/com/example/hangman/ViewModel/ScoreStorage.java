package com.example.hangman.ViewModel;

public interface ScoreStorage {

    int loadWins();
    int loadLosses();
    void saveScore(int wins, int losses);
}


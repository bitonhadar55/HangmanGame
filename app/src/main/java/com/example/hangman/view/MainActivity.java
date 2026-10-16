package com.example.hangman.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hangman.R;
import com.example.hangman.model.Dictionary;
import com.example.hangman.model.Word;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        testWord();
        testDictionary();
    }

    private static final String TAG = "WordTest";

    private void testWord() {
        Word w = new Word("BANANA");
        Log.d(TAG, "start: " + w.getDisplayWord());              // ______

        boolean found = w.checkLetter('B');                      // true (1 time)
        Log.d(TAG, "B: " + found + " -> " + w.getDisplayWord());

        found = w.checkLetter('A');                              // true (3 times)
        Log.d(TAG, "A: " + found + " -> " + w.getDisplayWord());

        found = w.checkLetter('X');                              // false
        Log.d(TAG, "X: " + found + " -> " + w.getDisplayWord());

        Log.d(TAG, "victory? " + w.check4Victory());             // false

        found = w.checkLetter('N');                              // true (2 times)
        Log.d(TAG, "N: " + found + " -> " + w.getDisplayWord());

        Log.d(TAG, "victory? " + w.check4Victory());             // true
    }

    private void testDictionary() {
        Dictionary d = new Dictionary();
        for (int i = 1; i <= 12; i++) {
            Log.d("DictTest", i + ": " + d.getRandomWord());
        }
    }
}
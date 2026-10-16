package com.example.hangman.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hangman.R;
import com.example.hangman.model.Dictionary;
import com.example.hangman.model.Word;

public class MainActivity extends AppCompatActivity implements GameView{
    private Button[] abc = new Button['Z'-'A'+1];
    private TextView tvWord;
    private ImageView ivHangman;      // with the other data members
    private Drawable[] hungMan;       // the 10 images
    private Button btnRestart;          // with the other data members

    private int testStage = 0;
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

        btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(this::restart);


        ViewGroup grid = findViewById(R.id.lettersGrid);
        for (int i = 0; i < grid.getChildCount(); i++)
        {
            View child = grid.getChildAt(i);
            if (child instanceof Button)
            {
                abc[i] = (Button) child;
                abc[i].setOnClickListener(this::letterClick);
            }
        }

        ivHangman = findViewById(R.id.ivHangman);
        ivHangman.setOnClickListener(this::testNextImage);

        //init hangMan image array
        hungMan = new Drawable[10];
        for (int i = 0; i < 10; i++)
        {
            String drawableName = "hangman_stage_" + (i+1);
            int resID = getResources().getIdentifier(drawableName, "drawable",
                    getPackageName());
            hungMan[i] = getResources().getDrawable(resID, null);
        }
        drawHungMan(0);

        tvWord = findViewById(R.id.tvWord);
        updateDisplayWord("DANA");

        //unit tests
        testWord();
        testDictionary();
        testUpdateLetters();
    }

    // TEMPORARY - shows the next image on every tap on the image
    private void testNextImage(View v) {
        testStage++;
        if (testStage == 10) {
            testStage = 0;
        }
        drawHungMan(testStage);
    }
    // TEMPORARY version - in lesson 6 the body becomes viewModel.restart();
    private void restart(View v) {
        drawHungMan(0);              // first image
        updateLetters(new int[26]);  // all letters grey and clickable
        updateDisplayWord("_____");  // a hidden 5-letter word
    }


    private void testUpdateLetters()
    {
        int[] guessed = new int[26];   // a new int array is filled with 0
        guessed[0] = 1;                // A - correct
        guessed[1] = 1;                // B - correct
        guessed[2] = -1;               // C - wrong
        guessed[3] = -1;               // D - wrong
        updateLetters(guessed);

    }

    private void letterClick(View v) {
        Button btn = (Button) v;

        // הצגת האות בהודעת Toast
        Toast.makeText(
                this,
                btn.getText().toString(),
                Toast.LENGTH_SHORT
        ).show();

        // נטרול הכפתור לאחר הלחיצה
        btn.setClickable(false);

        // שינוי צבע הרקע לאדום
        btn.setBackgroundColor(Color.RED);
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

    // end of game - no more letter clicks
    private void disableKeyboard() {
        for (int i = 0; i < 26; i++) {
            abc[i].setClickable(false);
        }
    }


    @Override
    public void drawHungMan(int misses) {
        ivHangman.setImageDrawable(hungMan[misses]);
    }


    @Override
    public void updateLetters(int[] guessed) {
        for (int i = 0; i < abc.length; i++) {
            if (guessed[i] == 1) {              // correct guess
                abc[i].setBackgroundColor(Color.GREEN);
                abc[i].setClickable(false);
            } else if (guessed[i] == -1) {      // wrong guess
                abc[i].setBackgroundColor(Color.RED);
                abc[i].setClickable(false);
            } else {                            // not guessed yet
                abc[i].setBackgroundColor(Color.LTGRAY);
                abc[i].setClickable(true);
            }
        }
    }


    @Override
    public void updateDisplayWord(String displayWord) {
        tvWord.setText(displayWord);
    }
}


package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.soukousschallenge.R;
import com.example.soukousschallenge.model.Partie;

public class MainActivity extends AppCompatActivity {

    private Button mStartButton;
    private Button mScoreButton;
    public static final int RC_SCORE = 1;
    public static final int RC_GAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartButton = findViewById(R.id.activity_main_startButton);
        mScoreButton = findViewById(R.id.activity_main_scoreButton);


        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancement de la vue de jeu
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity,RC_GAME);
            }
        });

        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancement de la vue des scores
                Intent scoreActivity = new Intent(MainActivity.this, ScoreActivity.class);
                startActivityForResult(scoreActivity,RC_SCORE);
            }
        });





    }
}
package com.example.soukousschallenge.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.soukousschallenge.R;

public class EndGamePopUpActivity extends AppCompatActivity {

    private Button mQuitButton;
    private Button mReplayButton;
    private TextView mScoreNumber;
    public static final int RC_GAME = 1;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8),(int) (height*0.6));

        mQuitButton = findViewById(R.id.endgame_popup_quitButton);
        mReplayButton = findViewById(R.id.endgame_popup_replay);
        mScoreNumber = findViewById(R.id.endgame_popup_scoreNumber);

        Intent scoreGameActivity = getIntent();
        final int scoreNumber = scoreGameActivity.getIntExtra("scoreNumber", 45);
        mScoreNumber.setText(Integer.toString(scoreNumber));

        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndGamePopUpActivity.this, MainActivity.class);
                i.putExtra("score", scoreNumber);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EndGamePopUpActivity.this, GameActivity.class);
                startActivityForResult(i,RC_GAME);
            }
        });
    }
}
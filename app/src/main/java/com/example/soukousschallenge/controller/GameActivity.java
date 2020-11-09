package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.soukousschallenge.R;

public class GameActivity extends AppCompatActivity {

    private ImageButton mPauseButton;
    public static final int PAUSE_POPUP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mPauseButton = findViewById(R.id.activity_game_pauseButton);

        mPauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent pausePopUpActivity = new Intent(GameActivity.this, PausePopUpActivity.class);
                startActivityForResult(pausePopUpActivity, PAUSE_POPUP);
            }
        });
    }


}
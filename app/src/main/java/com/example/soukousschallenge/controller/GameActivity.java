package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.soukousschallenge.R;

public class GameActivity extends AppCompatActivity {

    private ImageButton mPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mPauseButton.findViewById(R.id.activity_game_pauseButton);

        mPauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //code à implémenter
            }
        });
    }


}
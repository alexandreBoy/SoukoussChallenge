package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.soukousschallenge.R;

public class ScoreActivity extends AppCompatActivity {

    private ListView mBestScoresList;
    private TextView mBestScoresLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mBestScoresList = findViewById(R.id.activity_score_bestScoresList);
        mBestScoresLabel = findViewById(R.id.activity_score_bestScoresLabel);
        //mBestScoresList.setAdapter();
    }
}
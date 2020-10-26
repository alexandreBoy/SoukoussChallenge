package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.soukousschallenge.R;

public class ScoreActivity extends AppCompatActivity {

    private TableLayout mBestScoresTable;
    private TextView mBestScoresLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mBestScoresTable = findViewById(R.id.activity_score_bestScoresTable);
        mBestScoresLabel = findViewById(R.id.activity_score_bestScoresLabel);
    }
}
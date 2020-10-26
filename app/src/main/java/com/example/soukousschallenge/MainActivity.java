package com.example.soukousschallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TableLayout mBestScoresTable;
    private TextView mBestScoresLabel;
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBestScoresTable = findViewById(R.id.activity_main_bestScoresTable);
        mBestScoresLabel = findViewById(R.id.activity_main_bestScoresLabel);
        mStartButton = findViewById(R.id.activity_main_startButton);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lancement de la vue de jeu
                System.out.println("Une nouvelle partie se lance !");
            }
        });


    }
}
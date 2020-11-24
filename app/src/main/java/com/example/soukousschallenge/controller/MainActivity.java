package com.example.soukousschallenge.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.soukousschallenge.R;
import com.example.soukousschallenge.model.Partie;
import com.example.soukousschallenge.model.Score;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mStartButton;
    private Button mScoreButton;
    private List<Score> scores = new ArrayList<>();
    public static final int RC_SCORE = 4;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== RC_GAME){
            if(data.hasExtra("score")){
                int score = data.getIntExtra("score",-1);
                if(score == -1){
                    Log.d("ACTIVITY","Erreur de sauvegarde !");
                }else{
                    Log.d("ACTIVITY","C'est magnifique !! Score : "+score);
                    //loadData(); // On charge le score
                    //scores.add() // On ajoute le score à la liste
                    //saveData(); // On save le score
                }
            }
        }
    }

    /*private void saveData(){
        //Sauvegarde de la liste des tirages
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson();
        editor.putString("draws list",json);
        editor.apply();
    }*/


}
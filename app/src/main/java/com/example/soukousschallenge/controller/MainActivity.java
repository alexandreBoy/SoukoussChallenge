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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mStartButton;
    private Button mScoreButton;
    private List<Score> scores;
    public static final int RC_SCORE = 4;
    public static final int RC_GAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartButton = findViewById(R.id.activity_main_startButton);
        mScoreButton = findViewById(R.id.activity_main_scoreButton);
        scores = new ArrayList<Score>();

        loadData();

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

        if(data != null)
        {
            if(requestCode== RC_GAME){
                if(data.hasExtra("score")){
                    int score = data.getIntExtra("score",-1);
                    if(score != -1){
                        loadData(); // On charge la liste des scores
                        Score score_to_add = new Score();
                        score_to_add.setValeurScore(score);
                        scores.add(score_to_add); // On ajoute le nouveau score
                        saveData(); // On save la liste des scores avec le nouveau score
                    }
                }else{
                    if (data.hasExtra("replay")){
                        int replay = data.getIntExtra("replay",-1);
                        if( replay != -1){
                            Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                            startActivityForResult(gameActivity,RC_GAME);
                        }
                    }
                }
            }
        }
    }

    private void saveData(){
        //Sauvegarde de la liste des tirages
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scores);
        editor.putString("scores list",json);
        editor.apply();
    }

    private void loadData(){
        //Chargement de la liste des tirages
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("scores list", null);
        Type type = new TypeToken<List<Score>>() {}.getType();
        scores = gson.fromJson(json,type);

        if(scores == null){
            scores = new ArrayList<Score>();
        }
    }
}
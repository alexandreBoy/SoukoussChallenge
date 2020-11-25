package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.soukousschallenge.R;
import com.example.soukousschallenge.model.Score;
import com.example.soukousschallenge.model.ScoreAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    private ListView mBestScoresList;
    private TextView mBestScoresLabel;
    private ImageButton mDeleteButton;
    private ScoreAdapter mScoreAdapter;
    private List<Score> scores = new ArrayList<Score>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        mBestScoresList = findViewById(R.id.activity_score_bestScoresList);
        mBestScoresLabel = findViewById(R.id.activity_score_bestScoresLabel);
        mDeleteButton = findViewById(R.id.activity_score_deleteButton);

        loadData();
        scores = Score.descendingSort((ArrayList<Score>) scores);
        mScoreAdapter = new ScoreAdapter(this,scores);
        mBestScoresList.setAdapter(mScoreAdapter);

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Suppression de tous les scores
                scores.clear();
                saveData();
                mScoreAdapter.notifyDataSetChanged();
            }
        });
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
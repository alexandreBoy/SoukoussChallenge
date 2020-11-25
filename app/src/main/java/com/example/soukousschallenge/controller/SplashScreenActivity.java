package com.example.soukousschallenge.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.soukousschallenge.R;

public class SplashScreenActivity extends AppCompatActivity{

    private static int SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent nextIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(nextIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}

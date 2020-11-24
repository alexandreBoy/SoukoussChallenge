package com.example.soukousschallenge.model;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.util.*;

public class Partie{
    CountDownTimer chrono;
    int duree;
    Score score;
    long tempsRestant;
    boolean isChronoRunning;
    TextView affichageChrono;
    boolean isFinished;
    CustomListener cl;


    public Partie(TextView t){
        duree = 30000;
        tempsRestant = duree;
        score = new Score();
        affichageChrono = t;


    }


    // Getters & Setters


    public CountDownTimer getChrono(){
        return chrono;
    }

    public void setChrono(CountDownTimer chrono){
        this.chrono = chrono;
    }

    public int getDuree(){
        return duree;
    }

    public void setDuree(int duree){
        this.duree = duree;
    }

    public Score getScore(){
        return score;
    }

    public void setScore(Score score){
        this.score = score;
    }

    public long getTempsRestant(){
        return tempsRestant;
    }

    public void setTempsRestant(long tempsRestant){
        this.tempsRestant = tempsRestant;
    }

    public boolean isChronoRunning(){
        return isChronoRunning;
    }

    public void setChronoRunning(boolean chronoRunning){
        isChronoRunning = chronoRunning;
    }

    public TextView getAffichageChrono(){
        return affichageChrono;
    }

    public void setAffichageChrono(TextView affichageChrono){
        this.affichageChrono = affichageChrono;
    }

    public void setCl(CustomListener cl){
        this.cl = cl;

    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setFinished(boolean finished){
        isFinished = finished;
        if( finished && cl != null) cl.onChange();
    }



    public void startChrono(){
        chrono = new CountDownTimer(tempsRestant,1000){
            @Override
            public void onTick(long l){
                tempsRestant = l;
                updateChronoText();

            }

            @Override
            public void onFinish(){
                isChronoRunning = false;
                setFinished(true);

            }
        }.start();

        isChronoRunning = true;
        setFinished(false);

    }

    public void pauseChrono(){
        chrono.cancel();
        isChronoRunning = false;
    }

    public void updateChronoText(){
        int secondes = (int) (tempsRestant/1000)%60;
        String timeLeft = String.format("00:%02d",secondes);

        affichageChrono.setText(timeLeft);

    }

    public interface CustomListener{
        void onChange();
    }



}

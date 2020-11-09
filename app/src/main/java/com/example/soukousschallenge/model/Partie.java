package com.example.soukousschallenge.model;

import android.util.Log;

import java.util.*;

public class Partie{
    Timer chrono;
    int duree;
    Score score;
    int tempsRestant;
    TimerTask tache;

    public Partie(){
        chrono = new Timer();
        duree = 10;
        tempsRestant = duree;
        tache = new TimerTask(){
            @Override
            public void run(){
                if(tempsRestant>=0){
                    Log.d("CHRONO","Temps restant : "+tempsRestant);
                    if(tempsRestant==0){
                        cancel();
                    }
                    tempsRestant--;
                }

            }
        };

    }


    // Getters & Setters
    public Timer getChrono(){
        return chrono;
    }

    public void setChrono(Timer chrono){
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

    public int getTempsRestant(){
        return tempsRestant;
    }

    public void setTempsRestant(int tempsRestant){
        this.tempsRestant = tempsRestant;
    }

    public TimerTask getTache(){ return tache;}

    public void setTimerTask(TimerTask tache){this.tache = tache;}

    public void startChrono(TimerTask t){
        chrono.schedule(t,0,1000);
    }

    public void startGame(){
        startChrono(tache);
    }

    public void pauseGame(){
        chrono.cancel();
        Log.d("CHRONO","Pause !");
    }

    public void resumeGame(){
        Log.d("CHRONO","Resume !");
        chrono = new Timer();
        tache = new TimerTask(){
            @Override
            public void run(){
                if(tempsRestant>=0){
                    Log.d("CHRONO","Temps restant : "+tempsRestant);
                    if(tempsRestant==0){
                        cancel();
                    }
                    tempsRestant--;
                }
            }
        };
        startChrono(tache);

    }

}

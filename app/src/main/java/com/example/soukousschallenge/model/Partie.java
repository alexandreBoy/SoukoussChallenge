package com.example.soukousschallenge.model;

import java.util.*;

public class Partie{
    Timer chrono;
    int duree;
    Score score;
    int tempsRestant;


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

    public void startChrono(TimerTask t){
        chrono.schedule(t,0,1000);
    }


}

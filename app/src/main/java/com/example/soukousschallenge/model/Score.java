package com.example.soukousschallenge.model;

import java.io.Serializable;

public class Score implements Serializable{
    int valeurScore;

    public Score(){
        this.valeurScore = 0;
    }

    // Getters & Setters
    public int getValeurScore(){
        return valeurScore;
    }

    public void setValeurScore(int valeurScore){
        this.valeurScore = valeurScore;
    }
}

package com.example.soukousschallenge.model;

import java.util.Date;

public class Score{
    Date date;
    int valeurScore;

    // Getters & Setters
    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public int getValeurScore(){
        return valeurScore;
    }

    public void setValeurScore(int valeurScore){
        this.valeurScore = valeurScore;
    }
}

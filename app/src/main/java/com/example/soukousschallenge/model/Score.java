package com.example.soukousschallenge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Score implements Serializable, Comparable<Score>{
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

    @Override
    public int compareTo(Score score){
        return(this.valeurScore-score.getValeurScore());
    }

    public static ArrayList<Score> descendingSort(ArrayList<Score> l){
        //On créer la ArrayList à retourner
        ArrayList<Score> sorted_score = new ArrayList<>();
        //On copie la ArrayList draw dans la nouvelle ArrayList
        for(Score sc : l){
            sorted_score.add(sc);
        }

        //On trie la ArrayList à retourner
        Collections.sort(sorted_score,Collections.<Score>reverseOrder());
        return sorted_score;
    }
}

package com.example.soukousschallenge.model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Action{
    boolean selected;
    int ID;

    public abstract boolean perform();

    public abstract String getActionText(Context c);

    // Getters & Setters
    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public int getID() { return ID; }
}

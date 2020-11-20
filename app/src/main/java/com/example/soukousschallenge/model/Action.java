package com.example.soukousschallenge.model;

public abstract class Action{
    boolean selected;
    int ID;

    public abstract boolean perform();

    // Getters & Setters
    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public int getID() { return ID; }
}

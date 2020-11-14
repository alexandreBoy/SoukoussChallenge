package com.example.soukousschallenge.model;

public abstract class Action{
    boolean terminated;
    int ID;

    public abstract boolean perform();

    // Getters & Setters
    public boolean isTerminated(){
        return terminated;
    }

    public void setTerminated(boolean terminated){
        this.terminated = terminated;
    }

    public int getID() { return ID; }
}

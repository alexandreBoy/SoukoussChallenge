package com.example.soukousschallenge.model;

import android.content.Context;

import com.example.soukousschallenge.R;

public class ActionTurnUp extends Action {
    private final int ID = 9;

    @Override
    public boolean perform() { return false; }

    @Override
    public String getActionText(Context c) {
        return c.getString(R.string.actionTurnOver);
    }
}

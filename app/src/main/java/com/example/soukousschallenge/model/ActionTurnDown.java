package com.example.soukousschallenge.model;

import android.content.Context;

import com.example.soukousschallenge.R;

public class ActionTurnDown extends Action {
    private final int ID = 1;

    @Override
    public boolean perform() {
        return false;
    }

    @Override
    public String getActionText(Context c) {
        return c.getString(R.string.actionTurnOver);
    }
}

package com.example.soukousschallenge.model;

import android.content.Context;

import com.example.soukousschallenge.R;

public class ActionLeftSwipe extends Action {
    private final int ID = 2;

    @Override
    public boolean perform() {
        return false;
    }

    @Override
    public String getActionText(Context c) {
        return c.getString(R.string.actionSwipeLeft);
    }
}

package com.example.unicorngladiators.io;

import android.util.Log;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;


public class PlayerAction implements ClickAction {
    private static final String TAG = "PlayerAction";
    private final Universe universe;
    public PlayerAction(Universe universe) {
        this.universe = universe;
    }

    @Override
    public void execute(Motion m) {
        Log.i(TAG, "executed");
        String name = "toto";
        this.universe.updatePlayerPosition(name, m);
    }

}
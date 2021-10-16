package com.example.unicorngladiators.io;


import android.util.Log;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;

public class JoystickAction implements ClickAction {
    private static final String TAG = "JoystickAction";
    private final Universe universe;

    public JoystickAction(Universe universe) {
        this.universe = universe;
    }

    @Override

    public void execute(Motion m) {

        Log.i(TAG, "executed");
        this.universe.updateJoystick(m);
    }
}
package com.example.unicorngladiators.io;


import android.util.Log;

import com.example.unicorngladiators.model.Joystick;
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
        Joystick js = this.universe.getJoystick();
        Position pos = js.getPosition();
        int displacement = (int)Math.sqrt((Math.pow(m.getX(), 2)) + Math.pow(m.getY(), 2));
        if (displacement < js.getRadius()){
            Log.i(TAG, "joystick executed");
            this.universe.updateJoystick(m);
        } else {
            int ratio = (int) (js.getRadius() / displacement);
            int constrainedX = pos.getX() + m.getX()*ratio;
            int constrainedY = pos.getY() + m.getY();
            Motion tmp = new Motion(constrainedX, constrainedY);
            this.universe.updateJoystick(tmp);
            Log.i(TAG, "joystick executed");
        }

    }
}
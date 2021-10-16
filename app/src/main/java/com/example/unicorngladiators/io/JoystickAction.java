package com.example.unicorngladiators.io;


import android.util.Log;
import android.view.MotionEvent;

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

    public void execute(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        Position eventPos = new Position(x, y);

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(this.universe.getJoystick().isPressed(eventPos)){
                    this.universe.setIsPressedForJoystick(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("Joystick Action","User moves");
                if (this.universe.getJoystick().getIsPressed()) {
                    Log.d("Joystick Action","to set actuator");
                    this.universe.setActuatorForJoystick(eventPos);
                }
                else{Log.d("Joystick Action","Joystick is not pressed");}
                break;
            case MotionEvent.ACTION_UP:
                this.universe.setIsPressedForJoystick(false);
                this.universe.resetActuatorForJoystick();
                break;
        }
    }
}
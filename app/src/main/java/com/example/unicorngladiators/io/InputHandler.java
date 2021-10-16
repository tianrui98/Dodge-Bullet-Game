package com.example.unicorngladiators.io;

//send signal to JoystickAction and PlayerAction

import android.util.Log;
import android.view.MotionEvent;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;


public class InputHandler implements InputListener.Callback {
    private ClickAction joystickAction;
    private ClickAction playerAction;

    public void setOnClickAction(ClickAction joystickAction, ClickAction playerAction) {
        this.joystickAction = joystickAction;
        this.playerAction = playerAction;
    }

    @Override
    public void onClick(MotionEvent me) {
        if (joystickAction != null) {
            joystickAction.execute(me);
            Log.d("InputerHandler", "execute joystick action");
        }

        if (playerAction != null) {
            playerAction.execute(me);
        }
    }
}
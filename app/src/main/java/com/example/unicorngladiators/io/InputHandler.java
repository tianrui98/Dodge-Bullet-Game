package com.example.unicorngladiators.io;

//determines what to do when user touches the joystick

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
    public void onClick(Motion m) {
        if (joystickAction != null) {
            joystickAction.execute(m);
        }

        if (playerAction != null) {
            playerAction.execute(m);
        }
    }
}
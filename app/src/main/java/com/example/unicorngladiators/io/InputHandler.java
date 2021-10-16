package com.example.unicorngladiators.io;

//determines what to do when user touches the joystick

import com.example.unicorngladiators.model.Position;


public class InputHandler implements InputListener.Callback {
    private ClickAction onClickAction;

    public void setOnClickAction(ClickAction onClickAction) {
        this.onClickAction = onClickAction;
    }

    @Override
    public void onClick(Position pos) {
        if (onClickAction != null) onClickAction.execute(pos);
    }
}
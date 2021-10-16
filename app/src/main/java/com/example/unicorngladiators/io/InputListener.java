package com.example.unicorngladiators.io;


import android.view.MotionEvent;
import android.view.View;

import com.example.unicorngladiators.model.Joystick;
import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;


public class InputListener implements View.OnTouchListener {
    private Universe universe;
    private Position pos;
    private Callback callback;

    public InputListener(Universe universe) {
        this.universe = universe;
    }

    public void setCallback(InputHandler cb) {
        this.callback = cb;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Joystick js = this.universe.getJoystick();
        Position pos = js.getPosition();
        if (motionEvent.getAction() != motionEvent.ACTION_UP ) {
//            int displacement = (int) Math.sqrt((Math.pow(motionEvent.getX() - pos.getX(), 2)) + Math.pow(motionEvent.getY() - pos.getY(), 2));
            int disX = (int) (motionEvent.getRawX() - pos.getX());
            int disY = (int) (motionEvent.getRawY() - pos.getY());
            Motion motion = new Motion(disX, disY);
            callback.onClick(motion);
        }
        return true;
    }

    public interface Callback {
        void onClick ( Motion motion ) ;
    }
}
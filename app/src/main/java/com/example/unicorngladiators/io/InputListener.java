package com.example.unicorngladiators.io;


import android.util.Log;
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
        callback.onClick(motionEvent);
        Log.d("Inputlistener", "user clicks");
        return true;
    }

    public interface Callback {
        void onClick ( MotionEvent me ) ;
    }
}
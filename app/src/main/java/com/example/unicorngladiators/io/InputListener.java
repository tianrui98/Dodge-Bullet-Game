package com.example.unicorngladiators.io;


import android.view.MotionEvent;
import android.view.View;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;


public class InputListener implements View.OnTouchListener {
    private Position pos;
    private Callback callback;
    public void setCallback(InputHandler cb) {
        this.callback = cb;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != motionEvent.ACTION_UP ) {
            pos = new Position(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
            callback.onClick(pos);
        }
        return true;
    }

    public interface Callback {
        void onClick ( Position pos ) ;
    }
}
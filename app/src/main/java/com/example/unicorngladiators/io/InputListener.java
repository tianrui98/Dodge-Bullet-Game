package com.example.unicorngladiators.io;


import android.view.MotionEvent;
import android.view.View;

import com.example.unicorngladiators.model.Position;

public class InputListener implements View.OnTouchListener {
    private Position pos;
    private Callback callback;
    public void setCallback(InputHandler cb) {
        this.callback = (Callback) cb;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //TODO do we need to round it up?
                pos = new Position(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
                callback.onClick(pos);
                break;
        }
        return true;
    }
    public interface Callback {
        void onClick ( Position pos ) ;
    }
}
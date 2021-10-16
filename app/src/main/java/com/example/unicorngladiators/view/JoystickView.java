package com.example.unicorngladiators.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// Callbacks are for objects to notify the class that an event has happened
public class JoystickView extends SurfaceView implements SurfaceHolder.Callback {

    public JoystickView(Context context) {
        super(context);
    }

    public JoystickView(Context context, AttributeSet attributes, int style){
        super(context, attributes, style);
    }

    public JoystickView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){}

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){}
}

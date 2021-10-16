package com.example.unicorngladiators.io;

import android.view.MotionEvent;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;

public interface ClickAction {
    void execute(MotionEvent me);
}
package com.example.unicorngladiators;

import android.content.res.Resources;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.view.Renderer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameController extends Thread{
    private Renderer renderer;
    private SurfaceView sv;
    private Universe universe;
    private long startTime;
    SurfaceHolder holder;

    public GameController(SurfaceView sv, Resources context) {
        this.startTime = System.currentTimeMillis();
        this.sv = sv;

        //instantiate universe, princess, unicorns and ask Renderer to draw them
        this.universe = new Universe(null);
        this.renderer = new Renderer(universe, holder, context);
        this.sv.getHolder().addCallback(this.renderer);
        this.sv.setWillNotDraw(false);

        this.universe.setCallBack(this.renderer);
        this.sv.setWillNotDraw(false);
        this.sv.getHolder().addCallback(this.renderer);
    }


    @Override
    public void run() {
        while (true) {


            long elapsedTime = System.currentTimeMillis();
            this.universe.step( elapsedTime - this.startTime);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

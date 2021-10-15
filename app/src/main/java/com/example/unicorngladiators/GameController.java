package com.example.unicorngladiators;

import android.content.res.Resources;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.view.Renderer;

import java.util.ArrayList;
import java.util.List;
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
        List<Unicorn> emptyPlayerList = new ArrayList<>();
        this.universe = new Universe(emptyPlayerList);
        this.universe.addPlayer("toto", new Position(200,100), CharacterState.RIGHT);
        this.renderer = new Renderer(this.universe, holder, context);
        this.universe.setCallBack(this.renderer);
        this.sv.getHolder().addCallback(this.renderer);
        this.sv.setWillNotDraw(false);
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

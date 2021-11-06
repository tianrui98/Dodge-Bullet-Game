package com.example.unicorngladiators;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.unicorngladiators.io.InputHandler;
import com.example.unicorngladiators.io.InputListener;
import com.example.unicorngladiators.io.JoystickAction;
import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.network.Room;
import com.example.unicorngladiators.view.Renderer;


import java.util.HashMap;

public class GameController extends Thread{
    private final String TAG = "GameController" ;
    private final Room room;
    private final HashMap<String, String> allPlayers;
    private Renderer renderer;
    private SurfaceView sv;
    private Universe universe;
    private long startTime;
    private SurfaceHolder holder;
    private Activity gameActivity;
    private String puid;

    // Constructor Class for the game, helps handle resource allocation and acts as an intermediary
    // between android interface and the inner universe class
    public GameController(SurfaceView sv, Resources context, int height, int width, Room room, String puid, Activity gameActivity) {
        this.startTime = System.currentTimeMillis();
        this.sv = sv;
        this.gameActivity = gameActivity;
        this.puid = puid;

        //instantiate universe, princess, unicorns and ask Renderer to draw them
        HashMap<String, Unicorn> emptyPlayerMap = new HashMap<>();
        String currentPlayerName = room.getPlayerName(puid);
        this.universe = new Universe(emptyPlayerMap,height,width, room, puid, currentPlayerName);
        this.room = room;
        //transfer players from Room to universe
        this.allPlayers = room.getPlayer_ids();

        Log.d(TAG, "Universe initialized with players: " + this.universe.getPlayersHashMap().keySet());

        //manage relationship between surface holder and renderer, and between universe and renderer
        this.renderer = new Renderer(this.universe, holder, context);
        this.universe.setCallBack(this.renderer);
        this.sv.getHolder().addCallback(this.renderer);
        this.sv.setWillNotDraw(false);


        //manage relationship between listener and surfaceView
        InputListener inputListener = new InputListener(this.universe);
        this.sv.setOnTouchListener(inputListener);

        //manage relationship between listener and handler
        InputHandler inputHandler = new InputHandler();
        inputHandler.setOnClickAction(new JoystickAction(this.universe));
        inputListener.setCallback(inputHandler);
    }

    public Universe getUniverse() {
        return universe;
    }

    @Override
    // Defining a class method to help step through the universe. This ensures
    // that the universe is being incremented every 20 miliseconds
    public void run() {
        while (!this.universe.snapped()) {
            long elapsedTime = System.currentTimeMillis();
            this.universe.step();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(this.gameActivity, EndGameActivity.class);

        intent.putExtra("Room", room);
        intent.putExtra("PlayerUID", puid);
        System.out.println("in waiting room: "+room.getPlayer_ids() + "<-- player ids");
        gameActivity.startActivity(intent);
    }

    public Room getRoom(){
        return this.universe.getRoom();
    }
}

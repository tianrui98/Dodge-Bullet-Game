package com.example.unicorngladiators;

import android.content.res.Resources;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.unicorngladiators.io.InputHandler;
import com.example.unicorngladiators.io.InputListener;
import com.example.unicorngladiators.io.JoystickAction;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.network.FirebaseRoomHandler;
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
    SurfaceHolder holder;

    public GameController(SurfaceView sv, Resources context, int height, int width, Room room, String puid) {
        this.startTime = System.currentTimeMillis();
        this.sv = sv;


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

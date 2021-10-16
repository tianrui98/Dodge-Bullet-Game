package com.example.unicorngladiators.model;
import android.util.Log;

import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Universe {
    private final Joystick joystick;
    private Princess princess;
    public List<Unicorn> players = new Vector<>();
    private final String TAG = "Universe";
    private int height;
    private int width;

    public Universe(List<Unicorn> players,int height,int width) {
        this.players = players;
        this.princess = new Princess(new Position(20,20), CharacterState.SPECIAL1);
        this.joystick = new Joystick();
        this.height = height;
        this.width = width;
    }

    //manage princess (npc)
    public Princess getPrincess() {return this.princess;}
    public void updatePrincess(CharacterState state, Position pos) {
        this.princess.setState(state);
        this.princess.setPosition(pos);
    }

    //TODO manage projectiles

    //manage players
    public void addPlayer(String name, Position pos, CharacterState state) {
        this.players.add(new Unicorn (name, 3, false, pos, state));
        castChanges();
    }
    public List<Unicorn> getPlayers() {
        return players;
    }


    //manage callback
    public interface Callback {
        void universeChanged ( Universe u ) ;
    }

    //manage joystick
    public Joystick getJoystick() {
        return this.joystick ;}

    public void updateJoystick(Position pos) {
        this.joystick.updatePosition(pos);
    }



    //tell universe what to update
    public void step(long elapsedTime) {
        //TODO round up elapsed time if we want something to happen every x seconds
//        Log.d(TAG, ("Elapsed time = " + Long.toString(elapsedTime)));
        this.princess.spin();
        for (Unicorn player : players) {
            player.walkRightStateChange();
        }


        Log.d(TAG,"Height of screen is currently " + Integer.toString(this.height));
        Log.d(TAG,"Width of screen is currently " + Integer.toString(this.width));
        castChanges();
    }

    private Callback  callback = null;
    public void setCallBack(Callback  c) {
        callback = c;
    }
    public void addCallBack (Callback  c ) {
        this.callback = c;
    }
    protected void castChanges() {
        if (callback != null) {
            callback.universeChanged(this);
        }
    }


}
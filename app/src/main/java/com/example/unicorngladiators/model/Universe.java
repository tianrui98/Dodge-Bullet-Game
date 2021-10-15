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
    private Princess princess;
    public List<Unicorn> players = new Vector<>();
    private final String TAG = "Universe";

    public Universe(List<Unicorn> players) {
        this.players = players;
        this.princess = new Princess(new Position(20,20), CharacterState.FRONT);
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

    //tell universe what to update
    public void step(long elapsedTime) {
        //TODO round up elapsed time if we want something to happen every x seconds
//        Log.d(TAG, ("Elapsed time = " + Long.toString(elapsedTime)));
        this.princess.wave();
        this.getPlayers().iterator().next().walkRightStateChange();
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
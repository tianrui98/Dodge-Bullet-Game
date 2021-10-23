package com.example.unicorngladiators.model;

import android.util.Log;

import com.example.unicorngladiators.model.characters.Character;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.example.unicorngladiators.model.projectiles.Peach;
import com.example.unicorngladiators.model.projectiles.Projectile;
import com.example.unicorngladiators.network.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Universe {
    private final Joystick joystick;
    private final List<Bullet> bullets;
    private List<Projectile> currentBullets;
    private int bulletIndex;
    private Princess princess;
    public HashMap<String, Unicorn> players;
    private final String TAG = "Universe";
    private int height;
    private int width;
    private Projectile peach;
    private Room room;
    private List<Projectile> currentPeaches;


    public Universe(HashMap<String,Unicorn> players, int height,int width, Room room) {
        this.players = players;
        this.princess = new Princess(new Position(20,20), CharacterState.SPECIAL1);
        this.joystick = new Joystick();

        this.height = height;
        this.width = width;
        this.room = room;

        this.bullets = this.room.getBullets();
        this.bulletIndex = 0;
        this.currentBullets = new ArrayList<Projectile>();
        this.currentPeaches = new ArrayList<Projectile>();
    }

    //manage princess (npc)
    public Princess getPrincess() {
        return this.princess;
    }

    public void updatePrincess(CharacterState state, Position pos) {
        this.princess.setState(state);
        this.princess.setPosition(pos);
    }

    //manage projectiles
    public void addABullet(){
        Projectile bullet = this.bullets.get(this.bulletIndex);
        this.bulletIndex += 1;
        this.currentBullets.add(bullet);
    }

    public void addAPeach(){
        //TODO: change height and width to arena's dimension
        Projectile peach = new Peach(1.0, this.getPrincess(), width, height);
        this.currentPeaches.add(peach);
    }

    //manage players
    public void addPlayer(String name, Position pos, CharacterState state) {
        this.players.put(name, new Unicorn (name, 3, false, pos, state));
        castChanges();
    }
    public HashMap<String, Unicorn> getPlayersHashMap() {
        return this.players;

    }

        //chang player position
    public void updatePlayerPosition(String name, Motion m) {
        Unicorn player = this.players.get(name);
        player.walk(m);
        this.players.put(name,player);
//        Log.d(TAG, "Player position changed");
    }

    public List<Projectile> getBullets() {
        return this.currentBullets;
    }


    public List<Projectile> getPeaches() {
        return this.currentPeaches;
    }


    //manage callback
    public interface Callback {
        void universeChanged(Universe u);
    }

    //manage joystick
    public Joystick getJoystick() {
        return this.joystick ; }

    public void setActuatorForJoystick(Position eventPos){
        this.joystick.setActuator(eventPos);
        }
    public void setIsPressedForJoystick(boolean pressed) {
        this.joystick.setIsPressed(pressed); }

    public void resetActuatorForJoystick() {
        this.joystick.resetActuator(); }

    //tell universe what to update
    public void step(long elapsedTime) {
        // TODO round up elapsed time if we want something to happen every x seconds
        //  Log.d(TAG, ("Elapsed time = " + Long.toString(elapsedTime)));
        this.princess.spin();

        this.joystick.update();
        for (Unicorn player : players.values()) {
            player.updatePositionState(this.joystick.getActuatorX(), this.joystick.getActuatorY());
        }

        this.addABullet();
        this.addAPeach();
        this.castChanges();

    }

//        Log.d(TAG,"Height of screen is currently " + Integer.toString(this.height));
//        Log.d(TAG,"Width of screen is currently " + Integer.toString(this.width));

    private Callback callback = null;

    public void setCallBack(Callback c) {
        callback = c;
    }

    public void addCallBack(Callback c) {
        this.callback = c;
    }

    protected void castChanges() {
        if (callback != null) {
            callback.universeChanged(this);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
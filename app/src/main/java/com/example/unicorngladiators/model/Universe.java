package com.example.unicorngladiators.model;

import android.util.Log;

import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.example.unicorngladiators.model.projectiles.Peach;
import com.example.unicorngladiators.model.projectiles.Projectile;
import com.example.unicorngladiators.network.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Universe {
    private final Joystick joystick;
    private final List<Bullet> bullets;
    private List<Bullet> currentBullets;
    private int bulletIndex;
    private Princess princess;
    public HashMap<String, Unicorn> players;
    private final String TAG = "Universe";
    private int height;
    private int width;
    private Projectile peach;
    private Room room;
    private final String currentPlayerName;
    private List<Projectile> currentPeaches;


    public Universe(HashMap<String,Unicorn> players, int height,int width, Room room, String currentPlayerName) {
        this.players = players;
        this.princess = new Princess(new Position(20,20), CharacterState.SPECIAL1);
        this.joystick = new Joystick();
        this.height = height;
        this.width = width;
        this.room = room;

        this.currentPlayerName = currentPlayerName;
        Log.d(TAG, "Current player name is " + this.currentPlayerName);

        //initialize projectiles
        this.bullets = this.room.getBullets();
        Log.d(TAG, "Bullets in uni init:");
        for (Bullet i: bullets)
            Log.d(TAG, "Bullet: " + i);
        this.bulletIndex = 0;
        this.currentBullets = new ArrayList<Bullet>();

        //TODO: delete the following line. Only add bullet in step()
//        this.addABullet();
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

    //manage projectiles on screen
    public void addABullet(){
        Bullet bullet = this.bullets.get(this.bulletIndex);
        this.bulletIndex += 1;
        this.currentBullets.add(bullet);
    }

    public void removeABullet(){
        //Todo: only remove a bullet when it's out of the arena or hit a unicorn
        this.currentBullets.remove(this.currentBullets.size() - 1);
    }

    public void updateCurrentBulletPosition(){
        for (Bullet bullet : this.currentBullets) {
            bullet.step();
        }
    }

    public void addAPeach(){
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

    public List<Bullet> getBullets() {
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
        this.players.get(this.currentPlayerName).updatePositionState(this.joystick.getActuatorX(), this.joystick.getActuatorY());
        this.updateCurrentBulletPosition();

        // TODO add bullets
        this.addABullet();

        //TODO add peach at random times
//        this.addAPeach();
        this.castChanges();

    }


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
package com.example.unicorngladiators.model;

import android.util.Log;

import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.example.unicorngladiators.model.projectiles.Peach;
import com.example.unicorngladiators.model.projectiles.Projectile;
import com.example.unicorngladiators.network.FirebasePlayerHandler;
import com.example.unicorngladiators.network.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Room room = null;
    private FirebasePlayerHandler fph;
    private final String currentPlayerName, currentPlayerUID;
    private List<Projectile> currentPeaches;

    private long steps;
    private long period;


    public Universe(HashMap<String,Unicorn> players, int height,int width, Room room, String currentPlayerUID, String currentPlayerName) {
        this.players = players;
        this.princess = new Princess(new Position(20,20), CharacterState.SPECIAL1);
        this.joystick = new Joystick();
        this.height = height;
        this.width = width;
        this.fph = new FirebasePlayerHandler(currentPlayerUID);

        this.currentPlayerUID = currentPlayerUID;

        this.currentPlayerName = currentPlayerName;
        Log.d(TAG, "Current player name is " + this.currentPlayerName);

        //initialize projectiles
        fph.readRoomStates();
        this.room = fph.getRoom();
        this.room.setPlayer_pos(room.getPlayer_pos());
        this.room.setPlayer_scores(room.getPlayer_scores());

        this.bullets = this.room.getBullets();
        Log.d(TAG, "Bullets in uni init:");
        //for (Bullet i: bullets)
        //    Log.d(TAG, "Bullet: " + i);
        this.bulletIndex = 0;
        this.currentBullets = new ArrayList<Bullet>();

        //TODO: delete the following line. Only add bullet in step()
//        this.addABullet();
        this.currentPeaches = new ArrayList<Projectile>();

        //init the players:
        System.out.println("all the players: "+this.room.getPlayer_ids());
        for (String puids : this.room.getPlayer_ids().keySet()) {
            System.out.println("adding "+puids);
            this.addPlayer(this.room.getPlayerName(puids), this.room.getPlayer_pos().get(puids), CharacterState.RIGHT1);
        }
        this.steps = 1;
        this.period = 20;
    }

    //manage princess (npc)
    public Princess getPrincess() {
        return this.princess;
    }

    public void updatePrincess(CharacterState state, Position pos) {
        this.princess.setState(state);
        this.princess.setPosition(pos);
    }

    public int getPlayerLives(){
        return this.players.get(this.currentPlayerName).getLives();
    }

    public void checkCollision() {
        for (Map.Entry<String, Unicorn> entry: this.players.entrySet()) {
            String key = entry.getKey();
            Unicorn unicorn = entry.getValue();
            Position unicornPos = unicorn.getPosition();
            if (unicorn.getIsInvulnerable()) {
                continue;
            }
            for (Bullet bullet: this.bullets) {
                Position bulletPos = bullet.getPosition();
                int distance = unicornPos.getDistance(bulletPos);
                // To do: Find appropriate distance
                Log.d("Bullet", String.valueOf(distance));
                if (distance < 20) {
                    Log.d("SimiLaoBu", "Your mother got hit");
                    unicorn.setLives(unicorn.getLives() - 1);
                    unicorn.setInvulnerable(true);
                    break;
                }
            }
        }
    }

    //manage projectiles on screen
    public void addABullet(){
        if (this.bulletIndex >= this.bullets.size())
            return;
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
        this.fph.updateMove(this.players.get(this.currentPlayerName).getPosition().shortString());

        if(this.steps % this.period == 0){
            this.addABullet();
        }
        this.steps += 1;
        // TODO add bullets


        // TODO add peach at random times
        // this.addAPeach();

        // Checking for Bullet Collisions here
        this.checkCollision();

        this.castChanges();

        for (String puid : this.room.getPlayer_ids().keySet()) {
            if (!puid.equals(this.currentPlayerUID)){
                Position posToUpdate = this.room.getPlayer_pos().get(puid);
                //System.out.println("current player: " + this.currentPlayerUID);
                System.out.println(puid+this.room.getPlayerName(puid)+" bloody stepping: "+posToUpdate);
                System.out.println("the entire pos: " + this.room.getPlayer_pos());
                Position curPos = this.players.get(this.room.getPlayerName(puid)).getPosition();
                this.players.get(this.room.getPlayerName(puid))
                        .updatePositionState(
                                posToUpdate.getX() - curPos.getX(),
                                posToUpdate.getY() - curPos.getY());
            }
        }
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
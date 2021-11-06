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

/**
 * The type Universe.
 */
public class Universe {
    private final Joystick joystick;
    private final List<Bullet> bullets;
    private List<Bullet> currentBullets;
    private int bulletIndex;
    private Princess princess;
    /**
     * The Players.
     */
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


    /**
     * Instantiates a new Universe.
     *
     * @param players           This maps the player names to their respective Unicorns
     * @param height            Height of the Screen
     * @param width             Width of the Screen
     * @param room              This represents the Room Object
     * @param currentPlayerUID  the current player uid
     * @param currentPlayerName the current player name
     *
     * Using this constructor we can instantiate an instance of the Universe which helps track and manage
     * changes to our individual player metadata
     */
    public Universe(HashMap<String,Unicorn> players, int height,int width, Room room, String currentPlayerUID, String currentPlayerName) {
        this.players = players;
        this.princess = new Princess(new Position(20,20), CharacterState.SPECIAL1, height, width);
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

    /**
     * Gets princess.
     *
     * @return the princess
     */
    public Princess getPrincess() {
        return this.princess;
    }

    /**
     * Update princess.
     *
     * @param state the state
     * @param pos   the pos
     */
    public void updatePrincess(CharacterState state, Position pos) {
        this.princess.setState(state);
        this.princess.setPosition(pos);
    }

    /**
     * Get player lives int.
     *
     * @return the int
     */
    public int getPlayerLives(){
        return this.players.get(this.currentPlayerName).getLives();
    }

    /**
     * Valid collision boolean.
     *
     * @param x        the x
     * @param y        the y
     * @param distance the distance
     * @return the boolean
     */
    static public boolean validCollision(Bullet x, Unicorn y, int distance){
        return y.getPosition().getDistance(x.getPosition()) <= distance && !y.getIsInvulnerable();
    }

    /**
     * Check collision helper.
     *
     * @param e The Mapping of the Unicorn to the indiivudal names of the players
     * @param b The List of bullets which are currently moving around in the Universe
     */
    static public void checkCollisionHelper(HashMap<String, Unicorn> e,List<Bullet> b){
        for(Map.Entry<String,Unicorn> entry:e.entrySet()){
            Unicorn unicorn = entry.getValue();
            for(Bullet bullet:b){
                if(Universe.validCollision(bullet,unicorn,200)){
                    try {
                        unicorn.takeBullet();
                    } catch (Exception excep) {
                        excep.printStackTrace();
                    }
                    break;
                }
            }
        }


    }

    /**
     * This checks if a collission is happening amongst any of the individual bullets and players
     */
    public void checkCollision() {
        Universe.checkCollisionHelper(this.players,this.bullets);
    }

    /**
     * Adds the next bullet to the list of bullets currently in the Universe
     */
//manage projectiles on screen
    public void addABullet(){
        if (this.bulletIndex >= this.bullets.size())
            return;
        Bullet bullet = this.bullets.get(this.bulletIndex);
        this.bulletIndex += 1;
        this.currentBullets.add(bullet);
    }

    /**
     * Remove a bullet.
     */
    public void removeABullet(){
        //Todo: only remove a bullet when it's out of the arena or hit a unicorn
        this.currentBullets.remove(this.currentBullets.size() - 1);
    }

    /**
     * Update all existing bullet positions.
     */
    public void updateCurrentBulletPosition(){
        for (Bullet bullet : this.currentBullets) {
            bullet.step();
        }
    }

    /**
     * Add a peach.
     */
    public void addAPeach(){
        Projectile peach = new Peach(1.0, this.getPrincess(), width, height);
        this.currentPeaches.add(peach);
    }

    /**
     * Add player.
     *
     * @param name  Name of Player's Unicorn
     * @param pos   PLayer's current Position
     * @param state State => This corresponds to the Direction ENUM we defined earlier
     */
//manage players
    public void addPlayer(String name, Position pos, CharacterState state) {
        this.players.put(name, new Unicorn (name, 3, false, pos, state));
        castChanges();
    }

    /**
     * Gets players hash map.
     *
     * @return the players hash map
     */
    public HashMap<String, Unicorn> getPlayersHashMap() {
        return this.players;
    }

    /**
     * Update player position.
     *
     * @param name Name of Player's Unicorn
     * @param m    Motion : The direction which the player is moving in
     */
//chang player position
    public void updatePlayerPosition(String name, Motion m) {
        Unicorn player = this.players.get(name);
        player.walk(m);
        this.players.put(name,player);
    }

    /**
     * Gets bullets.
     *
     * @return the bullets
     */
    public List<Bullet> getBullets() {
        return this.currentBullets;
    }


    /**
     * Gets peaches.
     *
     * @return the peaches
     */
    public List<Projectile> getPeaches() {
        return this.currentPeaches;
    }


    /**
     * The interface Callback.
     */
//manage callback
    public interface Callback {
        /**
         * Universe changed.
         *
         * @param u the u
         */
        void universeChanged(Universe u);
    }

    /**
     * Gets joystick.
     *
     * @return the joystick
     */
//manage joystick
    public Joystick getJoystick() {
        return this.joystick ;
    }

    /**
     * Set actuator for joystick.
     *
     * @param eventPos the event pos
     */
    public void setActuatorForJoystick(Position eventPos){
        this.joystick.setActuator(eventPos);
    }

    /**
     * Sets is pressed for joystick.
     *
     * @param pressed Boolean value indicating that joystick is pressed or not
     */
    public void setIsPressedForJoystick(boolean pressed) {
        this.joystick.setIsPressed(pressed);
    }


    /**
     * Reset actuator for joystick.
     */
    public void resetActuatorForJoystick() {
        this.joystick.resetActuator(); }

    /**
     * Step updates the universe every 20 ms
     * <p>
     * There are a few things in the universe which are updated with each iteration
     * 1. Princess Peach's position
     * 2. The Player's existing Position ( The player can only update his position and no other player's position.
     * 3. Bullet Amount and Positions
     * 4. Any existing collisions that a unicorn might have with a bullet
     * 5. We then update the local state of all the player positions by making a call to firebase
     */
    public void step() {

        // Update Princess Peach's position
        this.princess.spin();
        this.princess.stroll();

        // Update Player's existing position
        this.joystick.update();
        this.players.get(this.currentPlayerName).updatePositionState(this.joystick.getActuatorX(), this.joystick.getActuatorY());
        this.updateCurrentBulletPosition();
        this.fph.updateMove(this.players.get(this.currentPlayerName).getPosition().shortString());
        this.players.get(this.currentPlayerName).UnicornStep();

        // We Add a bullet every 20 calls to step ~ 400 MS
        if(this.steps % this.period == 0){
            this.addABullet();
        }
        this.steps += 1;


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

    /**
     * Sets call back.
     *
     * @param c the c
     */
    public void setCallBack(Callback c) {
        callback = c;
    }

    /**
     * Cast changes.
     */
    protected void castChanges() {
        if (callback != null) {
            callback.universeChanged(this);
        }
    }

}
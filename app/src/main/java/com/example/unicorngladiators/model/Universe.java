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
    private Room room ;
    private FirebasePlayerHandler fph;
    private final String currentPlayerName, currentPlayerUID;
    private List<Peach> currentPeaches;

    private long steps;
    private long period;

    private boolean snap = false;

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
        this.joystick = new Joystick(width, height);
        this.height = height;
        this.width = width;
        this.fph = new FirebasePlayerHandler(currentPlayerUID);

        this.currentPlayerUID = currentPlayerUID;

        this.currentPlayerName = currentPlayerName;
        Log.d(TAG, "Current player name is " + this.currentPlayerName);

        //initialize projectiles
        fph.readRoomStates(room.getId());
        this.room = fph.getRoom();
        this.room.setPlayer_pos(room.getPlayer_pos());
        this.room.setPlayer_scores(room.getPlayer_scores());

        this.bullets = this.room.getBullets();
        Log.d(TAG, "Bullets in uni init:");
        //for (Bullet i: bullets)
        //    Log.d(TAG, "Bullet: " + i);
        this.bulletIndex = 0;
        this.currentBullets = new ArrayList<Bullet>();
        this.currentPeaches = new ArrayList<Peach>();

        //init the players:
        System.out.println("all the players: "+this.room.getPlayer_ids());
        for (String puids : this.room.getPlayer_ids().keySet()) {
            System.out.println("adding "+puids);
            this.addPlayer(this.room.getPlayerName(puids), this.room.getPlayer_pos().get(puids), CharacterState.RIGHT1);
        }
        this.steps = 1;
        this.period = 10;
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
     * Get all player's lives as a Hashmap where keys are player names and values are number of lives
     * @return
     */
    public HashMap<String, Integer> getPlayersLives() {
        HashMap<String, Integer> map = this.room.getPlayer_scores();
        HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
        for (String id : map.keySet()){
            nameMap.put(this.room.getPlayerName(id), map.get(id));
        }
        return nameMap;
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

    static public boolean validCollisionPeach(Peach x, Unicorn y, int distance){
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
                        bullet.setIsUsed(true);
                    } catch (Exception excep) {
                        excep.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    static public void checkCollisionPeach(HashMap<String, Unicorn> e,List<Peach> b){
        for(Map.Entry<String,Unicorn> entry:e.entrySet()){
            Unicorn unicorn = entry.getValue();
            for(Peach peach:b){
                if(Universe.validCollisionPeach(peach,unicorn,200)){
                    try {
                        unicorn.takePeach();
                        peach.setIsUsed(true);
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
        Universe.checkCollisionPeach(this.players,this.currentPeaches);
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
     * Update all existing bullet positions.
     */
    public void updateCurrentBulletPosition(){
        for (Bullet bullet : this.currentBullets) {
            bullet.step();
        }
    }


    public void updateCurrentPeachPosition(){
        for (Peach peach : this.currentPeaches) {
            peach.step();
        }
    }

    public void addAPeach(){
        Peach peach = new Peach(5.0, this.princess, width, height);
        Log.d("peach position", String.valueOf(peach.getPosition()));
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
    public List<Peach> getPeaches() {
        return this.currentPeaches;
    }

    /**
     * Get current player's name
     * @return the player's name
     */
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    /**
     * Get the current player's id
     * @return the player's id
     */

    public String getCurrentPlayerUID() {
        return currentPlayerUID;
    }

    /**
     * The interface Callback.
     */
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
    public void step(long elapsedTime) {
        Log.d(TAG, ("Elapsed time = " + Long.toString(elapsedTime)));

        this.princess.stroll();

        if (elapsedTime%5000 >= 0 && elapsedTime%5000 <= 140) {
            this.addAPeach();
        }

        // Update Player's existing position
        this.joystick.update();
        this.players.get(this.currentPlayerName).updatePositionState(this.joystick.getActuatorX(), this.joystick.getActuatorY());
        this.players.get(this.currentPlayerName).flash();
        this.updateCurrentBulletPosition();
        this.updateCurrentPeachPosition();

        // Update Players's position and lives on Firebase
        this.fph.updateMove(this.players.get(this.currentPlayerName).getPosition().shortString());
        this.fph.updateScore(this.players.get(this.currentPlayerName).getLives());
        this.room.getPlayer_scores().put(this.currentPlayerUID, this.players.get(this.currentPlayerName).getLives());
        this.players.get(this.currentPlayerName).UnicornStep();

        // We Add a bullet every 20 calls to step ~ 400 MS
        if(this.steps % this.period == 0){
            this.addABullet();
        }
        this.steps += 1;

        // Checking for Bullet Collisions here
        if (this.players.get(this.currentPlayerName).getLives() > 0)
            this.checkCollision();

        this.castChanges();

        int countDead = 0;
        for (String puid : this.room.getPlayer_ids().keySet()) {

            if (!puid.equals(this.currentPlayerUID)){
                Position posToUpdate = this.room.getPlayer_pos().get(puid);
                Position curPos = this.players.get(this.room.getPlayerName(puid)).getPosition();
                this.players.get(this.room.getPlayerName(puid))
                        .updatePositionState(
                                posToUpdate.getX() - curPos.getX(),
                                posToUpdate.getY() - curPos.getY());
                this.players.get(this.room.getPlayerName(puid))
                        .setLives(this.room.getPlayer_scores().get(puid));
            }
            if(this.room.getPlayer_scores().get(puid) == 0) {
                countDead++;
                this.players.get(this.room.getPlayerName(puid)).setVisible(true);
            }
        }
        Log.d(TAG, this.room.getPlayer_scores()+"dead count: "+countDead);

        if(countDead >= this.room.getNum_players() - 1)
            this.snapping();
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

    /**
     * Check for the end of the game/Universe.
     * @return
     */
    public boolean snapped(){
        return this.snap;
    }

    /**
     * End the game/Universe.
     */
    public void snapping(){
        fph.leaveRoom();
        this.snap = true;
    }

    /**
     * Getter for the Room object.
     * @return
     */
    public Room getRoom(){
        return this.room;
    }
}
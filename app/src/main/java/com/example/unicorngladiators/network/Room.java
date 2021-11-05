package com.example.unicorngladiators.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.projectiles.Bullet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Room class represent the waiting room states.  It initializes
 * the Bullet object list (in string representation) and allows for
 * joining waiting room and starting a game.
 */
public class Room implements Serializable {
    private boolean start;
    private boolean end;
    private String id;
    private int num_players;
    private HashMap<String, String> player_ids = new HashMap<String, String>();
    private HashMap<String, Integer> player_scores = new HashMap<String, Integer>();
    private HashMap<String, Position> player_pos = new HashMap<String, Position>();
    private List<Bullet> bullets;
    private List<String> bullet_strings;

    /**
     * This constructor takes in a room ID string.  It initializes the start (whether the game started)
     * and end state, the number of player count: num_players, the Bullet object list as a new ArrayList,
     * the Bullet string representation list as a new ArrayList.
     * @param id
     */
    public Room(String id){
        this.start = false;
        this.end = false;
        this.id = id;
        this.num_players = 0;
        this.bullets = new ArrayList<Bullet>();
        this.bullet_strings = new ArrayList<String>();
    }

    /**
     * Add a new player to the room.  This uses the player unique ID: playerUid,
     * the player name: playerName, and the initial position p.
     * @param playerUid
     * @param playerName
     * @param p
     */
    public void addPlayer(String playerUid, String playerName, Position p){
        player_ids.put(playerUid, playerName);
        player_pos.put(playerUid, p);
        player_scores.put(playerUid, 0);
        num_players++;
    }

    /**
     * This remove the player from the Room using the player unique ID.
     * @param playerUid
     */
    public void removePlayer(String playerUid){
        player_ids.remove(playerUid);
        num_players--;
    }

    /**
     * This method updates the player states i.e. their position and their scores.
     * It identify the player using the player unique ID and the HashMap states
     * hold the score and position values.
     * @param playerUid
     * @param states
     */
    public void updatePlayerState(String playerUid, HashMap<String, Object> states){
        player_pos.put(playerUid, new Position((String) states.get("pos")));
        Long tmp = new Long(0);
        tmp = (Long) states.get("score");
        player_scores.put(playerUid, tmp.intValue());
    }

    /**
     * Getter for player scores.
     * @return
     */
    public HashMap<String, Integer> getPlayer_scores() {
        return player_scores;
    }

    /**
     * Getter for player positions.
     * @return
     */
    public HashMap<String, Position> getPlayer_pos() {
        return player_pos;
    }

    /**
     * Getter for player name using their unique ID.
     * @param playerUid
     * @return
     */
    public String getPlayerName(String playerUid){
        return this.player_ids.get(playerUid);
    }

    /**
     * Setter for the player unique ID HashMap.
     * @param player_ids
     */
    public void setPlayerIds(HashMap<String, String> player_ids){
        this.player_ids = player_ids;
        this.num_players = this.player_ids.size();
    }

    /**
     * Setter for player scores.
     * @param player_scores
     */
    public void setPlayer_scores(HashMap<String, Integer> player_scores) {
        this.player_scores = player_scores;
    }

    /**
     * Setter for player positions.
     * @param player_pos
     */
    public void setPlayer_pos(HashMap<String, Position> player_pos) {
        this.player_pos = player_pos;
    }

    /**
     * Setter for the start state.
     * @param start
     */
    public void setStart(boolean start){
        this.start = start;
    }

    /**
     * Setter for the end state.
     * @param end
     */
    public void setEnd(boolean end){
        this.end = end;
    }


    /**
     * Getter for the start state.
     * @return
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Getter for the end state.
     * @return
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * Getter for the room ID.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the number of players.
     * @return
     */
    public int getNum_players() {
        return num_players;
    }

    /**
     * Getter for the player unique ID and names map.
     * @return
     */
    public HashMap<String, String> getPlayer_ids() {
        return player_ids;
    }

    /**
     * Getter for the Bullet object list initialized in the room.
     * @return
     */
    public List<Bullet> getBullets() {
        if(this.bullets.size() > 0) {
            try {
                this.bullets.get(0).toString();
            } catch (Exception e) {
                this.bullets = new ArrayList<>();
                for(String s: this.bullet_strings)
                    this.bullets.add(Bullet.fromString(s));
            }
        } else{
            for(String s: this.bullet_strings)
                this.bullets.add(Bullet.fromString(s));
        }

        return this.bullets;
    }

    /**
     * Setter for the Bullet string representation list.
     * @param bullets
     */
    public void setBullets(List<String> bullets) {
        this.bullet_strings = bullets;
    }

}

package com.example.unicorngladiators.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.projectiles.Bullet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public Room(String id){
        this.start = false;
        this.end = false;
        this.id = id;
        this.num_players = 0;
        this.bullets = new ArrayList<Bullet>();
        this.bullet_strings = new ArrayList<String>();
    }


    public void addPlayer(String playerUid, String playerName){
        player_ids.put(playerUid, playerName);
        num_players++;
    }

    public void removePlayer(String playerUid){
        player_ids.remove(playerUid);
        num_players--;
    }

    public void updatePlayerState(String playerUid, HashMap<String, Object> states){
        player_pos.put(playerUid, new Position((String) states.get("pos")));
        player_scores.put(playerUid, (Integer) states.get("score"));
    }

    public String getPlayerName(String playerUid){
        return this.player_ids.get(playerUid);
    }

    public void setPlayerIds(HashMap<String, String> player_ids){
        this.player_ids = player_ids;
        this.num_players = this.player_ids.size();
    }

    public void setStart(boolean start){
        this.start = start;
    }

    public void setEnd(boolean end){
        this.end = end;
    }


    public boolean isStart() {
        return start;
    }

    public boolean isEnd() {
        return end;
    }

    public String getId() {
        return id;
    }

    public int getNum_players() {
        return num_players;
    }

    public HashMap<String, String> getPlayer_ids() {
        return player_ids;
    }

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

    public void setBullets(List<String> bullets) {
        this.bullet_strings = bullets;
    }

}

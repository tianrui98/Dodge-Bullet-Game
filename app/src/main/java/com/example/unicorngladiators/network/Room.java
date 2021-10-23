package com.example.unicorngladiators.network;

import com.example.unicorngladiators.model.projectiles.Bullet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room {
    private boolean start;
    private boolean end;
    private String id;
    private int num_players;
    private HashMap<String, String> player_ids = new HashMap<String, String>();
    private List<Bullet> bullets;

    public Room(String id){
        this.start = false;
        this.end = false;
        this.id = id;
        this.num_players = 0;
        this.bullets = new ArrayList<Bullet>();
    }

    public void addPlayer(String playerUid, String playerName){
        player_ids.put(playerUid, playerName);
        num_players++;
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
        return bullets;
    }

    public void setBullets(List<String> bullets) {
        for(String s: bullets){

        }
    }
}

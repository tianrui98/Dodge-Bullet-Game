package com.example.unicorngladiators.network;

import com.example.unicorngladiators.model.projectiles.Bullet;

import java.util.HashMap;

public class Room {
    private boolean start;
    private boolean end;
    private String id;
    private int num_players;
    private HashMap<String, String> player_ids = new HashMap<String, String>();

    public Bullet[] getBullets() {
        return bullets;
    }

    public void setBullets(Bullet[] bullets) {
        this.bullets = bullets;
    }

    private Bullet bullets[];

    public Room(String id){
        this.start = false;
        this.end = false;
        this.id = id;
        this.num_players = 0;
        this.bullets = new Bullet[100];
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
        this.num_players = player_ids.size();
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
}

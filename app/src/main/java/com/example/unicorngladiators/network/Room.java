package com.example.unicorngladiators.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.unicorngladiators.model.projectiles.Bullet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Room implements Parcelable {
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

    protected Room(Parcel in) {
        start = in.readByte() != 0;
        end = in.readByte() != 0;
        id = in.readString();
        num_players = in.readInt();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public void addPlayer(String playerUid, String playerName){
        player_ids.put(playerUid, playerName);
        num_players++;
    }

    public void removePlayer(String playerUid){
        player_ids.remove(playerUid);
        num_players--;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (start ? 1 : 0));
        parcel.writeByte((byte) (end ? 1 : 0));
        parcel.writeString(id);
        parcel.writeInt(num_players);
    }
}

package com.example.unicorngladiators.model;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Universe {
    List<Unicorn> players = new Vector<>();
    Princess princess;

    //manage princess (npc)
    public void addPrincess(int x, int y) {
        princess = new Princess(x, y);
    }

    public Princess getPrincess() {
        return princess;
    }

    //TODO manage projectiles



    //manage players
    public void addPlayer(String name, String color, int x, int y) {
        players.add(new Unicorn (name, color, 3, false, x, y));
        castChanges();
    }
    public Collection<Unicorn> getPlayers() {
        return players;
    }

    //manage callback
    private interface Callback {
        void universeChanged ( Universe u ) ;
    }
    public void setCallBack(Callback c) {
        callback = c;
    }
    public void addCallBack (Callback c ) {
        this.callback = c;
    }
    protected void castChanges() {
        if (callback != null) {
            callback.universeChanged(this);
        }
    }
    private Callback callback = null;

}
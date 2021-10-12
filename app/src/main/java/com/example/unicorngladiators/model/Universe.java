package com.example.unicorngladiators.model;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Universe {
    List<Unicorn> players = new Vector<>();
    Princess princess = new Princess(10,10);

    public Universe(List<Unicorn> players) {
        this.players = players;
    }

    //manage princess (npc)
    public Princess getPrincess() {
        return this.princess;
    }

    public void updatePrincess(CharacterState state, Position pos) {
        this.princess.changeState(state);
        this.princess.changePosition(pos);
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
    public interface Callback {
        void universeChanged ( Universe u ) ;
    }

    private Callback  callback = null;
    public void setCallBack(Callback  c) {
        callback = c;
    }
    public void addCallBack (Callback  c ) {
        this.callback = c;
    }
    protected void castChanges() {
        if (callback != null) {
            callback.universeChanged(this);
        }
    }


}
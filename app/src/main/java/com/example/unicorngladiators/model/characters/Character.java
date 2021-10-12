package com.example.unicorngladiators.model.characters;

// Generic character class. Can walk in four directions

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;

public class Character {
    private Position pos;
    private CharacterState state;
    private Callback callback = null;

    public Character(int x, int y) {
        this.pos = new Position(x, y);
        this.state = CharacterState.FRONT;
    }

    //p.x and p.y means how many units to move along x and y
    public void walk(Position p) {
        this.pos.add(p);
    }

    public Position getPosition() {
        return this.pos;
    }

    public CharacterState getState(){return this.state;}


    //manage callback
    private interface Callback {
        void characterChanged ( Character character) ;
    }
    public void setCallBack(Callback c) {
        callback = c;
    }

    public void addCallBack (Callback c ) {
        this.callback = c;
    }

    protected void castChanges() {
        if (callback != null) {
            callback.characterChanged(this);
        }
    }

}

package com.example.unicorngladiators.model.characters;

// Generic character class. Can walk in four directions

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;

public class Character {
    private Position pos;
    private CharacterState state;

    public Character(Position pos, CharacterState state) {
        this.pos = pos;
        this.state = state;
    }

    //p.x and p.y means how many units to move along x and y
    public void walk(Motion p) {
        // update position
        this.pos.add(p);

        // TODO update state when character is moving
        }


    public Position getPosition() {
        return this.pos;
    }

    public CharacterState getState(){return this.state;}

    public void setPosition (Position pos) {this.pos = pos;}

    public void setState (CharacterState state) {this.state = state;}


}

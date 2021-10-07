package com.example.unicorngladiators.model.characters;

// Generic character class. Can walk in four directions

import com.example.unicorngladiators.model.Position;

public class Character {
    private Position pos;

    public Character(int x, int y) {
        this.pos = new Position(x, y);
    }

    //p.x and p.y means how many units to move along x and y
    public void Walk(Position p) {
        this.pos.add(p);
    }

    public Position getPosition() {
        return this.pos;
    }
}

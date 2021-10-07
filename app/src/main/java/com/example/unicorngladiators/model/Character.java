package com.example.unicorngladiators.model;

// Generic character class. Can walk in four directions

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

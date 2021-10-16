package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;

public class Direction {
    private Position from, offset;

    public Direction(int fromX, int fromY, int offsetX, int offsetY){
        this.from = new Position(fromX, fromY);
        this.offset = new Position(offsetX, offsetY);
    }

    public Direction(Position from, Position offset){
        this.from = from;
        this.offset = this.offset;
    }

    public Position getOffset(){
        return this.offset;
    }
}
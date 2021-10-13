package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;

public class Direction {
    private Position from, to, offset;

    public Direction(int fromX, int fromY, int toX, int toY){
        this.from = new Position(fromX, fromY);
        this.to = new Position(toX, toY);
    }

    public Direction(Position from, Position to){
        this.from = from;
        this.to = to;
    }

    public Position getOffset(){
        this.offset = this.to;
        this.offset.subtract(this.from);
        return this.offset;
    }
}
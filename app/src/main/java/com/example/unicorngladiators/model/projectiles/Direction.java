package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;

public class Direction {
    private Position from, to;

    public Direction(int fromX, int fromY, int toX, int toY){
        this.from = Position(fromX, fromY);
        this.to = Position(toX, toY);
    }

    public Direction(Position from, Position to){
        this.from = from;
        this.to = to;
    }

    public Position getOffset(){
        return (this.to.subtract(this.from));
    }
}
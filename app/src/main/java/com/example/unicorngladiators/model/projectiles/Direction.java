package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;

import java.io.Serializable;

/**
 * The Direction class represents the direction of the projectiles by
 * defining the initial direction as well as the vector in terms of offsets.
 */
public class Direction implements Serializable {
    private Position from, offset;

    /**
     * This constructor takes in the initial position and the offset vector in
     * raw integers form.
     * @param fromX
     * @param fromY
     * @param offsetX
     * @param offsetY
     */
    public Direction(int fromX, int fromY, int offsetX, int offsetY){
        this.from = new Position(fromX, fromY);
        this.offset = new Position(offsetX, offsetY);
    }

    /**
     * This constructor takes in the initial position and the offset vector as
     * Position objects.
     * @param from
     * @param offset
     */
    public Direction(Position from, Position offset){
        this.from = from;
        this.offset = offset;
    }

    public Position getOffset(){
        return this.offset;
    }

    public Position getFrom() {
        return from;
    }

    /**
     * This method overrides the Object toString method and gives an output String that is
     * made up of the shortString representation of the Position class and is parsable.
     * @return
     */
    @Override
    public String toString(){
        return String.format("%s, %s", this.from.shortString(), this.offset.shortString());
    }
}
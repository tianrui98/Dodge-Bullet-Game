package com.example.unicorngladiators.model;


import java.io.Serializable;

/**
 * Class inherits Serializable
 */
public class Position implements Serializable {
    private int x, y;

    /**
     * Initializes the position with x, y coordinate tuple
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Initializes the position with string
     * @param shortString x, y coordinate in string
     */
    public Position(String shortString) {
        String[] parts = shortString.split(", ");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
    }

    /**
     * Adds position with input vector
     * @param p delta vector to add to the x, y coordinates
     */
    public void add(Position p) {
        this.x += p.getX();
        this.y += p.getY();
    }

    /**
     * Subtracts position with input vector
     * @param p delta vector to subtract from the x, y coordinates
     */
    public void subtract(Position p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    /**
     * Multiplies position with input vector
     * @param p delta vector to multiply to the x, y coordinates
     */
    public void mult(Position p) {
        this.x *= p.x;
        this.y *= p.y;
    }

    /**
     * Multiplies position with input vector
     * @param factor double to multiply the x,y coordinates with
     */
    public void mult(double factor) {
        this.x *= factor;
        this.y *= factor;
    }

    /**
     * Checks if the object position is equal to current position
     * @param obj Object to check the position
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        if (p != null) return this.x == p.x && this.y == p.y;
        return false;
    }

    /**
     *
     * @return string of x, y coordinates
     */
    public String shortString(){
        return this.x + ", " + this.y;
    }

    /**
     * Changes position to string
     * @return string of the x, y coordinates
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}'; }

    /**
     * Retrieves x coordinate of position
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves y coordinate of position
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Calculates distance from current position to the input position via Pythagorean Theorem
     * @param pos position to check the distance from
     * @return distance from current position to input position
     */
    public int getDistance(Position pos) {
        int xDelta = this.getX() - pos.getX();
        int yDelta = this.getY() - pos.getY();
        return (int) Math.sqrt(xDelta ^ 2 + yDelta ^ 2);
    }
}


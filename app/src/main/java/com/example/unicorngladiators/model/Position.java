package com.example.unicorngladiators.model;


public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(String shortString) {
        String[] parts = shortString.split(", ");
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
    }

    public void add(Position p) {
        this.x += p.getX();
        this.y += p.getY();
    }

    public void subtract(Position p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    public void mult(Position p) {
        this.x *= p.x;
        this.y *= p.y;
    }

    public void mult(double factor) {
        this.x *= factor;
        this.y *= factor;
    }



    public String shortString(){
        return this.x + ", " + this.y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


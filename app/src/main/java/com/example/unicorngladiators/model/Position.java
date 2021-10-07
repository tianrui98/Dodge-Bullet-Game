package com.example.unicorngladiators.model;


public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Position p) {
        this.x += p.x;
        this.y += p.y;
    }

    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        if (p != null) return x == p.x && y == p.y;
        return false;
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


package com.example.unicorngladiators.model;

// Generic character class. Can walk in four directions

public class Character {
    private int x;
    private int y;

    public Character(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //walk in d direction for k units
    public void Walk(Direction d, int k) {
        if (d == Direction.UP){
            this.setX(this.getX() + k);
        }
        else if (d == Direction.DOWN){
            this.setX(this.getX() - k);
        }
        else if (d == Direction.LEFT){
            this.setX(this.getY() - k);
        }
        else{
            this.setX(this.getY() + k);
        }
    }

    //Getter and Setter
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

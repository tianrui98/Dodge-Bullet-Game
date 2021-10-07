package com.example.unicorngladiators.model;

//Princess is an NPC who moves along the arena randomly and throws peaches every 15 seconds

public class Princess {
    private int x ;
    private int y ;

    public Princess (int x, int y){
        this.x = x;
        this.y = y;
    }

    //Walk from point 1 to point 2
    public void Walk( int x1, int y1, int x2, int y2 ) {

    }

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

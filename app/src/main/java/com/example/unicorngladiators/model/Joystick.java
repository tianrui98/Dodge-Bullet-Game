package com.example.unicorngladiators.model;

//TODO implement Joystick based on this https://www.youtube.com/watch?v=3oZ2jt0hQmo&list=PL2EfDMM6n_LYJdzaOQ5jZZ3Dj5L4tbAuM&index=5
public class Joystick {
    private int hat;
    private int radius;
    private int posY;
    private int posX;

    public Joystick(){
        this.posX = 1000;
        this.posY = 1000;
        this.radius = 30;
        this.hat = 50;
    }

    public void updatePosition(Motion m){
        this.posX += m.getX();
        this.posY += m.getY();
    }

    public Position getPosition(){
        Position p = new Position(posX, posY);
        return p;
    }

    public int getRadius(){
        return radius;
    }



}

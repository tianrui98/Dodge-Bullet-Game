package com.example.unicorngladiators.model;

import android.util.Log;

//TODO implement Joystick based on this https://www.youtube.com/watch?v=3oZ2jt0hQmo&list=PL2EfDMM6n_LYJdzaOQ5jZZ3Dj5L4tbAuM&index=5
public class Joystick {

    private boolean isPressed;
    private final int innerRadius;
    private final int outerRadius;
    private int innerPosY;
    private int innerPosX;
    private final int outerPosY;
    private final int outerPosX;
    private double actuatorX;
    private double actuatorY;

    public Joystick(){
        this.innerPosX = 1000;
        this.innerPosY = 1600;
        this.outerPosX = 1000;
        this.outerPosY = 1600;
        innerRadius = 30;
        outerRadius = 50;
        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
        this.isPressed = false;
    }


    public Position getInnerCenterPosition(){
        Position p = new Position(this.innerPosX, this.innerPosY);
        return p;
    }
    public Position getOuterCenterPosition(){
        Position p = new Position(this.outerPosX, this.outerPosY);
        return p;
    }

    public int getInnerRadius(){
        return innerRadius;
    }

    public int getOuterRadius() {return outerRadius;}

    public boolean isPressed(Position eventPos) {
        double dX = eventPos.getX() - this.outerPosX ;
        double dY = eventPos.getY() - this.outerPosY ;
        int centerToTouchDistance = (int) Math.sqrt(
                Math.pow(dX, 2) + Math.pow(dY, 2)
        );
        Log.d("Joystick", "CenterToTouchDistance = " + Integer.toString(centerToTouchDistance));
        if (centerToTouchDistance < outerRadius) {

        Log.d("Joystick","joystick is pressed");}
        return centerToTouchDistance < outerRadius;
    }


    public void setIsPressed(boolean pressed) {
        this.isPressed = pressed;
    }

    public boolean getIsPressed() {
        return this.isPressed;
    }


    // not pulling joystick -> 0; pulling all the way -> 1
    public void setActuator(Position eventPos) {
        Log.d("Joystick","set actuator");
        double dX = eventPos.getX() - this.outerPosX ;
        double dY = eventPos.getY() - this.outerPosY ;
        int centerToTouchDistance = (int) Math.sqrt(
                Math.pow(dX, 2) + Math.pow(dY, 2)
        );


        if (centerToTouchDistance < this.outerRadius) {
            this.actuatorX = (double) dX/ outerRadius;
            this.actuatorY = (double) dY / outerRadius;
        } else {
            this.actuatorX = (double) dX/centerToTouchDistance;
            this.actuatorY = (double) dY/centerToTouchDistance;
        }
        Log.d("Joystick", "dX = " + Double.toString(dX) + " " + "Y to " + Double.toString(dY));
        Log.d("Joystick", "Center to Touch Distance = "+ Integer.toString(centerToTouchDistance));
        Log.d("Joystick", "Set Actuator X to " + Double.toString(this.actuatorX) + " " + "Y to " + Double.toString(this.actuatorY));
    }

    public void resetActuator() {
        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
    }

    public void update(){
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        this.innerPosX =  (int) Math.round(this.outerPosX + this.actuatorX * outerRadius);
        this.innerPosY =  (int) Math.round(this.outerPosY + this.actuatorY * outerRadius);
//        if (this.innerPosX != this.outerPosX & this.innerPosY != this.outerPosY) {
//        Log.d("Joystick", "Update inner X to " + Integer.toString(this.innerPosX) + " " + "Y to " + Integer.toString(this.innerPosY));
//    }
    }

    public double getActuatorX() {
        return this.actuatorX;
    }
    public double getActuatorY() {
        return this.actuatorY;
    }
}

package com.example.unicorngladiators.model;

import android.util.Log;

//TODO implement Joystick based on this https://www.youtube.com/watch?v=3oZ2jt0hQmo&list=PL2EfDMM6n_LYJdzaOQ5jZZ3Dj5L4tbAuM&index=5

/**
 * The Joystick class is the state manager of the Joystick component on the GameActivity.
 * It has a MAX_SPEED attribute that can be set.  The rest of attributes can be tuned too.
 */
public class Joystick {

    private static final int MAX_SPEED = 2;
    private boolean isPressed;
    private int innerRadius;
    private int outerRadius;
    private int innerPosY;
    private int innerPosX;
    private final int outerPosY;
    private final int outerPosX;
    private double actuatorX;
    private double actuatorY;

    /**
     * This constructor initialized all of the Joystick attributes to the default values.
     */
    public Joystick(int screenWidth, int screenHeight){
        this.outerPosX = (int) (screenWidth * 0.8);
        this.outerPosY = (int) (screenHeight * 0.8);
        this.innerPosX = 800;
        this.innerPosY = 1400;
        this.outerRadius = (int) (screenWidth * 0.05);
        this.innerRadius = (int) (outerRadius * 0.6);

        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
        this.isPressed = false;
    }

    /**
     * This returns the innerCenter position of the Joystick as a Position object.
     * @return
     */
    public Position getInnerCenterPosition(){
        Position p = new Position(this.innerPosX, this.innerPosY);
        return p;
    }

    /**
     * This returns the outerCenter position of the Joystick as a Position object.
     * @return
     */
    public Position getOuterCenterPosition(){
        Position p = new Position(this.outerPosX, this.outerPosY);
        return p;
    }

    //Getter
    public int getInnerRadius(){
        return this.innerRadius;
    }
    public int getOuterRadius() {return this.outerRadius;}
    /**
     * This method check whether the Joystick component is pressed with an event.
     * @param eventPos
     * @return
     */
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

    /**
     * Setter for isPressed.
     * @param pressed
     */
    public void setIsPressed(boolean pressed) {
        this.isPressed = pressed;
    }

    /**
     * Getter for isPressed.
     * @return
     */
    public boolean getIsPressed() {
        return this.isPressed;
    }


    // not pulling joystick -> 0; pulling all the way -> 1

    /**
     * Setter for actuator displacements using a Position object input of the click event.
     * This calculates the offset from the outer position of the component.
     * @param eventPos
     */
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

    /**
     * This reset the Joystick displacement to 0--back to the center.
     */
    public void resetActuator() {
        this.actuatorX = 0.0;
        this.actuatorY = 0.0;
    }

    /**
     * This calls the updateInnerCirclePosition method.
     */
    public void update(){
        updateInnerCirclePosition();
    }

    /**
     * This sets the inner circle position using the outerPos and the actuator displacement.
     */
    private void updateInnerCirclePosition() {
        this.innerPosX =  (int) Math.round(this.outerPosX + this.actuatorX * outerRadius);
        this.innerPosY =  (int) Math.round(this.outerPosY + this.actuatorY * outerRadius);
//        if (this.innerPosX != this.outerPosX & this.innerPosY != this.outerPosY) {
//        Log.d("Joystick", "Update inner X to " + Integer.toString(this.innerPosX) + " " + "Y to " + Integer.toString(this.innerPosY));
//    }
    }

    /**
     * Getter for the x (horizontal) component of the actuator displacement.
     * @return
     */
    public double getActuatorX() {
        return this.actuatorX;
    }

    /**
     * Getter for the y (vertical) component of the actuator displacement.
     * @return
     */
    public double getActuatorY() {
        return this.actuatorY;
    }
}

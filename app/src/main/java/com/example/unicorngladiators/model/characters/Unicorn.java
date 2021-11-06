package com.example.unicorngladiators.model.characters;

import android.util.Log;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;

import java.util.concurrent.TimeUnit;

/**
 * A Unicorn object has:
 * name
 * color
 * lives (remaining)
 * invulnerable
 * alive
 * x, y position (of its center on the grid)
 *
 * It can perform two actions
 * takeBullet
 * takePeach
 */
public class Unicorn extends Character {
    private String name;
    private int lives;
    private boolean isInvulnerable;
    private int MAX_SPEED = 50;
    private int invulnerabilityCounter;
    private int invulnerabilityTimer = 50;
    private int xBound;
    private int yBound;

    /**
     * This constructor takes in all its attributes and initialized it.
     * @param name
     * @param lives
     * @param isInvulnerable
     * @param pos
     * @param state
     */
    public Unicorn(String name, int lives, boolean isInvulnerable, Position pos, CharacterState state, int yBound, int xBound) {
        super(pos, state);
        this.name = name;
        this.lives = lives;
        this.isInvulnerable = isInvulnerable;
        this.yBound = yBound;
        this.xBound = xBound;
    }

    public Unicorn(String name, int lives, boolean isInvulnerable, Position pos, CharacterState state) {
        super(pos, state);
        this.name = name;
        this.lives = lives;
        this.isInvulnerable = isInvulnerable;
        this.invulnerabilityCounter = 0;
        this.xBound = 1200;
        this.yBound = 1800;
    }

    /**
     * This is the behavior of the Unicorn if it is hit by a bullet.
     * If the unicorn is invulnerable then nothing happens.
     * Else it loses one life and become invulnerable for three seconds.
     * @throws InterruptedException
     */
    public void takeBullet () throws InterruptedException {
        if (!this.getIsInvulnerable() && this.getLives() > 0)
        {
            this.setLives((this.getLives() - 1));
            this.setInvulnerable(true);
            this.invulnerabilityCounter = invulnerabilityTimer;
        }
    }

    public void UnicornStep (){
        if(this.invulnerabilityCounter > 0){
            System.out.println("Unicorn has " + this.invulnerabilityCounter + " seconds of invulnerability left");
            this.invulnerabilityCounter-=1;
            if(this.invulnerabilityCounter==0){
                this.setInvulnerable(false);
            }
        }
    }

    /**
     * This is the behavior of the Unicorn if it is hit by a peach.
     * It gains one life if it is not invulnerable.
     */
    public void takePeach (){
        if (!this.getIsInvulnerable()) {
            this.setLives((this.getLives() + 1));
            this.setInvulnerable(true);
            this.invulnerabilityCounter = invulnerabilityTimer;
        }
    }

    /**
     * Updates the position and state of the unicorn based on the displacement of the Joystick.
     * @param actuatorX Actuator X from the Joystick
     * @param actuatorY Actuator Y from the Joystick
     */
    public void updatePositionState(double actuatorX, double actuatorY){
        int velocityX = (int) Math.round(actuatorX * MAX_SPEED);
        int velocityY = (int) Math.round(actuatorY * MAX_SPEED);
        int currX = this.getPosition().getX();
        int currY = this.getPosition().getY();
        /* Check movement conditions */
        int finalX = currX + velocityX;
        int finalY = currY + velocityY;

        if(finalY <= 50 || finalY >= this.getyBound()-50){
            velocityY = 0;
        }
        if(finalX <= 50 || finalX >= this.getxBound() - 200 ){
            velocityX = 0;
        }
//
//        if (currY <= 50  && velocityY < 0) {
//            velocityY = 0;
//        }
//        if (this.getyBound() - currY <= 50 && velocityY > 0) {
//            velocityY = 0;
//        }
//        if (currX <= 50  && velocityX < 0) {
//            velocityX = 0;
//        }
//        if (this.getxBound() - currX <= 50 && velocityX > 0) {
//            velocityX = 0;
//        }
        this.walk(new Motion(velocityX, velocityY) );
    }

    /**
     * This overloaded method takes in the actual motion rather than the displacement of the Joystick.
     * @param actuatorX
     * @param actuatorY
     */
    public void updatePositionState(int actuatorX, int actuatorY){
        this.walk(new Motion(actuatorX, actuatorY));
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public boolean getIsInvulnerable() {
        return isInvulnerable;
    }

    public int getyBound() {return this.yBound;}

    public int getxBound() {return this.xBound;}

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setInvulnerable(boolean isInvulnerable) {
        this.isInvulnerable = isInvulnerable;
    }


}

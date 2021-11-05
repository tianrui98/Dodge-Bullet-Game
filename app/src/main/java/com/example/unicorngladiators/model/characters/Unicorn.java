package com.example.unicorngladiators.model.characters;

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

    /**
     * This constructor takes in all its attributes and initialized it.
     * @param name
     * @param lives
     * @param isInvulnerable
     * @param pos
     * @param state
     */
    public Unicorn(String name, int lives, boolean isInvulnerable, Position pos, CharacterState state) {
        super(pos, state);
        this.name = name;
        this.lives = lives;
        this.isInvulnerable = isInvulnerable;
    }

    /**
     * This is the behavior of the Unicorn if it is hit by a bullet.
     * If the unicorn is invulnerable then nothing happens.
     * Else it loses one life and become invulnerable for three seconds.
     * @throws InterruptedException
     */
    public void takeBullet () throws InterruptedException {
        if (!this.getIsInvulnerable())
        {
        this.setLives((this.getLives() - 1));
        this.setInvulnerable(true);
        TimeUnit.SECONDS.sleep(3);
        this.setInvulnerable(false);
        }
    }

    /**
     * This is the behavior of the Unicorn if it is hit by a peach.
     * It gains one life if it is not invulnerable.
     */
    public void takePeach (){
        if (!this.getIsInvulnerable()) {
            this.setLives((this.getLives() + 1));
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

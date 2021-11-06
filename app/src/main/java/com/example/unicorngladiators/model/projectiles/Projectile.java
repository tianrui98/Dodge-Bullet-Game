package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Unicorn;

/**
 * The Projectile abstract class defines default behaviors for the projectile child classes.
 * It defines the direction, speed, and speedUp attributes.
 */
public abstract class Projectile {
    private static double speedUp = 5;
    private double speed = 1;

    /**
     * Getter for projectile direction.
     * @return
     */
    public Direction getDirection() {
        return direction;
    }

    private Direction direction;
    private Position offset;
    private Position currentPos;

    /**
     * Default hit behavior of a projectile on a Character.
     * @param c
     * @throws InterruptedException
     */
    public void hit(Unicorn c) throws InterruptedException {
        return;
    }

    /**
     * Setter for speedUp variable.
     * @param speedUp
     */
    public void setSpeedUp(double speedUp){
        this.speedUp = speedUp;
    }

    /**
     * Setter for Direction and calculate the offset.
     * @param direction
     */
    public void setDirection(Direction direction){
        this.direction = direction;
        this.offset = direction.getOffset();
        this.offset.mult(this.speedUp);
    }

    /**
     * Getter for projectile's speed.
     * @return
     */
    public double getSpeed(){
        return this.speed;
    }

    /**
     * Setter for projectile's speed.
     * @param speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Speed up the projectile using its speedUp variable.
     */
    public void speedUpProjectile(){
        this.speed *= this.speedUp;
    }

    /**
     * Calculate the projectile current position with the next step.
     */
    public void step(){
        this.currentPos.add(this.offset);
    }

    /**
     * Getter for the projectile's position.
     * @return
     */
    public Position getPosition(){
        return this.currentPos;
    }

    /**
     * Setter for the projectile's position.
     * @param pos
     */
    public void setPosition(Position pos) { this.currentPos = pos;}
}
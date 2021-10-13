package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.projectiles.Direction;
import com.example.unicorngladiators.model.Position;

//TODO implement projectile class
public abstract class Projectile {
    private static double speedUp = 1.1;
    private double speed = 1;
    private Direction direction;
    private Position offset;
    private Position currentPos;

    public void hit(Character c) {
        return;
    }

    public void setSpeedUp(double speedUp){
        this.speedUp = speedUp;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        this.offset = direction.getOffset();
        this.offset.mult(this.speedUp);
    }

    public double getSpeed(){
        return this.speed;
    }

    public void speedUpProjectile(){
        this.speed *= this.speedUp;
    }

    public void step(){
        this.currentPos.add(this.offset);
    }
}

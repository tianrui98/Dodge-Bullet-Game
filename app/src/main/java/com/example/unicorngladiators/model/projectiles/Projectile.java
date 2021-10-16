package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Unicorn;

//TODO implement projectile class
public abstract class Projectile {
    private static double speedUp = 1.1;
    private double speed = 1;
    private Direction direction;
    private Position offset;
    private Position currentPos;

    public void hit(Unicorn c) throws InterruptedException {
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void speedUpProjectile(){
        this.speed *= this.speedUp;
    }

    public void step(){
        this.currentPos.add(this.offset);
    }
}

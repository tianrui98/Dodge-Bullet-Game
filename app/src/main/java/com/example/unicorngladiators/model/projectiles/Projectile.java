package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.projectiles.Direction;
import com.example.unicorngladiators.model.Position;

//TODO implement projectile class
public abstract class Projectile {
    private static int speedUp = 1.1;
    private int speed = 1;
    private Direction direction;
    private Position offset;
    private Position currentPos;

    public void hit(Character c) {
        return;
    }

    public void setSpeedUp(int speedUp){
        this.speedUp = speedUp;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        this.offset = direction.getOffset;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void speedUpProjectile(){
        this.speed *= this.speedUp;
    }

    public void step(){
        this.currentPos.add(this.offset.mult(this.speedUp));
    }
}

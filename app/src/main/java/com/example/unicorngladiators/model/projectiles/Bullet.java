package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.Random;
// TODO implement Bullet class
public class Bullet extends Projectile {
    public Bullet(double speed,int maxX, int maxY) {
        this.setSpeed(speed);
        Random rand = new Random();
        int offsetX = rand.nextInt(5);
        int offsetY = rand.nextInt(10);
        int fromX = rand.nextInt(maxX), fromY = rand.nextInt(maxY);
        if (fromX < maxX/2) fromX = 0;
        else {
            fromX = maxX;
            offsetX = 0 - offsetX;
        }

        if (fromY < maxY/2) fromY = 0;
        else {
            fromY = maxY;
            offsetY = 0 - offsetY;
        }


        this.setDirection(
                new Direction(fromX, fromY, offsetX, offsetY));
        this.speedUpProjectile();
    }

    @Override
    public void hit(Unicorn c) throws InterruptedException {
        c.takeBullet();
    }
}

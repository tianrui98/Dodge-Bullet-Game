package com.example.unicorngladiators.model.projectiles;

import java.util.Random;
// TODO implement Bullet class
public class Bullet extends Projectile {
    public Bullet() {
        Random rand = new Random();
        this.setDirection(
                new Direction(
                        rand.nextInt(10),
                        rand.nextInt(10),
                        rand.nextInt(10),
                        rand.nextInt(10)));
        this.speedUpProjectile();
    }
}

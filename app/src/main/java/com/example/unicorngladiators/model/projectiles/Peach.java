package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.Random;

public class Peach extends Projectile {
    public Peach(double speed, Princess p, int maxX, int maxY) {
        this.setSpeed(speed);
        Random rand = new Random();
        int offsetX = rand.nextInt(5);
        int offsetY = rand.nextInt(10);
        Position p_pos = p.getPosition();
        if (p_pos.getX() > maxX/2) offsetX = 0 - offsetX;
        if (p_pos.getY() > maxY/2) offsetY = 0 - offsetY;

        this.setDirection(
                new Direction(p_pos.getX(), p_pos.getY(), offsetX, offsetY));
        this.speedUpProjectile();
    }

    @Override
    public void hit(Unicorn c) {
        c.takePeach();
    }
}
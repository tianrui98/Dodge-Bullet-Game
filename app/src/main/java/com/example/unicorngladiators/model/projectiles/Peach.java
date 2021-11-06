package com.example.unicorngladiators.model.projectiles;

import android.util.Log;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Peach class is just peachy. It is similar to the Bullet class representation but
 * has different hit behaviors and is dependent on Princess Peach.
 */
public class Peach extends Projectile {
    /**
     * This constructor initializes the Peach object using the speed,
     * position of the Princess and the screen constraints.
     * @param speed
     * @param p
     * @param maxX
     * @param maxY
     */
    public Peach(double speed, Princess p, int maxX, int maxY) {
        this.setSpeed(speed);
        Random rand = new Random();
        int offsetX = 2;
        int offsetY = 5;
        Position p_pos = new Position(p.getPosition().getX(), p.getPosition().getY());
        if (p_pos.getX() > maxX/2) offsetX = 0 - offsetX;
        if (p_pos.getY() > maxY/2) offsetY = 0 - offsetY;
        this.setPosition(p_pos);
        this.setDirection(
                new Direction(p_pos.getX(), p_pos.getY(), offsetX, offsetY));
        this.speedUpProjectile();
    }

    /**
     * This method overrides the hit behavior of the Peach on the character.
     * It is defined using the character takePeach behavior.
     * @param c
     */
    @Override
    public void hit(Unicorn c) {
        c.takePeach();
        this.setIsUsed(true);
    }

}
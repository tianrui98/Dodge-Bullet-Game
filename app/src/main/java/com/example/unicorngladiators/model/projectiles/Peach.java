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
 * Peach extends projectile
 */
public class Peach extends Projectile implements Serializable {
    /**
     * Initializes peach object
     * @param speed Initial speed of peach
     * @param p Princess object
     * @param maxX Screen width
     * @param maxY Screen height
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
     * Method increases life of unicorn if it collides with peach
     * @param c Unicorn object
     */
    @Override
    public void hit(Unicorn c) {
        c.takePeach();
    }

}
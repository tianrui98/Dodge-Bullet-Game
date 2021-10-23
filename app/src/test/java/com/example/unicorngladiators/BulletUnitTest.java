package com.example.unicorngladiators;

import org.junit.Test;
import java.lang.Math;

import static org.junit.Assert.*;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.example.unicorngladiators.model.projectiles.Direction;

public class BulletUnitTest {
    /**
     * Unit Tests for Position sub-class of bullets
     */
    @Test
    public void equalPosition() {
        for (int i = 0; i < 1000; i++) {
            int xFrom = (int) (Math.random() * 10);
            int yFrom = (int) (Math.random() * 10);
            int xTo = (int) (Math.random() * 10);
            int yTo = (int) (Math.random() * 10);

            /* First method of initializing Direction */
            Direction dir1 = new Direction(xFrom, yFrom, xTo, yTo);
            /* Second method of initializing Direction */
            Direction dir2 = new Direction(new Position(xFrom, yFrom), new Position(xTo, yTo));

            assertEquals(dir1.getOffset().toString(), dir2.getOffset().toString());
            assertEquals(dir1.getFrom().toString(), dir2.getFrom().toString());
        }
    }

    @Test
    public void initProjectile() {
        for (int i = 0; i < 1000; i++) {
            int xMax = (int) (Math.random() * 100) + 1500;
            int yMax = (int) (Math.random() * 100) + 1500;
            double spd = Math.random() * 10;
            Bullet bullet = new Bullet(spd, xMax, yMax);

        }
    }
}

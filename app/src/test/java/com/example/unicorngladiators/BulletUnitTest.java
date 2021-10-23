package com.example.unicorngladiators;

import org.junit.Test;
import java.lang.Math;
import java.util.List;
import java.util.Random;

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
            /* Check Bullet Speed Equality */
            assertEquals(bullet.getSpeed(), spd, 0.001);
            System.out.println(bullet.toString());
        }
    }

    @Test
    public void testBulletFromString() {
        for (int i = 0; i < 50; i++) {
            int xFrom = (int) (Math.random() * 10);
            int yFrom = (int) (Math.random() * 10);
            int xTo = (int) (Math.random() * 10);
            int yTo = (int) (Math.random() * 10);
            double spd = Math.random() * 3 + 0.4;
            double spdTrunc = Math.round(spd * 100.0) / 100.0;
            // Initializing Bullet String
            String bulletString = String.format("%d,%d,%d,%d,%f",xFrom, yFrom, xTo, yTo, spdTrunc);
            Bullet bullet = Bullet.fromString(bulletString);
        }
    }

    @Test
    public void generateBulletStringListTest() {
        int testSize = 1000;
        double speedup = 1.2;
        double speed = 1;

        List<String> bulletList = Bullet.generateBulletStringList(testSize
                ,200,400,speedup);

        for(int i = 0; i < testSize;i++){
            Bullet tmp = Bullet.fromString(bulletList.get(i));
            assertEquals(tmp.getSpeed(), speed,0.1);
            speed *= speedup;
        }

    }
}

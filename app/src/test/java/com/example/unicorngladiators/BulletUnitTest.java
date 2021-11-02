package com.example.unicorngladiators;

import org.junit.Test;
import java.lang.Math;
import java.util.Random;

import static org.junit.Assert.*;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.projectiles.Direction;

public class BulletUnitTest {
    /**
     * Unit Tests for Position sub-class of bullets
     */
    @Test
    public void equalPosition() {
        for (int i = 0; i < 20; i++) {
            int xFrom = (int) (Math.random() * 10);
            int yFrom = (int) (Math.random() * 10);
            int xTo = (int) (Math.random() * 10);
            int yTo = (int) (Math.random() * 10);

            /* First method of initializing Direction */
            Direction dir1 = new Direction(xFrom, yFrom, xTo, yTo);
            /* Second method of initializing Direction */
            Direction dir2 = new Direction(new Position(xFrom, yFrom), new Position(xTo, yTo));

            assertEquals(dir1.getOffset(), dir1.getOffset());
            assertEquals(dir1.toString(), dir2.toString());
        }
    }
}

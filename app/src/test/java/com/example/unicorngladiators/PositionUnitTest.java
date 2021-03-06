package com.example.unicorngladiators;

import org.junit.Test;
import java.lang.Math;

import static org.junit.Assert.*;

import com.example.unicorngladiators.model.Position;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PositionUnitTest {

    @Test
    public void positionAddTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(1);
            int pos1X = (int) (Math.random() * 20);
            int pos1Y = (int) (Math.random() * 20);
            int pos2X = (int) (Math.random() * 20);
            int pos2Y = (int) (Math.random() * 20);
            Position pos1 = new Position(pos1X, pos1Y);
            Position pos2 = new Position(pos2X, pos2Y);
            pos1.add(pos2);
            assertEquals(pos1.getX(), pos1X + pos2X);
            assertEquals(pos1.getY(), pos1Y + pos2Y);
        }
    }

    @Test
    public void positionSubtractTest() {
        for (int i = 0; i < 20; i++) {
            int pos1X = (int) (Math.random() * 20);
            int pos1Y = (int) (Math.random() * 20);
            int pos2X = (int) (Math.random() * 20);
            int pos2Y = (int) (Math.random() * 20);
            Position pos1 = new Position(pos1X, pos1Y);
            Position pos2 = new Position(pos2X, pos2Y);
            pos1.subtract(pos2);
            assertEquals(pos1.getX(), pos1X - pos2X);
            assertEquals(pos1.getY(), pos1Y - pos2Y);
        }
    }

    @Test
    public void positionMultFactor() {
        for (int i = 0; i < 20; i++) {
            int pos1X = (int) (Math.random() * 20);
            int pos1Y = (int) (Math.random() * 20);
            int coeff = (int) (Math.random() * 30 - 15);
            Position pos1 = new Position(pos1X, pos1Y);
            pos1.mult(coeff);
            assertEquals(pos1.getX(), pos1X * coeff);
            assertEquals(pos1.getY(), pos1Y * coeff);
        }
    }

    @Test
    public void positionMultPosition() {
        for (int i = 0; i < 20; i++) {
            int pos1X = (int) (Math.random() * 20);
            int pos1Y = (int) (Math.random() * 20);
            int pos2X = (int) (Math.random() * 20);
            int pos2Y = (int) (Math.random() * 20);
            Position pos1 = new Position(pos1X, pos1Y);
            Position pos2 = new Position(pos2X, pos2Y);
            pos1.mult(pos2);
            assertEquals(pos1.getX(), pos1X * pos2X);
            assertEquals(pos1.getY(), pos1Y * pos2Y);
        }
    }

    @Test
    public void positionEquals() {
        for (int i = 0; i < 30; i++) {
            int pos1X = (int) (Math.random() * 4);
            int pos1Y = (int) (Math.random() * 4);
            int pos2X = (int) (Math.random() * 4);
            int pos2Y = (int) (Math.random() * 4);
            Position pos1 = new Position(pos1X, pos1Y);
            Position pos2 = new Position(pos2X, pos2Y);
            assertTrue(pos1.equals(pos2) == (pos1X == pos2X && pos1Y == pos2Y));
        }
    }

    @Test
    public void getDistance(){
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(3,4);
        System.out.println(pos1.getDistance(pos2));
        assert(pos1.getDistance(pos2) == 5);
        assert(pos1.getDistance(pos2) == pos2.getDistance(pos1));
    }

}
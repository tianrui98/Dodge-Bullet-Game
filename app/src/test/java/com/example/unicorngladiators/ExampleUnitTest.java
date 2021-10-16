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
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void PositionAddTest() {
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
    public void PositionSubtractTest() {
        for (int i = 0; i < 10; i++) {
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
    public void PositionMultFactor() {
        for (int i = 0; i < 10; i++) {
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
    public void PositionMultPosition() {
        for (int i = 0; i < 10; i++) {
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
}
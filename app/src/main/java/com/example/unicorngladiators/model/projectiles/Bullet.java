package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.characters.Unicorn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bullet is a child class of the Projectile abstract class.  This is used for
 * interacting with the Unicorn class.  It is serializable so it can be passed
 * between activities.  There is a shortString method for optimized representation
 * so it can be used in transmitting the information to Firebase.
 */
public class Bullet extends Projectile implements Serializable {

    /**
     * This constructor is used for defining a random Bullet object with its speed
     * and max screen position.  These max positions are used to confine the bullet
     * initial positions.
     * @param speed
     * @param maxX
     * @param maxY
     */
    public Bullet(double speed,int maxX, int maxY) {
        this.setSpeed(speed);
        Random rand = new Random();
        int offsetX = rand.nextInt(5);
        int offsetY = rand.nextInt(10);
        int fromX = rand.nextInt(maxX), fromY = rand.nextInt(maxY);
        if (fromX < maxX/2) fromX = 0;
        else {
            fromX = maxX;
            offsetX = - offsetX;
        }

        if (fromY < maxY/2) fromY = 0;
        else {
            fromY = maxY;
            offsetY = - offsetY;
        }

        Direction direction = new Direction(fromX, fromY, offsetX, offsetY);
        this.setDirection(direction);
        this.setPosition(direction.getFrom());
    }

    /**
     * This constructor is used for when the shortString representation is
     * parsed and defined the Bullet object with its Direction and speed.
     * @param direction
     * @param speed
     */
    public Bullet(Direction direction, Double speed){
        this.setDirection(direction);
        this.setSpeed(speed);
        this.setPosition(direction.getFrom());
    }

    /**
     * This method overrides the hit behavior of the abstract parent class hit behavior.
     * It is defined using the Unicorn's takeBullet behavior.
     * @param c
     * @throws InterruptedException
     */
    @Override
    public void hit(Unicorn c) throws InterruptedException {
        c.takeBullet();
    }

    /**
     * This method overrides the Object toString method and gives an output String parsable by the fromString method.
     * @return
     */
    @Override
    public String toString(){
        return String.format("%s,%.2f", this.getDirection().toString(), this.getSpeed());
    }

    /**
     * fromString parses the Bullet object from the toString representation.
     * @param stringRepr
     * @return
     */
    public static Bullet fromString(String stringRepr){
        stringRepr = stringRepr.replaceAll("\\s+","");
        String[] splits = stringRepr.split(",");
        //System.out.println("Bullet string: "+ stringRepr);
        //[fromX, fromY, offsetX, offsetY, speed]
        int fromX = Integer.parseInt(splits[0]);
        int fromY = Integer.parseInt(splits[1]);
        int offsetX = Integer.parseInt(splits[2]);
        int offsetY = Integer.parseInt(splits[3]);
        double speed = Double.parseDouble(splits[4]);
        Bullet b = new Bullet(new Direction(fromX, fromY, offsetX, offsetY), speed);
        return b;
    }

    /**
     * Generates the list of Bullets in toString representation so that it can be used by the Firebase handlers.
     * @param number
     * @param height
     * @param width
     * @param speedup
     * @return
     */
    public static List<String> generateBulletStringList(int number, int height,int width, double speedup){
        List<String> bullets = new ArrayList<String>();
        double current_speed = 100.0;
        for(int i=0;i<number;i++) {
            Bullet tmp = new Bullet(current_speed, width, height);
            bullets.add(tmp.toString());
            current_speed *= speedup;
        }
        return bullets;
    }
}
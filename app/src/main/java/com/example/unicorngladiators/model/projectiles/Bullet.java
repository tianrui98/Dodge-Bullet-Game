package com.example.unicorngladiators.model.projectiles;

import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// TODO implement Bullet class
public class Bullet extends Projectile {
    public Bullet(double speed,int maxX, int maxY) {
        this.setSpeed(speed);
        Random rand = new Random();
        int offsetX = rand.nextInt(5);
        int offsetY = rand.nextInt(10);
        int fromX = rand.nextInt(maxX), fromY = rand.nextInt(maxY);
        if (fromX < maxX/2) fromX = 0;
        else {
            fromX = maxX;
            offsetX = 0 - offsetX;
        }

        if (fromY < maxY/2) fromY = 0;
        else {
            fromY = maxY;
            offsetY = 0 - offsetY;
        }


        this.setDirection(
                new Direction(fromX, fromY, offsetX, offsetY));
    }

    public Bullet(Direction direction, Double speed){
        this.setDirection(direction);
        this.setSpeed(speed);
    }

    @Override
    public void hit(Unicorn c) throws InterruptedException {
        c.takeBullet();
    }

    @Override
    public String toString(){
        return String.format("%s,%,.2f", this.getDirection().toString(), this.getSpeed());
    }

    public static Bullet fromString(String stringRepr){
        String[] splits = stringRepr.split(",");
        for(String s: splits)
            System.out.print(s+", ");
        System.out.println();
        //[fromX, fromY, offsetX, offsetY, speed]
        int fromX = Integer.parseInt(splits[0]);
        int fromY = Integer.parseInt(splits[1]);
        int offsetX = Integer.parseInt(splits[2]);
        int offsetY = Integer.parseInt(splits[3]);
        double speed = Double.parseDouble(splits[4]);
        return new Bullet(new Direction(fromX, fromY, offsetX, offsetY), speed);
    }

    public static List<String> generateBulletStringList(int number, int height,int width, double speedup){
        List<String> bullets = new ArrayList<String>();
        double current_speed = 1.0;
        for(int i=0;i<number;i++) {
            Bullet tmp = new Bullet(current_speed, width, height);
            bullets.add(tmp.toString());
            current_speed *= speedup;
        }
        return bullets;
    }
}

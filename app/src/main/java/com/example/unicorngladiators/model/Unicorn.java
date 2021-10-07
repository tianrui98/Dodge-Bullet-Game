package com.example.unicorngladiators.model;

/* A Unicorn object has the following attributes:
- name
- color
- lives (remaining)
- invulnerable
- alive
- x, y position (of its center on the grid)

It can perform two actions
- takeBullet
- takePeach
 */

import java.util.concurrent.TimeUnit;

public class Unicorn extends Character{
    private String name;
    private String color;
    private int lives;
    private boolean isInvulnerable;

    public Unicorn(String name, String color, int lives, boolean isInvulnerable, int x, int y) {
        super(x,y);
        this.name = name;
        this.color = color;
        this.lives = lives;
        this.isInvulnerable = isInvulnerable;
    }

    //if the unicorn is invulnerable -> nothing happens. Else it loses one life and become invulnerable for three seconds
    public void takeBullet () throws InterruptedException {
        if (!this.getIsInvulnerable())
        {
        this.setLives((this.getLives() - 1));
        this.setInvulnerable(true);
        TimeUnit.SECONDS.sleep(3);
        this.setInvulnerable(false);
        }
    }

    //gain one life if it is not invulnerable
    public void takePeach (){
        if (!this.getIsInvulnerable()) {
            this.setLives((this.getLives() + 1));
        }
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getLives() {
        return lives;
    }

    public boolean getIsInvulnerable() {
        return isInvulnerable;
    }


    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setInvulnerable(boolean isInvulnerable) {
        this.isInvulnerable = isInvulnerable;
    }


}

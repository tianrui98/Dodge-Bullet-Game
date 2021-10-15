package com.example.unicorngladiators.model.characters;

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

import android.util.Log;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Character;

import java.util.concurrent.TimeUnit;

public class Unicorn extends Character {
    private String name;
    private int lives;
    private boolean isInvulnerable;

    public Unicorn(String name, int lives, boolean isInvulnerable, Position pos, CharacterState state) {
        super(pos, state);
        this.name = name;
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

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setInvulnerable(boolean isInvulnerable) {
        this.isInvulnerable = isInvulnerable;
    }


}

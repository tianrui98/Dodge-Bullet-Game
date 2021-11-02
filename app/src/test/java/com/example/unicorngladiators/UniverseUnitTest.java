package com.example.unicorngladiators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.example.unicorngladiators.model.projectiles.Direction;
import com.example.unicorngladiators.network.Room;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class UniverseUnitTest {
    /**
     * Unit Tests for Position sub-class of bullets
     */
    @Test
    public void UniverseValidCollide(){
        Position origin = new Position(0,0);
        Bullet b = new Bullet(20.0,200,300);
        b.setPosition(origin);

        Position valid = new Position(10,10);
        Unicorn u = new Unicorn("toto",3,false,valid, CharacterState.BACK1);

        int distance = 200;
        assertTrue(Universe.validCollision(b,u,distance));

    }

    @Test
    public void UniverseInvalidCollide(){
        Position origin = new Position(0,0);
        Bullet b = new Bullet(20.0,200,300);
        b.setPosition(origin);
        Position invalid = new Position(200,200);
        Unicorn v = new Unicorn("toto",3,false,invalid, CharacterState.BACK1);

        int distance = 200;
        assertTrue(!Universe.validCollision(b,v,distance));

    }

    @Test
    public void checkCollisionHelperTest(){
        Position origin = new Position(0,0);
        Bullet b = new Bullet(20.0,200,300);
        b.setPosition(origin);
        Position invalid = new Position(10,10);
        Unicorn v = new Unicorn("toto",3,false,invalid, CharacterState.BACK1);

        ArrayList<Bullet> bulletList = new ArrayList<>();
        bulletList.add(b);

        HashMap<String,Unicorn> unicornHashMap = new HashMap<String,Unicorn>();
        unicornHashMap.put("abcde",v);

        Universe.checkCollisionHelper(unicornHashMap,bulletList);

        assertTrue(v.getLives()==2);

    }

    @Test
    public void checkNoCollisionHelperTest(){
        Position origin = new Position(0,0);
        Bullet b = new Bullet(20.0,200,300);
        b.setPosition(origin);
        Position invalid = new Position(200,200);
        Unicorn v = new Unicorn("toto",3,false,invalid, CharacterState.BACK1);

        ArrayList<Bullet> bulletList = new ArrayList<>();
        bulletList.add(b);

        HashMap<String,Unicorn> unicornHashMap = new HashMap<String,Unicorn>();
        unicornHashMap.put("abcde",v);

        Universe.checkCollisionHelper(unicornHashMap,bulletList);

        assertTrue(v.getLives()==3);

    }

}

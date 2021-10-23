package com.example.unicorngladiators.model.characters;

import android.util.Log;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Character;

/**
 * Princess is an NPC who moves along the arena and throws peaches every 15 seconds
 */
public class Princess extends Character {

    private final String name;
    private final int top_y;
    private final int bottom_y;
    private final int left_x;
    private final int right_x;


    public Princess(Position pos, CharacterState state, int height, int width) {
        super(pos,state);
        this.name = "princess";
        this.top_y = (int) (0.2 * height);
        this.bottom_y = height - (int) (0.2 * height);
        this.left_x = (int) 0.2 * width;
        this.right_x = width - (int) 0.2 * width;
    }

    public void spin (){
        switch (this.getState()){
            case SPECIAL1:
                this.setState(CharacterState.SPECIAL2);
                break;
            case SPECIAL2:
                this.setState(CharacterState.SPECIAL3);
                break;
            case SPECIAL3:
                this.setState(CharacterState.SPECIAL4);
                break;
            case SPECIAL4:
                this.setState(CharacterState.SPECIAL1);
                break;
        }
    }

    public void stroll() {
        Position pos = this.getPosition();
        int dx = pos.getX();
        int dy = pos.getY();
        // walk up
        if (dy <= this.bottom_y && dx <= this.left_x){
            Motion m = new Motion(0, 1);
            this.walk(m);
        }
        // walk left
        else if (dy >= this.top_y && dx <= this.right_x){
            Motion m = new Motion(-10,0);
            this.walk(m);
        }
        // walk down
        else if (dy <= this.top_y && dx >= this.right_x){
            Motion m = new Motion(0,-1);
            this.walk(m);
        }
        // walk right
        else if (dy <= this.bottom_y && dx <= this.right_x){
            Motion m = new Motion(10,0);
            this.walk(m);
        }
    }


}

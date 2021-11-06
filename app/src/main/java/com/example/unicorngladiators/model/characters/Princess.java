package com.example.unicorngladiators.model.characters;


import android.util.Log;

import com.example.unicorngladiators.model.Facing;
import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;

/**
 * Princess is an NPC who moves along the arena and throws peaches every 15 seconds
 */
public class Princess extends Character {
    private Position pos;
    private CharacterState state;
    private final String name;
    private final int top_y;
    private final int bottom_y;
    private final int left_x;
    private final int right_x;
    /**
     * Princess initializes the princess state
     * @param pos Coordinate position of the princess
     * @param state Character state
     * @param height Screen height used to set boundaries for princess
     * @param width Screen width used to set boundaries for princess
     */
    public Princess(Position pos, CharacterState state, int height, int width) {
        super(pos,state);
        this.pos = pos;
        this.state = state;
        this.name = "princess";
        this.top_y = (int) (0.03 * height);
        this.bottom_y = height - (int) (0.2 * height);
        this.left_x = (int) (0.03 * width);
        this.right_x = width - (int) (0.2 * width);
    }

    /**
     * Method to get princess spinning. Only works when princess is throwing a peach.
     */
    public void spin (){
        switch (this.getState()) {
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
                this.setThrowing(false);
                break;
        }
    }

    /**
     * Method to get princess strolling through the arena
     */
    public void stroll() {
        Position pos = this.getPosition();
        int dx = pos.getX();
        int dy = pos.getY();
        Facing dir = this.getFacing();
        // walk up
        if (dy >= this.top_y && dx <= this.left_x){
//            Log.d("princess walking up",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(0,-40);
            this.pos.add(p);
            if (dir != Facing.UP){ this.setFacing(Facing.UP); this.setState(CharacterState.BACK1);}
        }
        // walk right
        else if (dy <= this.top_y && dx <= this.right_x){
//            Log.d("princess walking right",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(40,0);
            this.pos.add(p);
            if (dir != Facing.RIGHT){ this.setFacing(Facing.RIGHT); this.setState(CharacterState.RIGHT1);}
        }
        // walk down
        else if (dy <= this.bottom_y && dx >= this.right_x){
            Log.d("princess walking down",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(0,40);
            this.pos.add(p);
            if (dir != Facing.DOWN){ this.setFacing(Facing.DOWN); this.setState(CharacterState.FRONT1);}
        }
        // walk left
        else if (dy >= this.bottom_y && dx >= this.left_x){
            Log.d("princess walking left",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(-40,0);
            this.pos.add(p);

            if (dir != Facing.LEFT ){ this.setFacing(Facing.LEFT); this.setState(CharacterState.LEFT1); }
        }
    }

    /**
     * Retrieves the current position of princess
     * @return princess position
     */
    public Position getPosition() {
        return this.pos;
    }

    /**
     * Retrieves current state of princess
     * @return princess state
     */
    public CharacterState getState(){return this.state;}

    /**
     * Sets the princess position
     * @param pos position to set the princess position with
     */
    public void setPosition (Position pos) {this.pos = pos;}

    /**
     * Sets the princess state
     * @param state state to set the princess state with
     */
    public void setState (CharacterState state) {this.state = state;}


}

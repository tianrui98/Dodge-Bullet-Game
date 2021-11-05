package com.example.unicorngladiators.model.characters;


import android.util.Log;

import com.example.unicorngladiators.model.Motion;

/**
 * Princess is an NPC who moves along the arena and throws peaches every 15 seconds
 */
public class Princess extends Character {
    private Position pos;
    private CharacterState state;
    private Character.CharacterDirection direction;
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
        this.direction = null;
        this.name = "princess";
        this.top_y = (int) (0.03 * height);
        this.bottom_y = height - (int) (0.2 * height);
        this.left_x = (int) (0.03 * width);
        this.right_x = width - (int) (0.2 * width);
    }

    /**
     * Method to get princess spinning
     */
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

    /**
     * Method to get princess strolling through the arena
     */
    public void stroll() {
        Position pos = this.getPosition();
        int dx = pos.getX();
        int dy = pos.getY();
        Character.CharacterDirection dir = this.getDirection();
        // walk up
        if (dy >= this.top_y && dx <= this.left_x){
//            Log.d("princess walking up",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(0,-40);
            this.pos.add(p);
            this.setState(CharacterState.BACK1);
            this.setDirection(CharacterDirection.UP);
            this.walkUpStateChange();
        }
        // walk right
        else if (dy <= this.top_y && dx <= this.right_x){
//            Log.d("princess walking right",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(40,0);
            this.pos.add(p);
            this.setState(CharacterState.RIGHT1);
            this.setDirection(CharacterDirection.RIGHT);
            this.walkRightStateChange();
        }
        // walk down
        else if (dy <= this.bottom_y && dx >= this.right_x){
            Log.d("princess walking down",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(0,40);
            this.pos.add(p);
            this.setState(CharacterState.FRONT1);
            this.setDirection(CharacterDirection.DOWN);
            this.walkDownStateChange();
        }
        // walk left
        else if (dy >= this.bottom_y && dx >= this.left_x){
            Log.d("princess walking left",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(-40,0);
            this.pos.add(p);
            this.setState(CharacterState.LEFT1);
            this.setDirection(CharacterDirection.LEFT);
            this.walkLeftStateChange();
        }
    }

    /**
     * Method that changes the princess state to face the front while walking down
     */
    public void walkDownStateChange (){
        switch (this.getState()){
            case FRONT1:
                this.setState(CharacterState.FRONT2);
                break;
            case FRONT2:
                this.setState(CharacterState.FRONT3);
                break;
            case FRONT3:
                this.setState(CharacterState.FRONT4);
                break;
            case FRONT4:
                this.setState(CharacterState.FRONT1);
                break;
        }
    }

    /**
     * Method that changes the princess state to face right when walking right
     */
    public void walkRightStateChange(){
        switch (this.getState()){
            case RIGHT1:
                this.setState(CharacterState.RIGHT2);
                break;
            case RIGHT2:
                this.setState(CharacterState.RIGHT3);
                break;
            case RIGHT3:
                this.setState(CharacterState.RIGHT4);
                break;
            case RIGHT4:
                this.setState(CharacterState.RIGHT1);
                break;
        }
    }

    /**
     * Method that changes the princess state to left when walking left
     */
    public void walkLeftStateChange(){
        switch (this.getState()){
            case LEFT1:
                this.setState(CharacterState.LEFT2);
                break;
            case LEFT2:
                this.setState(CharacterState.LEFT3);
                break;
            case LEFT3:
                this.setState(CharacterState.LEFT4);
                break;
            case LEFT4:
                this.setState(CharacterState.LEFT1);
                break;
        }
    }

    /**
     * Method that changes the princess state to face the back when walking up
     */
    public void walkUpStateChange(){
        switch (this.getState()){
            case BACK1:
                this.setState(CharacterState.BACK2);
                break;
            case BACK2:
                this.setState(CharacterState.BACK3);
                break;
            case BACK3:
                this.setState(CharacterState.BACK4);
                break;
            case BACK4:
                this.setState(CharacterState.BACK1);
                break;
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
     * Retrieves the current direction of princess
     * @return princess direction
     */
    public CharacterDirection getDirection(){
        return  this.direction;
    }

    /**
     * Sets the princess direction
     * @param direction direction to set the princess direction with
     */
    public void setDirection(CharacterDirection direction){
        this.direction = direction;
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

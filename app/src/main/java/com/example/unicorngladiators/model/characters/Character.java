package com.example.unicorngladiators.model.characters;

// Generic character class. Can walk in four directions


import com.example.unicorngladiators.model.Facing;
import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;

/**
 * Character class stores and updates 3 information: the position, the state, and the direction.
 */
public class Character {
    public enum CharacterDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    
    private Position pos;
    private CharacterState state;
    private Facing facing;


    /**
     * Character class constructor takes in the initial position of the character and its state.
     *
     * @param pos
     * @param state
     */
    public Character(Position pos, CharacterState state) {
        this.pos = pos;
        this.state = state;
        this.facing = null;
    }

    //p.x and p.y means how many units to move along x and y

    /**
     * Update the position of a character given a Motion object.
     * @param m
     */
    public void walk(Motion m) {
        // update position
        int dx = m.getX();
        int dy = m.getY();
        float ratio;

        Facing dir = this.getFacing();
        if (dx != 0 || dy != 0){
            Position p = new Position(dx, dy);
            this.pos.add(p);

            if (dx == 0) { ratio = 0;}
            else { ratio  = Math.abs(dy/dx); }

            if (ratio >= 1 ) {
                 if (dy <= 0) {

                     if (dir != Facing.UP){
                         this.setState(CharacterState.BACK1);
                         this.setFacing(Facing.UP);}
                         this.walkUpStateChange();}

                 else {
                     if (dir != Facing.DOWN) {
                         this.setState(CharacterState.FRONT1);
                         this.setFacing(Facing.DOWN);}
                         this.walkDownStateChange();
                     }
             } else {
                 if (dx <= 0) {
                     if (dir != Facing.LEFT){
                         this.setState(CharacterState.LEFT1);
                         this.setFacing(Facing.LEFT);}
                         this.walkLeftStateChange();}
                 else {
                     if (dir != Facing.RIGHT){
                     this.setState(CharacterState.RIGHT1);
                     this.setFacing(Facing.RIGHT);}
                     this.walkRightStateChange();
             }
         }
    }}

    /**
     * Updates character frame as it walks down.
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
     * Updates character frame as it walks right.
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
     * Updates character frame as it walks left.
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
     * Updates character frame as it walks up.
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
     * Getter for position state.
     * @return
     */
    public Position getPosition() {
        return this.pos;
    }

    /**
     * Getter for direction state.
     * @return
     */
    public Facing getFacing(){
        return  this.facing;
    }

    /**
     * Setter for direction state.
     * @param facing
     */
    public void setFacing(Facing facing){
        this.facing = facing;
    }

    /**
     * Getter for movement state.
     * @return
     */
    public CharacterState getState(){return this.state;}


    /**
     * Setter for position state.
     * @param pos
     */
    public void setPosition (Position pos) {this.pos = pos;}

    /**
     * Setter for movement state.
     * @param state
     */
    public void setState (CharacterState state) {this.state = state;}


}

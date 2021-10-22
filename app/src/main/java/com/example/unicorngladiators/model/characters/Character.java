package com.example.unicorngladiators.model.characters;

// Generic character class. Can walk in four directions

import androidx.constraintlayout.widget.ConstraintSet;

import com.example.unicorngladiators.model.Direction;
import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;

public class Character {
    private Position pos;
    private CharacterState state;
    private Direction direction;

    public Character(Position pos, CharacterState state) {
        this.pos = pos;
        this.state = state;
        this.direction = null;
    }

    //p.x and p.y means how many units to move along x and y
    public void walk(Motion m) {
        // update position
        int dx = m.getX();
        int dy = m.getY();
        float ratio;
        Direction dir = this.getDirection();
        if (dx != 0 || dy != 0){
            Position p = new Position(dx, dy);
            this.pos.add(p);

            if (dx == 0) { ratio = 0;}
            else { ratio  = Math.abs(dy/dx); }

            if (ratio >= 1 ) {
                 if (dy <= 0) {
                     if (dir != Direction.UP){
                         this.setState(CharacterState.BACK1);
                         this.setDirection(Direction.UP);}
                         this.walkUpStateChange();}

                 else {
                     if (dir != Direction.DOWN) {
                         this.setState(CharacterState.FRONT1);
                         this.setDirection(Direction.DOWN);}
                         this.walkDownStateChange();
                     }
             } else {
                 if (dx <= 0) {
                     if (dir != Direction.LEFT){
                         this.setState(CharacterState.LEFT1);
                         this.setDirection(Direction.LEFT);}
                         this.walkLeftStateChange();}
                 else {
                     if (dir != Direction.RIGHT){
                     this.setState(CharacterState.RIGHT1);
                     this.setDirection(Direction.RIGHT);}
                     this.walkRightStateChange();
             }
         }
    }}


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

    public Position getPosition() {
        return this.pos;
    }

    public Direction getDirection(){
        return  this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public CharacterState getState(){return this.state;}


    public void setPosition (Position pos) {this.pos = pos;}

    public void setState (CharacterState state) {this.state = state;}


}

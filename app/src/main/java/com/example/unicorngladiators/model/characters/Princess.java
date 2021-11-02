package com.example.unicorngladiators.model.characters;

import android.util.Log;

import com.example.unicorngladiators.model.Motion;
import com.example.unicorngladiators.model.Position;

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


    public Princess(Position pos, CharacterState state, int height, int width) {
        super(pos,state);
        this.pos = pos;
        this.state = state;
        this.direction = null;
        this.name = "princess";
        this.top_y = (int) (0.2 * height);
        this.bottom_y = height - (int) (0.2 * height);
        this.left_x = (int) (0.1 * width);
        this.right_x = width - (int) (0.2 * width);
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
        Character.CharacterDirection dir = this.getDirection();
        // walk up
        if (dy >= this.top_y && dx <= this.left_x){
            Log.d("princess walking up",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(0,-40);
            this.pos.add(p);
            this.setState(CharacterState.BACK1);
            this.setDirection(CharacterDirection.UP);
            this.walkUpStateChange();
//            Motion m = new Motion(0,-40);
//            this.walk(m);
        }
        // walk right
        else if (dy <= this.top_y && dx <= this.right_x){
            Log.d("princess walking right",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(40,0);
            this.pos.add(p);
            this.setState(CharacterState.RIGHT1);
            this.setDirection(CharacterDirection.RIGHT);
            this.walkRightStateChange();
//            Motion m = new Motion(10,0);
//            this.walk(m);
        }
        // walk down
        else if (dy <= this.bottom_y && dx >= this.right_x){
            Log.d("princess walking down",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(0,40);
            this.pos.add(p);
            this.setState(CharacterState.FRONT1);
            this.setDirection(CharacterDirection.DOWN);
            this.walkDownStateChange();
//            Motion m = new Motion(0,10);
//            this.walk(m);
        }
        // walk left
        else if (dy >= this.bottom_y && dx >= this.left_x){
            Log.d("princess walking left",Integer.toString(dx)+Integer.toString(dy));
            Position p = new Position(-40,0);
            this.pos.add(p);
            this.setState(CharacterState.LEFT1);
            this.setDirection(CharacterDirection.LEFT);
            this.walkLeftStateChange();
//            Motion m = new Motion(-10,0);
//            this.walk(m);
        }
    }

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

    public CharacterDirection getDirection(){
        return  this.direction;
    }

    public void setDirection(CharacterDirection direction){
        this.direction = direction;
    }

    public CharacterState getState(){return this.state;}


    public void setPosition (Position pos) {this.pos = pos;}

    public void setState (CharacterState state) {this.state = state;}

}

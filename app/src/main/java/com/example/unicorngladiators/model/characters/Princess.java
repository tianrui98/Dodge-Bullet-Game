package com.example.unicorngladiators.model.characters;

//Princess is an NPC who moves along the arena randomly and throws peaches every 15 seconds

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Character;

public class Princess extends Character {

    private final String name;

    public Princess(Position pos, CharacterState state) {
        super(pos,state);
        this.name = "princess";
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
}

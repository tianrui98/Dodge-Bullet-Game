package com.example.unicorngladiators.model.characters;

//Princess is an NPC who moves along the arena randomly and throws peaches every 15 seconds

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.characters.Character;

public class Princess extends Character {
    public Princess(Position pos, CharacterState state) {
        super(pos,state);
    }

    public void wave (){
        if (this.getState() == CharacterState.FRONT) {
            this.setState(CharacterState.SPECIAL);
        }
        else if (this.getState() == CharacterState.SPECIAL) {
            this.setState(CharacterState.FRONT);
        }
    }
}

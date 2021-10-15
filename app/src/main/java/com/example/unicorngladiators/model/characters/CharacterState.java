package com.example.unicorngladiators.model.characters;

//The state is related to where the character is facing
//The SPECIAL state is "wave" for Princess and "hurt" for Unicorn

//FRONT = standing still, facing front
//FRONT1 = moving & facing front
public enum CharacterState {
    FRONT,
    FRONT1,
    FRONT2,
    LEFT,
    LEFT1,
    LEFT2,
    RIGHT,
    RIGHT1,
    RIGHT2,
    BACK,
    BACK1,
    BACK2,
    SPECIAL,
    INVISIBLE,
}
package com.example.unicorngladiators.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.unicorngladiators.R;
import com.example.unicorngladiators.model.characters.CharacterState;

import java.util.HashMap;

//return the sprite we need
public class SpriteSheet {
    private final Bitmap totoBitmap;
    private final Bitmap tutuBitmap;
    private final Bitmap tataBitmap;
    private final Bitmap titiBitmap;
    private final Bitmap princessBitmap;
    private final HashMap<CharacterState, Sprite> unicornSpriteHashMap;

    public SpriteSheet (Resources context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        //TODO: switch. Allow function to take in the character name eg Toto titi tata tutu
        this.totoBitmap = BitmapFactory.decodeResource(context, R.drawable.toto_sprite_sheet, bitmapOptions);
        this.titiBitmap = BitmapFactory.decodeResource(context, R.drawable.titi_sprite_sheet, bitmapOptions);
        this.tataBitmap = BitmapFactory.decodeResource(context, R.drawable.tata_sprite_sheet, bitmapOptions);
        this.tutuBitmap = BitmapFactory.decodeResource(context, R.drawable.tutu_sprite_sheet, bitmapOptions);
        this.princessBitmap = BitmapFactory.decodeResource(context, R.drawable.princess_sprite_sheet, bitmapOptions);
        unicornSpriteHashMap = this.getUnicornSpriteHashmap();
    }

    public Sprite getPlayerSprite(String name, CharacterState state) {
        //TODO: allow retrieving sprites of differtetn unicorns
        return unicornSpriteHashMap.get(state);
    }

    public HashMap<CharacterState, Sprite> getUnicornSpriteHashmap() {
        int gridLength = 48;
        HashMap<CharacterState, Sprite> spriteHashMap= new HashMap<CharacterState, Sprite>();
        spriteHashMap.put(CharacterState.FRONT1, new Sprite(this, new Rect(0 * gridLength, 0 *gridLength, 1 * gridLength, 1 * gridLength)));
        spriteHashMap.put(CharacterState.FRONT2, new Sprite(this, new Rect(1 * gridLength, 0 *gridLength, 2 * gridLength, 1 * gridLength)));
        spriteHashMap.put(CharacterState.FRONT3, new Sprite(this, new Rect(2 * gridLength, 0 *gridLength, 3 * gridLength, 1 * gridLength)));
        spriteHashMap.put(CharacterState.FRONT4, new Sprite(this, new Rect(3 * gridLength, 0 *gridLength, 4 * gridLength, 1 * gridLength)));
        spriteHashMap.put(CharacterState.LEFT1, new Sprite(this, new Rect(0 * gridLength, 1 *gridLength, 1 * gridLength, 2 * gridLength)));
        spriteHashMap.put(CharacterState.LEFT2, new Sprite(this, new Rect(1 * gridLength, 1 *gridLength, 2 * gridLength, 2 * gridLength)));
        spriteHashMap.put(CharacterState.LEFT3, new Sprite(this, new Rect(2 * gridLength, 1 *gridLength, 3 * gridLength, 2 * gridLength)));
        spriteHashMap.put(CharacterState.LEFT4, new Sprite(this, new Rect(3 * gridLength, 1 *gridLength, 4 * gridLength, 2 * gridLength)));
        spriteHashMap.put(CharacterState.RIGHT1, new Sprite(this, new Rect(0 * gridLength, 2 * gridLength, 1 * gridLength, 3 * gridLength)));
        spriteHashMap.put(CharacterState.RIGHT2, new Sprite(this, new Rect(1 * gridLength, 2 * gridLength, 2 * gridLength, 3 * gridLength)));
        spriteHashMap.put(CharacterState.RIGHT3, new Sprite(this, new Rect(2 * gridLength, 2 * gridLength, 3 * gridLength, 3 * gridLength)));
        spriteHashMap.put(CharacterState.RIGHT4, new Sprite(this, new Rect(3 * gridLength, 2 * gridLength, 4 * gridLength, 3 * gridLength)));
        spriteHashMap.put(CharacterState.BACK1, new Sprite(this, new Rect(0 * gridLength, 3 * gridLength, 1 * gridLength, 4 * gridLength)));
        spriteHashMap.put(CharacterState.BACK2, new Sprite(this, new Rect(1 * gridLength, 3 * gridLength, 2 * gridLength, 4 * gridLength)));
        spriteHashMap.put(CharacterState.BACK3, new Sprite(this, new Rect(2 * gridLength, 3 * gridLength, 3 * gridLength, 4 * gridLength)));
        spriteHashMap.put(CharacterState.BACK4, new Sprite(this, new Rect(3 * gridLength, 3 * gridLength, 4 * gridLength, 4 * gridLength)));

        return spriteHashMap;
    };

    public Bitmap getBitmap(String name) {
        Bitmap res = null;
     switch (name) {
         case "toto":
             res = totoBitmap;
             break;
         case "titi":
             res = titiBitmap;
             break;
         case "tata":
             res = tataBitmap;
         case "tutu":
             res = tutuBitmap;
             break;
         case "princess":
             res = princessBitmap;
             break;
     }
     return res;
    }
}

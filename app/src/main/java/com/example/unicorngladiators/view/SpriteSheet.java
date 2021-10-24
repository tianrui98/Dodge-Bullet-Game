package com.example.unicorngladiators.view;

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
    private final Bitmap objectsBitmap;
    private final HashMap<CharacterState, Sprite> characterSpriteHashMap;
    private final HashMap<String, Sprite> projectileSpriteHashMap;


    public SpriteSheet (Resources context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        //TODO: switch. Allow function to take in the character name eg Toto titi tata tutu
        this.totoBitmap = BitmapFactory.decodeResource(context, R.drawable.toto_sprite_sheet, bitmapOptions);
        this.titiBitmap = BitmapFactory.decodeResource(context, R.drawable.titi_sprite_sheet, bitmapOptions);
        this.tataBitmap = BitmapFactory.decodeResource(context, R.drawable.tata_sprite_sheet, bitmapOptions);
        this.tutuBitmap = BitmapFactory.decodeResource(context, R.drawable.tutu_sprite_sheet, bitmapOptions);
        this.princessBitmap = BitmapFactory.decodeResource(context, R.drawable.princess_sprite_sheet, bitmapOptions);
        this.characterSpriteHashMap = this.getCharacterSpriteHashmap();
        this.objectsBitmap = BitmapFactory.decodeResource(context, R.drawable.objects_sprite_sheet, bitmapOptions);
        this.projectileSpriteHashMap = this.getObjectsSpriteHashmap();
    }

    private HashMap<String, Sprite> getObjectsSpriteHashmap() {
        int gridLength = 48;
        HashMap<String, Sprite> spriteHashMap= new HashMap<String, Sprite>();
        spriteHashMap.put("peach", new Sprite(this, new Rect(0 * gridLength, 0 *gridLength, 1 * gridLength, 1 * gridLength)));
        spriteHashMap.put("bullet", new Sprite(this, new Rect(0 * gridLength, 1 *gridLength, 1 * gridLength, 2 * gridLength)));
        return spriteHashMap;
    }

    public Sprite getProjectileSprite(String name) {
        return this.projectileSpriteHashMap.get(name);

    }

    public Sprite getPlayerSprite(CharacterState state) {

        return this.characterSpriteHashMap.get(state);

    }

    public HashMap<CharacterState, Sprite> getCharacterSpriteHashmap() {
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
        spriteHashMap.put(CharacterState.SPECIAL1, new Sprite(this, new Rect(0 * gridLength, 4 * gridLength, 1 * gridLength, 5 * gridLength)));
        spriteHashMap.put(CharacterState.SPECIAL2, new Sprite(this, new Rect(1 * gridLength, 4 * gridLength, 2 * gridLength, 5 * gridLength)));
        spriteHashMap.put(CharacterState.SPECIAL3, new Sprite(this, new Rect(2 * gridLength, 4 * gridLength, 3 * gridLength, 5 * gridLength)));
        spriteHashMap.put(CharacterState.SPECIAL4, new Sprite(this, new Rect(3 * gridLength, 4 * gridLength, 4 * gridLength, 5 * gridLength)));

        return spriteHashMap;
    };

    public Bitmap getBitmap(String name) {
        Bitmap res = null;
         switch (name) {
             case "toto":
                 res = this.totoBitmap;
                 break;
             case "titi":
                 res = this.titiBitmap;
                 break;
             case "tata":
                 res = this.tataBitmap;
                 break;
             case "tutu":
                 res = this.tutuBitmap;
                 break;
             case "princess":
                 res = this.princessBitmap;
                 break;
             case "objects":
                 res = this.objectsBitmap;
                 break;
         }
     return res;
    }
}

package com.example.unicorngladiators.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.unicorngladiators.R;

//return the sprite we need
public class SpriteSheet {
    private Bitmap bitmap;
    Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
    public SpriteSheet (Resources context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        //TODO: add if else statements. Allow function to take in the character name eg Toto titi tata tutu
        bitmap = BitmapFactory.decodeResource(context, R.drawable.unicorn_sprite_sheet, bitmapOptions);
    }

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(0,0,50,50));

    }

    public Bitmap getBitmap() {
        return bitmapScaled;
    }
}

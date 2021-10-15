package com.example.unicorngladiators.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.unicorngladiators.model.Position;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, Position pos) {
        //add offset so that the image's center is draw at x and y
        int x = pos.getX() ;
        int y = pos.getY() ;
        canvas.drawBitmap(spriteSheet.getBitmap(),
                rect,
                //adjust size here
                new Rect(x, y, x + 150, y+150),
                null);
        Log.d("Sprite", "draw unicorn sprite");
    }

    public int getHeight() {
        return rect.height();
    }

    public int getWidth() {
        return rect.width();
    }
}

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

    public void drawCharacter(Canvas canvas, Position pos, String name) {
        //add offset so that the image's center is draw at x and y
        int x = pos.getX() ;
        int y = pos.getY() ;
        canvas.drawBitmap(spriteSheet.getBitmap(name),
                rect,
                //adjust size here
                new Rect(x, y, x + 150, y+150),
                null);
    }

    public int getHeight() {
        return rect.height();
    }

    public int getWidth() {
        return rect.width();
    }
}

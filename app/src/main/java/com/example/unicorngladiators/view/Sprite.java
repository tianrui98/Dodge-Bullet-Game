package com.example.unicorngladiators.view;

import android.graphics.Canvas;
import android.graphics.Rect;

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
        int x = pos.getX() - 25;
        int y = pos.getY() - 25;
        canvas.drawBitmap(spriteSheet.getBitmap(),
                rect,
                new Rect(x, y, x + 50, y + 50),
                null);
    }
}

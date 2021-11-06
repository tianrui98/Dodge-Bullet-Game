package com.example.unicorngladiators.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.unicorngladiators.model.Position;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public Bitmap rotateBitmap(Bitmap bitmap, int degrees){
        Matrix matrix = new Matrix();
//        matrix.postRotate(rotation);
        matrix.preRotate(degrees);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public void drawSprite(Canvas canvas, Position pos, String name, int rotation) {
        //add offset so that the image's center is draw at x and y
        int x = pos.getX() ;
        int y = pos.getY() ;
        Bitmap bitmap = this.rotateBitmap(spriteSheet.getBitmap(name), rotation);
        canvas.drawBitmap(bitmap,
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

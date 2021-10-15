package com.example.unicorngladiators.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.unicorngladiators.R;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.model.characters.Character;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Princess;
import com.example.unicorngladiators.model.characters.Unicorn;

import java.util.*;

public class Renderer implements SurfaceHolder.Callback, Universe.Callback {
    private final static String TAG = "RendererObject";
    private final Bitmap princess_stand_bitmap;
    private final Bitmap princess_wave_bitmap;
    private SurfaceHolder holder;
    private Universe universe;
    private SpriteSheet unicorn_sprite_sheet;

    public Renderer(Universe universe, SurfaceHolder holder, Resources context) {
        this.universe = universe;
        this.universe.setCallBack(this);
        Bitmap princess_stand_raw_bitmap = BitmapFactory.decodeResource(context, R.drawable.peach_stand);
        if (princess_stand_raw_bitmap == null){
            Log.d("Renderer", "Princess is null");
        }
        this.princess_stand_bitmap = Bitmap.createScaledBitmap(princess_stand_raw_bitmap, princess_stand_raw_bitmap.getWidth() / 15, princess_stand_raw_bitmap.getHeight() / 15, true);
        Bitmap princess_wave_raw_bitmap = BitmapFactory.decodeResource(context, R.drawable.peach_wave);
        this.princess_wave_bitmap = Bitmap.createScaledBitmap(princess_wave_raw_bitmap, princess_wave_raw_bitmap.getWidth() / 15, princess_wave_raw_bitmap.getHeight() / 15, true);

        this.unicorn_sprite_sheet = new SpriteSheet(context);
    }


    private void drawPrincess(Canvas canvas) {
        CharacterState state = universe.getPrincess().getState();
        Position pos = universe.getPrincess().getPosition();

        //According to princess's state draw different bitmap
        if (state == CharacterState.FRONT1) {
            canvas.drawBitmap(this.princess_stand_bitmap, pos.getX(), pos.getY(), null);
        }
        else if (state == CharacterState.SPECIAL1){
            canvas.drawBitmap(this.princess_wave_bitmap, pos.getX(), pos.getY(), null);
        }
    }

    private void drawUnicorns(Canvas canvas) {


        List<Unicorn> players = this.universe.getPlayers();

        for (Unicorn player : players) {
            Sprite unicorn_sprite = this.unicorn_sprite_sheet.getPlayerSprite("toto", player.getState());
            //offset is to help the draw function to draw the center of the picture
            int h_offset = unicorn_sprite.getHeight() / 2;
            int w_offset = unicorn_sprite.getWidth() / 2;
            Position posAdjusted = new Position(player.getPosition().getX() + w_offset, player.getPosition().getX() + h_offset);
            unicorn_sprite.drawCharacter(canvas, posAdjusted, player.getName());
            Log.d("Renderer", "draw unicorn " + player.getName());
        }

    };

    private void drawSurfaceView() {
        if (universe != null && holder != null) {
            Canvas canvas = holder.lockCanvas();

            //set background white
            canvas.drawARGB(255, 255, 255, 255);
            //TODO draw more objects
            this.drawPrincess(canvas);
            this.drawUnicorns(canvas);
            holder.unlockCanvasAndPost(canvas);
        } else {
            Log.e(TAG, "error in drawSurfaceView");
        }
    }

    //manage callback
    @Override
    public void universeChanged(Universe universe) {
        this.drawSurfaceView();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "start surfaceCreated");
        this.holder = surfaceHolder;
        drawSurfaceView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "start surfaceChanged");
        drawSurfaceView();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "start surfaceDestroyed");
        this.holder = null;
    }

}

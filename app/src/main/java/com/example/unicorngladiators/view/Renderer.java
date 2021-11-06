package com.example.unicorngladiators.view;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.unicorngladiators.R;
import com.example.unicorngladiators.model.Joystick;
import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.model.characters.CharacterState;
import com.example.unicorngladiators.model.characters.Unicorn;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.example.unicorngladiators.model.projectiles.Peach;
import com.example.unicorngladiators.model.projectiles.Projectile;

import java.util.*;

public class Renderer implements SurfaceHolder.Callback, Universe.Callback {
    private final static String TAG = "RendererObject";
    private final Paint paint;
    private SurfaceHolder holder;
    private Universe universe;
    private SpriteSheet sprite_sheet;

    public Renderer(Universe universe, SurfaceHolder holder, Resources context) {
        this.universe = universe;
        this.universe.setCallBack(this);
        this.sprite_sheet = new SpriteSheet(context);
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        Bitmap tiles = BitmapFactory.decodeResource(context.newTheme().getResources(), R.drawable.sand);
        this.paint.setShader(new BitmapShader(tiles, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }


    private void drawPrincess(Canvas canvas) {
        CharacterState state = this.universe.getPrincess().getState();
        Position pos = this.universe.getPrincess().getPosition();

        //According to princess's state draw different bitmap
        Sprite sprite = this.sprite_sheet.getPlayerSprite(state);
        //offset is to help the draw function to draw the center of the picture
        int h_offset = sprite.getHeight() / 2;
        int w_offset = sprite.getWidth() / 2;
        Position posAdjusted = new Position(pos.getX() + w_offset, pos.getY() + h_offset);
        sprite.drawSprite(canvas, posAdjusted, "princess", 0);
    }

    private void drawUnicorns(Canvas canvas) {
        Collection<Unicorn> players = this.universe.getPlayersHashMap().values();
        System.out.println("drawing unicorns: "+ this.universe.getPlayersHashMap());
        for (Unicorn player : players) {
            Sprite unicorn_sprite = this.sprite_sheet.getPlayerSprite(player.getState());
            //offset is to help the draw function to draw the center of the picture
            int h_offset = unicorn_sprite.getHeight() / 2;
            int w_offset = unicorn_sprite.getWidth() / 2;
            System.out.println("drawing unicorn: "+ player.getName() + player.getPosition());
            Position posAdjusted = new Position(player.getPosition().getX() + w_offset, player.getPosition().getY() + h_offset);
            unicorn_sprite.drawSprite(canvas, posAdjusted, player.getName(), 0);
        }
    };

    private void drawJoystick(Canvas canvas){
        Joystick js = this.universe.getJoystick();
        Position innerPos = js.getInnerCenterPosition();
        Position outerPos = js.getOuterCenterPosition();
        Paint colors = new Paint();
        colors.setARGB(255,50,50,50);
        canvas.drawCircle(innerPos.getX(), innerPos.getY(), js.getInnerRadius(), colors); // base of joystick
        colors.setARGB(100,0,0,255);
        canvas.drawCircle(outerPos.getX(),outerPos.getY(),js.getOuterRadius(),colors); // hat of joystick
    }

    private void drawBullets(Canvas canvas) {
//        TODO: add rotation as an argument
        List<Bullet> bullets = this.universe.getBullets();
        for (Bullet bullet : bullets) {
            //System.out.println("drawing Bullet ..."+bullet.toString());
            Position pos = bullet.getPosition();
            Sprite sprite = this.sprite_sheet.getProjectileSprite("bullet");
            //offset is to help the draw function to draw the center of the picture
            int h_offset = sprite.getHeight() / 2;
            int w_offset = sprite.getWidth() / 2;
            Position posAdjusted = new Position(pos.getX() + w_offset, pos.getY() + h_offset);
            //Log.d("Renderer","position: X "+pos.getX()+" Y: "+pos.getY());
            //Log.d("Renderer","position: "+pos+" Adjusted: "+posAdjusted);
            sprite.drawSprite(canvas, posAdjusted, "object", 0 );
        }
    }

    private void drawPeaches(Canvas canvas) {
        List<Peach> peaches = this.universe.getPeaches();
        for (Peach peach : peaches) {
            Position pos = peach.getPosition();
            //According to princess's state draw different bitmap
            Sprite sprite = this.sprite_sheet.getProjectileSprite("peach");
            //offset is to help the draw function to draw the center of the picture
            int h_offset = sprite.getHeight() / 2;
            int w_offset = sprite.getWidth() / 2;
            Position posAdjusted = new Position(pos.getX() + w_offset, pos.getY() + h_offset);
            sprite.drawSprite(canvas, posAdjusted, "object", 0);
        }
    }

    //Todo delete this function. It's only for debugging
    private void drawLives(Canvas canvas) {
        HashMap<String, Integer> playersLives = this.universe.getPlayersLives();
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        int initialY = 50;
        for (String playerName : playersLives.keySet()) {
            int lives = playersLives.get(playerName);
            String ss = String.format("Player: %s Lives : %d",playerName,lives);
            canvas.drawText(ss,100,initialY,textPaint);
            initialY += 50;
        };
    }

    /**
     * Draw the character's icon on scoreboard
     * @param canvas
     */
    private void drawScoreboard(Canvas canvas) {
        Collection<Unicorn> players = this.universe.getPlayersHashMap().values();
        int frameX = 10;
        int frameY = 1600;

        for (Unicorn player : players) {
            //draw the icon
            String sprite_name;
            if (player.getLives() <= 0) {sprite_name = player.getName()+ "_mort";}
            else{sprite_name = player.getName()+ "_frame"; }
            Sprite unicorn_frame_sprite = this.sprite_sheet.getObjectsSpriteHashmap().get(sprite_name);
            //offset is to help the draw function to draw the center of the picture
            Position posAdjusted = new Position(frameX, frameY);
            unicorn_frame_sprite.drawSprite(canvas, posAdjusted, "object", 0);

            //draw name
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(50);
            canvas.drawText(player.getName(),frameX + 40 ,frameY + 20, textPaint);

            //draw hearts
            Sprite heart_sprite = this.sprite_sheet.getObjectsSpriteHashmap().get("heart");
            int heartX = frameX + 65;
            int heartY = frameY + 10;
            for (int i = 0; i < player.getLives() && i < 3; i ++ ) {
            Position heartPos = new Position(heartX,heartY);
            heart_sprite.drawSprite(canvas, heartPos,"object", 0 );
            heartX += 55;
            }

            //update frame position
            frameX += 250;
        }
    }

    private void drawSurfaceView() {
        if (universe != null && holder != null) {
            Canvas canvas = holder.lockCanvas();
            //set background white
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawRGB(255,255,255);

            this.drawPrincess(canvas);
            this.drawUnicorns(canvas);
            this.drawJoystick(canvas);
            this.drawBullets(canvas);
            this.drawPeaches(canvas);
            this.drawScoreboard(canvas);

            holder.unlockCanvasAndPost(canvas);
        } else {
            System.out.println("universe:" + universe + " holder "+holder);
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

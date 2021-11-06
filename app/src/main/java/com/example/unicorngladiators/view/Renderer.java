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
            if (player.getLives() > 0) {
                Sprite unicorn_sprite = this.sprite_sheet.getPlayerSprite(player.getState());
                //offset is to help the draw function to draw the center of the picture
                int h_offset = unicorn_sprite.getHeight() / 2;
                int w_offset = unicorn_sprite.getWidth() / 2;
                Position posAdjusted = new Position(player.getPosition().getX() + w_offset, player.getPosition().getY() + h_offset);
                unicorn_sprite.drawSprite(canvas, posAdjusted, player.getName(), 0);
            }
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

    private int rotateProjectile(int x, int y) {
        //angle
        double offsetX;
        if (x == 0) { offsetX = (double) x + 0.001;}
        else {offsetX = (double) x + 0.00;}
        double offsetY = (double) y + 0.00 ;

        double basicAngle = Math.atan(Math.abs(offsetY/offsetX)) * 180 / Math.PI;
        double rotation;
        // second quadrant
        if (offsetX < 0 && offsetY < 0) {
            rotation = 180 + basicAngle;
        // third quadrant
        } else if (offsetX < 0) {
            rotation = 180 - basicAngle;
        // fourth quadrant
        } else if (offsetX > 0 && offsetY >= 0){
            rotation = basicAngle;
        }
        // first quadrant
        else{
            rotation = 360 - basicAngle;
        }

        int degree = (int) rotation;
        return degree;
    }
    private void drawBullets(Canvas canvas) {
//        TODO: add rotation as an argument
        List<Bullet> bullets = this.universe.getBullets();
        for (Bullet bullet : bullets) {
            if (!bullet.getIsUsed()) {
            Position pos = bullet.getPosition();
            Sprite sprite = this.sprite_sheet.getProjectileSprite();
            //offset is to help the draw function to draw the center of the picture
            int h_offset = sprite.getHeight() / 2;
            int w_offset = sprite.getWidth() / 2;
            Position posAdjusted = new Position(pos.getX() + w_offset, pos.getY() + h_offset);
            int degree = this.rotateProjectile(bullet.getDirection().getOffset().getX(), bullet.getDirection().getOffset().getY());
            sprite.drawSprite(canvas, posAdjusted, "bullet", degree);
        }}
    }

    private void drawPeaches(Canvas canvas) {
        List<Peach> peaches = this.universe.getPeaches();
        for (Peach peach : peaches) {
            if (!peach.getIsUsed()) {
            Position pos = peach.getPosition();
            //According to princess's state draw different bitmap
            Sprite sprite = this.sprite_sheet.getProjectileSprite();
            //offset is to help the draw function to draw the center of the picture
            int h_offset = sprite.getHeight() / 2;
            int w_offset = sprite.getWidth() / 2;
            Position posAdjusted = new Position(pos.getX() + w_offset, pos.getY() + h_offset);
            int degree = this.rotateProjectile(peach.getDirection().getOffset().getX(), peach.getDirection().getOffset().getY());
            sprite.drawSprite(canvas, posAdjusted, "peach", degree);
        }}
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
        HashMap<String, Integer> playersLives = this.universe.getPlayersLives();
        int frameX = 10;
        int frameY = 1600;

        for (String playerName: playersLives.keySet()) {
            int lives = playersLives.get(playerName);
            //draw the icon
            String sprite_name;
            Log.d(TAG, "Playername: " + playerName);
            if (lives <= 0) {sprite_name = playerName+ "_mort";}
            else{sprite_name = playerName+ "_frame"; }
            Sprite unicorn_frame_sprite = this.sprite_sheet.getObjectsSprite(sprite_name);
            //offset is to help the draw function to draw the center of the picture
            Position posAdjusted = new Position(frameX, frameY);
            unicorn_frame_sprite.drawSprite(canvas, posAdjusted, "object", 0);

            //draw name
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(50);
            canvas.drawText(playerName,frameX + 40 ,frameY + 20, textPaint);

            //draw hearts
            Sprite heart_sprite = this.sprite_sheet.getObjectsSprite("heart");
            int heartX = frameX + 65;
            int heartY = frameY + 10;
            for (int i = 0; i < lives; i ++ ) {
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

            if (!this.universe.snapped()) {
                this.drawUnicorns(canvas);
                this.drawJoystick(canvas);
            }

            this.drawBullets(canvas);
            this.drawPeaches(canvas);
            this.drawScoreboard(canvas);
            this.drawLives(canvas);

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

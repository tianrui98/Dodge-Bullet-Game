package com.example.unicorngladiators.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.unicorngladiators.R;

public class DrawPrincess extends SurfaceView {

    Thread thread = null;
    boolean CanDraw = false;
    SurfaceHolder surfaceHolder;
    Bitmap princess_stand;
    Bitmap princess_stand_scaled;
    Bitmap princess_wave;
    Bitmap princess_wave_scaled;

    public DrawPrincess(Context context) {
        super(context);
        princess_stand = BitmapFactory.decodeResource(getResources(), R.drawable.peach_stand);
        princess_stand_scaled = Bitmap.createScaledBitmap(princess_stand, princess_stand.getWidth() / 15, princess_stand.getHeight() / 15, true);
        princess_wave = BitmapFactory.decodeResource(getResources(), R.drawable.peach_wave);
        princess_wave_scaled = Bitmap.createScaledBitmap(princess_wave, princess_wave.getWidth() / 15, princess_wave.getHeight() / 15, true);

    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(princess_stand_scaled, 50, 50, null);


    }

    public void run() throws InterruptedException {
        while(CanDraw){
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(princess_stand_scaled, 50, 50, null);
            Thread.sleep(500);
            canvas.drawBitmap(princess_wave_scaled, 50, 50, null);
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    public void pause(){
        CanDraw = false;

        while(true){
            try{
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thread =null;

    }
}
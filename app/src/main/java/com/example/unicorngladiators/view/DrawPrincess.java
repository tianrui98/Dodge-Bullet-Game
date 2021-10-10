package com.example.unicorngladiators.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import com.example.unicorngladiators.R;

public class DrawPrincess extends SurfaceView {

    public DrawPrincess(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Bitmap princess_stand;
        princess_stand = BitmapFactory.decodeResource(getResources(), R.drawable.peach_stand);
        canvas.drawBitmap(princess_stand, 10, 50, null);

    }
}
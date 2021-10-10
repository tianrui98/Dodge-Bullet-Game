package com.example.unicorngladiators;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity  {
    private static String TAG = "StartGame";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.princess);

        Canvas canvas = new Canvas();
        canvas.drawARGB(255, 255, 255, 255);
//        Bitmap princess_stand = BitmapFactory.decodeResource(getResources(), R.mipmap.peach_wave_foreground);
//        canvas.drawBitmap(princess_stand, 10, 80, null);
//
//        Log.i(TAG, "Princess is watching");
    }
}
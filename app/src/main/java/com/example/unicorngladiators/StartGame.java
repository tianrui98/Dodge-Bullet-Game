package com.example.unicorngladiators;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.view.DrawPrincess;

public class StartGame extends AppCompatActivity  {
    private static String TAG = "StartGame";

    DrawPrincess drawPrincess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.princess);

        drawPrincess = new DrawPrincess(this);
        drawPrincess.setBackgroundColor(Color.WHITE);
        setContentView(drawPrincess);


    }
}


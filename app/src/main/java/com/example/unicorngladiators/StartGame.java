package com.example.unicorngladiators;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity  {
    private static String TAG = "StartGame";

    DrawPrincess drawPrincess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawPrincess = new DrawPrincess(this);
        drawPrincess.setBackgroundColor(Color.WHITE);
        setContentView(drawPrincess);


    }
}


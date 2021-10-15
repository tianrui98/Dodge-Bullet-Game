package com.example.unicorngladiators;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.view.Renderer;

public class GameActivity extends AppCompatActivity  {
    private static String TAG = "GameActivity";
    SurfaceView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create background
        setContentView(R.layout.game_activity);
        sv = findViewById(R.id.surfaceView);
        GameController gc = new GameController(sv, getResources());
        gc.start();
        Log.d(TAG, "onCreate finished.");
    }
}
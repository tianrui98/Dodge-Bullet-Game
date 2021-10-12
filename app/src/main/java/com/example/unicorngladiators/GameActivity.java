package com.example.unicorngladiators;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.model.Universe;
import com.example.unicorngladiators.view.Renderer;

public class GameActivity extends AppCompatActivity  {
    private static String TAG = "StartGame";
    Universe universe;
    SurfaceView sv;
    SurfaceHolder holder;
    Resources context;
    Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create background
        setContentView(R.layout.game_activity);

        //instantiate universe, princess, unicorns
        this.universe = new Universe(null);
        this.renderer = new Renderer(universe, holder, context);

        this.sv = findViewById(R.id.surfaceView);
        this.sv.getHolder().addCallback(this.renderer);

        //manage click and touch events


    }
}
package com.example.unicorngladiators;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.network.FirebaseRoomHandler;
import com.example.unicorngladiators.network.Room;

public class GameActivity extends AppCompatActivity  {
    private static String TAG = "GameActivity";
    SurfaceView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create background
        setContentView(R.layout.game_activity);
        sv = findViewById(R.id.surfaceView);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Room room = (Room) getIntent().getSerializableExtra("Room");
        FirebaseRoomHandler fh = (FirebaseRoomHandler) getIntent().getSerializableExtra("FirebaseHandler");

        if (room == null) {Log.d(TAG, "room is null");}
        if (fh == null) {Log.d(TAG, "fh is null");}

        GameController gc = new GameController(sv, getResources(),height,width, room);
        gc.start();
        Log.d(TAG, "onCreate finished.");
    }
}
package com.example.unicorngladiators;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.network.FirebaseRoomHandler;
import com.example.unicorngladiators.network.Room;

public class GameActivity extends Activity {
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
        String puid = (String) getIntent().getSerializableExtra("PlayerUID");
        //FirebaseRoomHandler fh = (FirebaseRoomHandler) getIntent().getExtras().getParcelable("FirebaseHandler");

        if (room == null) {Log.d(TAG, "room is null");}
        //if (fh == null) {Log.d(TAG, "fh is null");}
        //System.out.println("in game activity: "+room.getPlayer_ids());
        //System.out.println("bullets: "+room.getBullets().size()+"first one: "+room.getBullets().get(0));
        GameController gc = new GameController(sv, getResources(), height, width, room, puid);
        gc.start();
        Log.d(TAG, "onCreate finished.");
    }
}
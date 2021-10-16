package com.example.unicorngladiators;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.network.FirebaseHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGameBtn;
    private Button quitGameBtn;
    private Button settingsBtn;


    private SurfaceHolder holder;

    FirebaseHandler fh = new FirebaseHandler();
    int x = 1, y =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startGameBtn = (Button) findViewById(R.id.startGameBtn);
        this.quitGameBtn = (Button) findViewById(R.id.quitGameBtn);
        this.settingsBtn = (Button) findViewById(R.id.settingsBtn);

        startGameBtn.setOnClickListener(this);
        quitGameBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startGameBtn:
                //start GameActivity when user clicks this button
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);

                fh.updateMove(x+", "+ y);
                x++; y++;
                Toast.makeText(this, "Start Game Btn Clicked", Toast.LENGTH_SHORT).show();

                break;
            case R.id.settingsBtn:
                Toast.makeText(this, "Settings Btn Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.quitGameBtn:
                Toast.makeText(this, "Quit Game Btn Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}


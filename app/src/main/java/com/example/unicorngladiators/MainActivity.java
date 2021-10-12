package com.example.unicorngladiators;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startGameBtn;
    private Button quitGameBtn;
    private Button settingsBtn;

    private SurfaceHolder holder;
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
//                Toast.makeText(this, "Start Game Btn Clicked", Toast.LENGTH_SHORT).show();

                //start another activity when user clicks this button
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
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


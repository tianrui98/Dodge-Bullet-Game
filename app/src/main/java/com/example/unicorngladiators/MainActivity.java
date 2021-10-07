package com.example.unicorngladiators;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private  final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");


        Log.d(TAG, "onCreate Finished");

    }

    @Override
    public void onClick(View view) {

    }
}


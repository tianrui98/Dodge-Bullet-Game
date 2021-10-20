package com.example.unicorngladiators;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.network.FirebaseHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirebaseRequest extends AppCompatActivity implements View.OnClickListener{
    private TextView contentView;
    private Button startGameBtn;
    private int x, y;

    FirebaseHandler fh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_req);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.startGameBtn = (Button) findViewById(R.id.viewContent);
        startGameBtn.setOnClickListener(this);

        this.contentView = (TextView) findViewById(R.id.textView);
        fh = new FirebaseHandler(contentView);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            default:
                fh.updateMove(x+", "+ y);
                x++; y++;
                break;
        }
    }
}

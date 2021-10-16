package com.example.unicorngladiators;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.network.FirebaseHandler;
import com.example.unicorngladiators.network.Room;


enum state {
    READY,
    WAITING
}

public class WaitingRoom extends AppCompatActivity implements View.OnClickListener{
    private state curr_state;
    private Button startGameBtn;
    private FirebaseHandler fh;
    private Room room;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.curr_state = state.WAITING;
        setContentView(R.layout.activity_waiting_room2);

        this.startGameBtn = (Button) findViewById(R.id.playerStatus);
        startGameBtn.setOnClickListener(this);
        startGameBtn.setText(this.curr_state.toString());

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        fh = new FirebaseHandler(width, height);
        room = fh.getRoom();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            default:
                switch (this.curr_state) {
                    case WAITING:
                        this.curr_state = state.READY;
                        break;
                    default:
                        this.curr_state = state.WAITING;
                        break;
                }

                startGameBtn.setText(this.curr_state.toString());
                break;
        }
    }


}
package com.example.unicorngladiators;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


enum state {
    READY,
    WAITING
}

public class waitingRoom extends AppCompatActivity {
    private state curr_state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.curr_state = state.WAITING;
        setContentView(R.layout.activity_waiting_room2);
        Button startingButton = (Button)findViewById(R.id.playerStatus);
        startingButton.setText(this.curr_state.toString());
    }

    public void onClick(View v) {
        switch(this.curr_state){
            case WAITING:
                this.curr_state = state.READY;
                break;
            default:
                this.curr_state = state.WAITING;
                break;
        }

        Button startingButton = (Button)findViewById(R.id.playerStatus);
        startingButton.setText(this.curr_state.toString());
    }


}
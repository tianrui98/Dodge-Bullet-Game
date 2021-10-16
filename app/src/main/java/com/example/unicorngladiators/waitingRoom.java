package com.example.unicorngladiators;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


enum state {
    READY,
    WAITING
}

public class waitingRoom extends AppCompatActivity implements View.OnClickListener{
    private state curr_state;
    private Button startGameBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.curr_state = state.WAITING;
        setContentView(R.layout.activity_waiting_room2);

        this.startGameBtn = (Button) findViewById(R.id.playerStatus);
        startGameBtn.setOnClickListener(this);
        startGameBtn.setText(this.curr_state.toString());

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
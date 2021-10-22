package com.example.unicorngladiators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unicorngladiators.network.FirebaseRoomHandler;
import com.example.unicorngladiators.network.Room;

public class WaitingRoomActivity extends Activity implements View.OnClickListener{
    private state curr_state;
    private Button readyStateBtn, startGameBtn;
    private FirebaseRoomHandler fh;
    private TextView playerCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.curr_state = state.ENTER;
        setContentView(R.layout.activity_waiting_room2);

        this.readyStateBtn = (Button) findViewById(R.id.playerStatus);
        readyStateBtn.setOnClickListener(this);
        readyStateBtn.setText(this.curr_state.toString());

        this.startGameBtn = (Button) findViewById(R.id.startGameHost);
        startGameBtn.setOnClickListener(this);

        this.playerCount = (TextView) findViewById(R.id.textViewPlayerCount);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        fh = new FirebaseRoomHandler(width, height, playerCount, startGameBtn);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startGameHost:
                fh.startGame();
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("FirebaseHandler", fh);
                Room room = fh.getRoom();
                if (room == null) {
                    Log.d("Waiting Room", "room in firebasehandler is null at starting");
                } else {
                    Log.d("Waiting Room", "room in firebasehandler is not null at starting");
                }
                intent.putExtra("Room", room);
                intent.putExtra("PlayerUID", fh.getPuid());
                System.out.println("in waiting room: "+room.getPlayer_ids() + "<-- player ids; bullets: "+room.getBullets().size()+"first one: "+room.getBullets().get(0));
                startActivity(intent);
                break;

            default:
                switch (this.curr_state) {
                     case ENTER:
                        this.curr_state = state.LEAVE;
                        fh.joinRoom();
                         if (fh.getRoom() == null) {
                             Log.d("Waiting Room", "room in firebasehandler is null, after joinRoom is called");
                         }
                        break;
                    default:
                        this.curr_state = state.ENTER;
                        if (fh.getRoom() == null) {
                            Log.d("Waiting Room", "room in firebasehandler is null before leaving");
                        } else {
                            Log.d("Waiting Room", "room in firebasehandler is not null before leaving");
                        }
                        fh.leaveRoom();
                        break;
                }

                readyStateBtn.setText(this.curr_state.toString());
                break;
        }
    }

    enum state {
        ENTER,
        LEAVE
    }
}
package com.example.unicorngladiators;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.unicorngladiators.network.Room;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EndGameActivity extends Activity implements View.OnClickListener{
    private Room room;
    private String puid;
    private Button playAgain;
    private TextView leaderboard;
    private String TAG = "EndGameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Room room = (Room) getIntent().getSerializableExtra("Room");
        String puid = (String) getIntent().getSerializableExtra("PlayerUID");
        this.room = room;
        this.puid = puid;

        this.playAgain = (Button) findViewById(R.id.playAgain);
        playAgain.setOnClickListener(EndGameActivity.this);

        this.leaderboard = (TextView) findViewById(R.id.leaderBoardText);
        Map<String, Integer> sortedScores = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sortedScores = this.room.getPlayer_scores().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));
        }

        String leaderboardText = "";
        int i = this.room.getNum_players();
        for(String player : sortedScores.keySet()) {
            String tmp = i + ". ";
            if (player.equals(this.puid)) { tmp += this.room.getPlayerName(player) +" (You)" + ": "; }
            else {tmp += this.room.getPlayerName(player) + ": ";}
            tmp += sortedScores.get(player) + "\n";
            i--;
            leaderboardText = tmp + leaderboardText;
        }

        this.leaderboard.setText(leaderboardText);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playAgain:
                Intent intent = new Intent(this, WaitingRoomActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
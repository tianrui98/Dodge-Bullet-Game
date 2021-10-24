package com.example.unicorngladiators.network;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.unicorngladiators.model.Position;
import com.example.unicorngladiators.model.projectiles.Bullet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebasePlayerHandler {
    private FirebaseDatabase database;
    private DatabaseReference rooms, players, player;
    private String puid, roomId;
    private Room room = null;
    private boolean inRoom, started = false;

    public FirebasePlayerHandler(String puid){
        this.puid = puid;
        this.rooms = database.getReference("rooms");
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        players = database.getReference("players");
        player = database.getReference("players/"+this.puid);
        addMovesEventListener(players);
        System.out.println("initing done");
    }

    public void readRoomStates(){
        rooms.child(roomId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    HashMap<String, Object> states = (HashMap<String, Object>) task.getResult().getValue();
                    System.out.println("states:"+states);
                    Boolean start = new Boolean(false);
                    start = (Boolean) states.get("start");
                    Boolean ended = new Boolean(false);
                    ended = (Boolean) states.get("ended");
                    room.setPlayerIds((HashMap<String, String>)states.get("player_ids"));
                    room.setStart((boolean) start);
                    room.setEnd((boolean) ended);
                    room.setBullets((List<String>) states.get("bullets"));
                }
            }
        });
    }

    public void leaveRoom() {
        if (!inRoom) return;
        inRoom = false;
        this.room.removePlayer(this.puid);
        this.updateMove("-1,-1");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.roomId+"/num_players", this.room.getNum_players());
        childUpdates.put(this.roomId+"/player_ids", this.room.getPlayer_ids());
        rooms.updateChildren(childUpdates);

        if (this.room.getNum_players() == 0)
            endGame();

    }

    public void endGame() {
        if (this.room == null) return;
        players.setValue("");
        rooms.setValue("");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put("rooms_listing", "");
        rooms.updateChildren(childUpdates);
        room = null;
    }

    public String getPuid(){ return this.puid; }

    public void updateMove(String newMove){
        System.out.println("adding move...");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(puid, newMove);
        players.updateChildren(childUpdates);
        System.out.println("done");
        return;
    }

    public Room getRoom(){ return this.room; }

    private void addMovesEventListener(DatabaseReference playersRef) {
        ValueEventListener movesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    HashMap<String, String> val = (HashMap<String, String>) dataSnapshot.getValue();
                    System.out.println(val);

                } catch (Exception e){

                }
                //System.out.println(dataSnapshot.getValue().getClass());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };
        playersRef.addValueEventListener(movesListener);
    }


}

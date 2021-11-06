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

/**
 * The FirebasePlayerHandler class is similar to the FirebaseRoomHandler but is specifically built
 * for the Universe class.  It reads the room states from the Firebase Real-time Database (RTDB) and
 * allows the instance to be update the move and the score for itself and update its local
 * room state (along with other players state within the room) from the RTDB.
 */
public class FirebasePlayerHandler {
    private FirebaseDatabase database;
    private DatabaseReference rooms, players, player;
    private String puid, roomId;
    private Room room = null;
    private boolean inRoom, started = false;

    /**
     * Instantiate the player handler with the player unique ID. It connects to RTDB and get the
     * references for the rooms, players, and player unique ID.
     * @param puid
     */
    public FirebasePlayerHandler(String puid){
        this.puid = puid;
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        this.rooms = database.getReference("rooms");
        players = database.getReference("players");
        player = database.getReference("players/"+this.puid);
        addMovesEventListener(players);
        updateScore(0);
        System.out.println("initing done");
    }

    /**
     * This method reads the room state from RTDB and parse it locally to the Room object.
     * Breaking this down into sequential rather than callback as that stops the Universe
     * from getting null values.
     */
    public void readRoomStates() {
        this.roomId = NULL;
        Task<DataSnapshot> roomIdTask = rooms.child("rooms_listing").get();

        while(!roomIdTask.isComplete()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (roomIdTask.isSuccessful()) {
            roomId = String.valueOf(roomIdTask.getResult().getValue());
            System.out.println("room id read: " + roomId);
            room = new Room(roomId);
        }

        Task<DataSnapshot> roomStatesTask = rooms.child(roomId).get();
        while(!roomStatesTask.isComplete()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (roomStatesTask.isSuccessful()) {
            HashMap<String, Object> states = (HashMap<String, Object>) roomStatesTask.getResult().getValue();
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
        System.out.println("read room states in player handler completed.");
    }

    /**
     * This allows the player to leave the room at the end of the game--same logic as in the
     * FirebaseRoomHandler.
     */
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

    /**
     * This allows the player to end the game--same logic as in the FirebaseRoomHandler.
     */
    public void endGame() {
        if (this.room == null) return;
        players.setValue("");
        rooms.setValue("");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put("rooms_listing", "");
        rooms.updateChildren(childUpdates);
        room = null;
    }

    /**
     * This allows the player update their move to RTDB--same logic as in the FirebaseRoomHandler.
     * @param newMove
     */
    public void updateMove(String newMove){
        //System.out.println("adding move...");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.puid+"/pos", newMove);
        players.updateChildren(childUpdates);
        //System.out.println("done");
        return;
    }

    /**
     * This allows the player update their score to RTDB--same logic as in the FirebaseRoomHandler.
     * @param score
     */
    public void updateScore(int score){
        System.out.println("updating score...");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.puid+"/score", score);
        players.updateChildren(childUpdates);
        System.out.println("done");
        return;
    }

    /**
     * Getter for the local Room object.
     * @return
     */
    public Room getRoom(){ return this.room; }

    /**
     * The player event listener is added to the players reference and parse all the changes to
     * the local Room variable which is read by the Universe.
     * @param playersRef
     */
    private void addMovesEventListener(DatabaseReference playersRef) {
        ValueEventListener movesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    HashMap<String, Object> val = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println("val: "+val);
                    HashMap<String, String> player_ids = room.getPlayer_ids();
                    for(String k : val.keySet()){
                        HashMap<String, Object> val_prop = new HashMap<>();
                        if (player_ids.containsKey(k) && !k.equals(puid)){
                            val_prop = (HashMap<String, Object>) val.get(k);
                            System.out.println("val prop: "+val_prop);
                            room.updatePlayerState(k, val_prop);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
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

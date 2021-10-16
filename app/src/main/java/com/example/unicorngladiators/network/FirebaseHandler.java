package com.example.unicorngladiators.network;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import androidx.annotation.NonNull;

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

public class FirebaseHandler {
    private FirebaseDatabase database;
    private DatabaseReference players, rooms;
    private String puid, roomId;
    private Room room;

    private String[] playerNames = {"Toto", "Titi", "Tata", "Tutu"};

    public FirebaseHandler(){
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        //database.getReference("message").setValue("init");
        players = database.getReference("players");
        this.puid = players.push().getKey();
        addMovesEventListener(players);
        try {
            this.joinRoom();
        } catch (Exception e){

            System.out.println(e.toString()+"Cannot join room");
        }
        System.out.println("initing done");
    }

    public void initRoom(){
        rooms = database.getReference("rooms");
        this.roomId = rooms.push().getKey();
        List<String> bullets = new ArrayList<String>();
        double current_speed = 1.0;
        for(int i=0;i<100;i++) {
            Bullet tmp = new Bullet(current_speed, 100, 100);
            bullets.add(tmp.toString());
            current_speed *= tmp.getSpeed();
        }

        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put("rooms_listing", this.roomId);
        childUpdates.put(this.roomId+"/start", false);
        childUpdates.put(this.roomId+"/ended", false);
        childUpdates.put(this.roomId+"/num_players", 0);
        childUpdates.put(this.roomId+"/player_ids", new HashMap<String, Object>());
        childUpdates.put(this.roomId+"/bullets", bullets);
        rooms.updateChildren(childUpdates);
        this.room = new Room(this.roomId);
        this.room.setBullets(bullets);
    }

    public void readRoomStates(){
        rooms.child(roomId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    HashMap<String, Object> states = (HashMap<String, Object>) task.getResult().getValue();
                    room.setPlayerIds((HashMap<String, String>)states.get("player_ids"));
                    room.setStart((boolean) states.get("start"));
                    room.setEnd((boolean) states.get("end"));
                    room.setBullets((List<String>) states.get("bullets"));
                }
            }
        });
    }

    public void joinRoom() throws Exception{
        System.out.println("starting join...");
        rooms = database.getReference("rooms");
        this.roomId = NULL;
        rooms.child("rooms_listing").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    roomId = String.valueOf(task.getResult().getValue());
                    room = new Room(roomId);
                }
            }
        });

        System.out.println("Checked room: "+this.roomId);

        if(this.roomId != NULL) {
            this.readRoomStates();
            if (room.getNum_players() > 4) throw new Exception("Room is full--try again later.");
            if (room.isStart()) throw new Exception("Game is started--try again later.");
            this.room.addPlayer(this.puid, this.playerNames[this.room.getNum_players()]);
            Map<String, Object> childUpdates = new HashMap<String, Object>();
            childUpdates.put(this.roomId+"/num_players", this.room.getNum_players());
            childUpdates.put(this.roomId+"/player_ids", this.room.getPlayer_ids());
            rooms.updateChildren(childUpdates);
        } else {
            this.initRoom();
        }
        addRoomEventListener(rooms);
    }

    public void endGame() throws Exception{
        if (this.room == null) throw new Exception("Not in a room.");
        rooms.setValue(NULL);
        players.setValue(NULL);
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

    private void addRoomEventListener(DatabaseReference roomRef) {
        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    HashMap<String, Object> val = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println(val);
                    room.setStart((boolean) val.get("start"));
                    room.setPlayerIds((HashMap<String, String>) val.get("player_ids"));
                    System.out.println("set new room state.");
                } catch (Exception e){

                }
                //System.out.println(dataSnapshot.getValue().getClass());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        };
        roomRef.addValueEventListener(roomListener);
    }

}

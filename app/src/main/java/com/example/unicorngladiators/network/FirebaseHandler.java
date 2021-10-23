package com.example.unicorngladiators.network;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.unicorngladiators.GameActivity;
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

public class FirebaseHandler implements Parcelable {
    private FirebaseDatabase database;
    private DatabaseReference players, rooms;
    private String puid, roomId;
    private Room room = null;
    private int width, height;
    private TextView playerCount;
    private Button startGameBtn;
    private boolean inRoom, started = false;

    private String[] playerNames = {"Toto", "Titi", "Tata", "Tutu"};
    private String[] initialPos;

    public FirebaseHandler(int width, int height, TextView playerCount, Button startGameBtn){
        this.playerCount = playerCount;
        this.startGameBtn = startGameBtn;
        this.width = width;
        this.height = height;
        initialPos = new String[] {
                new Position(width / 5, height / 2).shortString(),
                new Position(2*width / 5, height / 2).shortString(),
                new Position(3*width / 5, height / 2).shortString(),
                new Position(4*width / 5, height / 2).shortString(),
        };
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        players = database.getReference("players");
        this.puid = players.push().getKey();
        addMovesEventListener(players);
        System.out.println("initing done");
    }

    protected FirebaseHandler(Parcel in) {
        puid = in.readString();
        roomId = in.readString();
        width = in.readInt();
        height = in.readInt();
        inRoom = in.readByte() != 0;
        started = in.readByte() != 0;
        playerNames = in.createStringArray();
        initialPos = in.createStringArray();
    }

    public static final Creator<FirebaseHandler> CREATOR = new Creator<FirebaseHandler>() {
        @Override
        public FirebaseHandler createFromParcel(Parcel in) {
            return new FirebaseHandler(in);
        }

        @Override
        public FirebaseHandler[] newArray(int size) {
            return new FirebaseHandler[size];
        }
    };

    public void initRoom(){
        rooms = database.getReference("rooms");
        this.roomId = rooms.push().getKey();
        List<String> bullets = Bullet.generateBulletStringList(100,this.height,this.width,1.1);

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
        try {
            addPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    try {
                        addPlayer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void joinRoom() {
        System.out.println("starting join...");
        rooms = database.getReference("rooms");
        this.roomId = NULL;
        rooms.child("rooms_listing").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    roomId = String.valueOf(task.getResult().getValue());
                    System.out.println("room id read: " + roomId);
                    inRoom = true;
                    if (!roomId.equals("")) {
                        room = new Room(roomId);
                        readRoomStates();
                    } else {
                        initRoom();
                        if (room.getNum_players() == 1) {
                            startGameBtn.setVisibility(View.VISIBLE);
                            startGameBtn.setEnabled(false);
                        }
                    }
                }
            }
        });
    }

    public void addPlayer() throws Exception {
        if (room.getNum_players() > 4) throw new Exception("Room is full--try again later.");
        //if (room.isStart()) throw new Exception("Game is started--try again later.");
        this.room.addPlayer(this.puid, this.playerNames[this.room.getNum_players()]);
        this.updateMove(this.initialPos[this.room.getNum_players()]);
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.roomId+"/num_players", this.room.getNum_players());
        childUpdates.put(this.roomId+"/player_ids", this.room.getPlayer_ids());
        rooms.updateChildren(childUpdates);
        addRoomEventListener(rooms);
        this.playerCount.setText("Current Number Of Players : "+this.room.getNum_players());
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
        this.playerCount.setText("Current Number Of Players : "+this.room.getNum_players());

        startGameBtn.setVisibility(View.GONE);
        startGameBtn.setEnabled(false);

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

    public void startGame() {
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.roomId+"/start", true);
        rooms.updateChildren(childUpdates);
        room.setStart(true);
        started = true;
    }

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
                    HashMap<String, Object> room_spec = (HashMap<String, Object>) (val.get(room.getId()));
                    HashMap<String, String> player_ids = new HashMap<String, String>();
                    player_ids = (HashMap<String, String>) room_spec.get("player_ids");
                    room.setPlayerIds(player_ids);
                    List<String> bullets = new ArrayList<String>();
                    bullets = (List<String>) room_spec.get("bullets");
                    room.setBullets(bullets);
                    Boolean start = new Boolean(false);
                    start = (Boolean) room_spec.get("start");
                    Boolean ended = new Boolean(false);
                    ended = (Boolean) room_spec.get("ended");
                    room.setStart((boolean) start);
                    room.setEnd((boolean) ended);
                    System.out.println("the bools" + room.isStart() + room.isEnd());
                    System.out.println("set new room state.");
                    playerCount.setText("Current Number Of Players : "+ room.getNum_players());
                    startGameBtn.setEnabled(room.getNum_players() > 1);
                    if (room.getNum_players() == 1 && inRoom) {
                        startGameBtn.setVisibility(View.VISIBLE);
                        startGameBtn.setEnabled(false);
                    }
                    if (room.isStart() && !started) {
                        startGameBtn.setEnabled(true);
                        startGameBtn.performClick();
                        started = true;
                    }

                } catch (Exception e){
                    e.printStackTrace();
                    //playerCount.setText("");
                    //room = null;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(puid);
        parcel.writeString(roomId);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeByte((byte) (inRoom ? 1 : 0));
        parcel.writeByte((byte) (started ? 1 : 0));
        parcel.writeStringArray(playerNames);
        parcel.writeStringArray(initialPos);
    }
}

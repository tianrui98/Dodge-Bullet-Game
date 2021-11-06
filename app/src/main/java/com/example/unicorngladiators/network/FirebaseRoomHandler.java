package com.example.unicorngladiators.network;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.graphics.PostProcessor;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The FirebaseRoomHandler class abstracts the handling of the Firebase operations to
 * simple method calls. It will also update the waiting room activities so the UI
 * components are passed into its constructor.
 */
public class FirebaseRoomHandler implements Serializable {
    private FirebaseDatabase database;
    private DatabaseReference players, rooms;
    private String puid, roomId;
    private Room room = null;
    private int width, height;
    private TextView playerCount;
    private Button startGameBtn;
    private boolean inRoom, started = false;

    private String[] playerNames = {"toto", "titi", "tata", "tutu"};
    private String[] initialPos;

    /**
     * The constructor takes in the width and height of the screen to make sure the bullet spawning
     * will be out of screen and go across the screen.  The playerCount TextView component is for
     * updating the waiting room activity on how many players are in the waiting room.  The
     * startGameBtn Button is enabled for the host of the waiting room and is enabled when there are
     * at least 2 players.
     *
     * This initialized the connection to Firebase and create the database, players, room, and add
     * a player record to the Firebase Real-time Database (RTDB).  This write returns a unique ID
     * for the player.
     * @param width
     * @param height
     * @param playerCount
     * @param startGameBtn
     */
    public FirebaseRoomHandler(int width, int height, TextView playerCount, Button startGameBtn){
        this.playerCount = playerCount;
        this.startGameBtn = startGameBtn;
        this.width = width;
        this.height = height;
        initialPos = new String[] {
                new Position(200, 200).shortString(),
                new Position(200, 220).shortString(),
                new Position(200, 240).shortString(),
                new Position(200, 260).shortString()
        };
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        players = database.getReference("players");
        this.puid = players.push().getKey();
        System.out.println("initing done");
    }

    /**
     * This method initialize the room attributes on Firebase.  It pushes a room record and retrieve
     * the room ID.  It generates the Bullet list in string representation and the record it under
     * the room ID record and initializes the start, and end state to false.  It also initializes
     * the number of players to 0 and the player ID-name mapping to an empty HashMap.
     */
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

    /**
     * This parses the room state that was written to under the room ID to local.
     * If the game is already started in the room then initialize a new room.
     */
    public void readRoomStates(){
        rooms.child(roomId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    HashMap<String, Object> states = (HashMap<String, Object>) task.getResult().getValue();
                    System.out.println("states:"+states);
                    Boolean start = new Boolean(false);
                    start = (Boolean) states.get("start");
                    if (start) {
                        initRoom();
                        if (room.getNum_players() == 1) {
                            startGameBtn.setVisibility(View.VISIBLE);
                            startGameBtn.setEnabled(false);
                        }
                    } else {
                        Boolean ended = new Boolean(false);
                        ended = (Boolean) states.get("ended");
                        room.setPlayerIds((HashMap<String, String>) states.get("player_ids"));
                        room.setStart((boolean) start);
                        room.setEnd((boolean) ended);
                        room.setBullets((List<String>) states.get("bullets"));
                        System.out.println("bullets after read room state:" + room.getBullets().size());
                        try {
                            addPlayer();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * This allows an instance to join a room and read its Firebase state to local.
     * If there is only one player in the room (i.e. the current instance initing
     * the room) then the instance is the host and the start button is set to visible
     * but is disabled (cannot start a game with one player).
     */
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

    /**
     * This adds a player to the room.  This is called by the constructor.  When the instance is
     * created the player is added to the room.
     * @throws Exception
     */
    public void addPlayer() throws Exception {
        if (room.getNum_players() > 4) throw new Exception("Room is full--try again later.");
        //if (room.isStart()) throw new Exception("Game is started--try again later.");
        this.room.addPlayer(this.puid, this.playerNames[this.room.getNum_players()],
                new Position(this.initialPos[this.room.getNum_players()]));
        this.updateMove(this.initialPos[this.room.getNum_players()]);
        this.updateScore(3);
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.roomId+"/num_players", this.room.getNum_players());
        childUpdates.put(this.roomId+"/player_ids", this.room.getPlayer_ids());
        rooms.updateChildren(childUpdates);
        addRoomEventListener(rooms);
        this.playerCount.setText("Current Number Of Players : "+this.room.getNum_players());
    }

    /**
     * This allows the current instance to leave the waiting room.  If the instance is the last one
     * in the room, it resets the Firebase instance to the empty state.
     */
    public void leaveRoom() {
        if (!inRoom) return;
        inRoom = false;
        this.room.removePlayer(this.puid);
        database.getReference("players/"+this.puid).removeValue();
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

    /**
     * This function resets the Firebase RTDB to the initial state.
     */
    public void endGame() {
        if (this.room == null) return;
        // database.getReference("rooms/"+this.roomId).removeValue();
        room = null;
    }

    /**
     * Get the player unique ID.
     * @return
     */
    public String getPuid(){ return this.puid; }

    /**
     * Update the move of the current player by writing to RTDB under the player record.
     * @param newMove
     */
    public void updateMove(String newMove){
        System.out.println("adding move...");
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.puid+"/pos", newMove);
        players.updateChildren(childUpdates);
        System.out.println("done");
        return;
    }

    /**
     * Get other player's (identify using their unique ID as other) Position in shortString representation.
     * @param other
     * @return
     */
    public String getOtherPlayerPos(String other){
        System.out.println("reading move...");
        Task<DataSnapshot> tmp = players.child(other + "/pos").get();
        while (!tmp.isComplete()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (tmp.isSuccessful()){
            return String.valueOf(tmp.getResult().getValue());
        }
        return "0, 0";
    }

    /**
     * Update the current player score by writing to RTDB under the player record.
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
     * Get other player's (identify using their unique ID as other) score.
     * @param other
     * @return
     */
    public int getOtherPlayerScore(String other){
        System.out.println("reading move...");
        Task<DataSnapshot> tmp = players.child(other + "/score").get();
        while (!tmp.isComplete()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (tmp.isSuccessful()){
            return ((Long) tmp.getResult().getValue()).intValue();
        }
        return 0;
    }

    /**
     * Getter for the Room object.
     * @return
     */
    public Room getRoom(){ return this.room; }

    /**
     * Start the game by writing true to the start state of the room.
     * This also update the room state locally.
     */
    public void startGame() {
        Map<String, Object> childUpdatesListing = new HashMap<String, Object>();
        childUpdatesListing.put("rooms_listing", "");
        rooms.updateChildren(childUpdatesListing);
        Map<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(this.roomId+"/start", true);
        rooms.updateChildren(childUpdates);
        room.setStart(true);
        started = true;

    }

    /**
     * This is the room event change listener adder to the room RTDB reference.
     * This updates all the room states that is changed to the local room object.  This also checks
     * if the game is started and start the game on other instances as the host press start.
     * This updates the waiting room activity's number of player component.
     * @param roomRef
     */
    private void addRoomEventListener(DatabaseReference roomRef) {
        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    System.out.println("room: "+ room);
                    HashMap<String, Object> val = (HashMap<String, Object>) dataSnapshot.getValue();
                    HashMap<String, Object> room_spec = (HashMap<String, Object>) (val.get(room.getId()));
                    HashMap<String, String> player_ids = new HashMap<String, String>();
                    player_ids = (HashMap<String, String>) room_spec.get("player_ids");
                    room.setPlayerIds(player_ids);
                    HashMap<String, Position> tmpPos = new HashMap<String, Position>();
                    HashMap<String, Integer> tmpScores = new HashMap<String, Integer>();
                    for(String p : player_ids.keySet()){
                        tmpPos.put(p, new Position(getOtherPlayerPos(p)));
                        tmpScores.put(p, getOtherPlayerScore(p));
                    }
                    room.setPlayer_pos(tmpPos);
                    room.setPlayer_scores(tmpScores);
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
}

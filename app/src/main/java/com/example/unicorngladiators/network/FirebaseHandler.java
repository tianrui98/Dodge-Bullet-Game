package com.example.unicorngladiators.network;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {
    private FirebaseDatabase database;
    private DatabaseReference players;
    private String puid;
    private TextView demo;

    public FirebaseHandler(){
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        //database.getReference("message").setValue("init");
        players = database.getReference("players");
        players.setValue("NOTHING");
        this.puid = players.push().getKey();
        addMovesEventListener(players);
        System.out.println("initing done");
    }

    public FirebaseHandler(TextView toyDemo){
        System.out.println("initing handler...");
        database = FirebaseDatabase.getInstance("https://unicorn-63649-default-rtdb.asia-southeast1.firebasedatabase.app");
        //database.getReference("message").setValue("init");
        players = database.getReference("players");
        players.setValue("NOTHING");
        this.puid = players.push().getKey();
        addMovesEventListener(players);
        System.out.println("initing done");
        this.demo = toyDemo;
        demo.setText("inited firebase connection.");
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

    private void addMovesEventListener(DatabaseReference playersRef) {
        // [START post_value_event_listener]
        ValueEventListener movesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    HashMap<String, String> val = (HashMap<String, String>) dataSnapshot.getValue();
                    System.out.println(val);
                    if (demo != null)
                        demo.setText("Current Position: "+val.get(puid));
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

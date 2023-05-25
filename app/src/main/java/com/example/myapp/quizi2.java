package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class quizi2 extends AppCompatActivity {

    public static ArrayList<quizi> list;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizi2);

        list = new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("Hakob Hovnatanyan");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    quizi quizi=dataSnapshot.getValue(quizi.class);
                    list.add(quizi);
                }
                startActivity(new Intent(quizi2.this,quiz.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

  //      list.add(new quizi("mamat","papat","mort","mort"));
  //      list.add(new quizi("kuku","lala","titik","lala"));
  //      list.add(new quizi("Hovhaness","lala","titik","Hovhaness"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
     //           Intent intent = new Intent(quizi2.this,quiz.class);
     //           startActivity(intent);
            }
        },1000);
    }
}
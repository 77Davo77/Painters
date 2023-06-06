package com.example.myapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaintersChoose extends AppCompatActivity {

    private TextView painter1, painter2, painter3, painter4, painter5, painter6, painter7, painter8, painter9, painter10;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painters_choose);

        databaseReference = FirebaseDatabase.getInstance().getReference("painters");

        Hooks();
        setAllData();
        painter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaintersChoose.this,AboutThePainters.class));
            }
        });

    }



    private void Hooks() {
        painter1 = findViewById(R.id.painter1);
        painter2 = findViewById(R.id.painter2);
        painter3 = findViewById(R.id.painter3);
        painter4 = findViewById(R.id.painter4);
        painter5 = findViewById(R.id.painter5);
        painter6 = findViewById(R.id.painter6);
        painter7 = findViewById(R.id.painter7);
        painter8 = findViewById(R.id.painter8);
        painter9 = findViewById(R.id.painter9);
        painter10 = findViewById(R.id.painter10);
    }

    private void setAllData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String painter1Data = dataSnapshot.child("1").getValue(String.class);
                    String painter2Data = dataSnapshot.child("2").getValue(String.class);
                    String painter3Data = dataSnapshot.child("3").getValue(String.class);
                    String painter4Data = dataSnapshot.child("4").getValue(String.class);
                    String painter5Data = dataSnapshot.child("5").getValue(String.class);
                    String painter6Data = dataSnapshot.child("6").getValue(String.class);
                    String painter7Data = dataSnapshot.child("7").getValue(String.class);
                    String painter8Data = dataSnapshot.child("8").getValue(String.class);
                    String painter9Data = dataSnapshot.child("9").getValue(String.class);
                    String painter10Data = dataSnapshot.child("10").getValue(String.class);

                    painter1.setText(painter1Data);
                    painter2.setText(painter2Data);
                    painter3.setText(painter3Data);
                    painter4.setText(painter4Data);
                    painter5.setText(painter5Data);
                    painter6.setText(painter6Data);
                    painter7.setText(painter7Data);
                    painter8.setText(painter8Data);
                    painter9.setText(painter9Data);
                    painter10.setText(painter10Data);

                    painter1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startAboutThePaintersActivity(painter1Data);
                        }
                    });
                    painter2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startAboutThePaintersActivity(painter2Data);
                        }
                    });
                    painter3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startAboutThePaintersActivity(painter3Data);
                        }
                    });
                    painter4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startAboutThePaintersActivity(painter4Data);
                        }
                    });
                    painter5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
                        }
                    });
                    painter6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
                        }
                    });
                    painter7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
                        }
                    });
                    painter8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
                        }
                    });
                    painter9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
                        }
                    });
                    painter10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaintersChoose.this, "Пока не добавлена", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startAboutThePaintersActivity(String painterName) {
        Intent intent = new Intent(PaintersChoose.this, AboutThePainters.class);
        intent.putExtra("painterName", painterName);
        startActivity(intent);
    }

}

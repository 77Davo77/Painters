package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ChooseGame extends AppCompatActivity {

    BottomNavigationView bottomNavigationView2;

    TextView backBtn;
    ImageButton imageButton1, imageButton2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_choose_game);

        bottomNavigationView2 = findViewById(R.id.navigation_bar1);
        bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.statistics:
                        startActivity(new Intent(ChooseGame.this, Statistics2.class));
                        break;
                    case R.id.account:
                        startActivity(new Intent(ChooseGame.this, Account.class));
                        break;
                    case R.id.home:
                        startActivity(new Intent(ChooseGame.this, MainActivity.class));
                        break;
                }
                return true;
            }
        });

        imageButton1 = findViewById(R.id.imageButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseGame.this,Painters.class));
            }
        });

        imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseGame.this,quizi2.class));
            }
        });
    }
}
package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PuzzleLevels extends AppCompatActivity {

    TextView back14,level1,level2,level3;
    BottomNavigationView bottomNavigationView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_puzzle_levels);

        bottomNavigationView2 = findViewById(R.id.bottom_navigation2);
        bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.account:
                        startActivity(new Intent(PuzzleLevels.this, Account.class));
                        break;
                    case R.id.home:
                        startActivity(new Intent(PuzzleLevels.this, MainActivity.class));
                        break;
                }
                return true;
            }
        });

        level1 = findViewById(R.id.level1);
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PuzzleLevels.this,Puzzle.class));
            }
        });
    }
}
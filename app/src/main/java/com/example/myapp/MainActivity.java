package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.play.core.integrity.v;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView changeLanguage, infoBtn;
    FirebaseAuth firebaseAuth;
    TextView how2, start, play;

    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);
        Language.updateLanguage(this);
        firebaseAuth = FirebaseAuth.getInstance();

        start = findViewById(R.id.button1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PaintersChoose.class));
            }
        });

        TextView btn = findViewById(R.id.how);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, login.class));
            }
        });

        infoBtn = findViewById(R.id.info);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMessage(R.string.abc2);
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mBuilder.setNegativeButton("NEXT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                        mBuilder.setIcon(android.R.drawable.sym_def_app_icon);
                        mBuilder.setTitle(R.string.dialog_title);
                        mBuilder.setCancelable(false);
                        mBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog alertDialog = mBuilder.create();
                        alertDialog.show();
                    }
                });
                AlertDialog alertDialog = mBuilder.create();
                alertDialog.show();
            }
        });
        how2 = findViewById(R.id.how2);
        how2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Info.class));
            }
        });

        play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChooseGame.class));
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.statistics:
                        startActivity(new Intent(MainActivity.this, Statistics2.class));
                        break;
                    case R.id.account:
                        startActivity(new Intent(MainActivity.this, Account.class));
                        break;
                }
                return true;
            }
        });

        ImageView flagArmenia = findViewById(R.id.flagArmenia);
        flagArmenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang = "hy";
                Language.setLocale(MainActivity.this, lang);
                recreate();
            }
        });

        ImageView flagRussia = findViewById(R.id.flagRussia);
        flagRussia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang = "ru";
                Language.setLocale(MainActivity.this, lang);
                recreate();
            }
        });

        ImageView flagEnglish = findViewById(R.id.flagEnglish);
        flagEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang = "en";
                Language.setLocale(MainActivity.this, lang);
                recreate();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Push again for,go out", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
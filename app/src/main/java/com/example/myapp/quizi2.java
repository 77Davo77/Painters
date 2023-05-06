package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class quizi2 extends AppCompatActivity {

    public static ArrayList<quizi> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizi2);

        list = new ArrayList<>();
        list.add(new quizi("mamat","papat","mort","answer"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(quizi2.this,quiz.class);
                startActivity(intent);
            }
        },1500);
    }
}
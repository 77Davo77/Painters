package com.example.myapp;


import static com.example.myapp.quizi2.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class quiz extends AppCompatActivity {

    TextView option1,option2,option3;
    CardView card1,card2,card3;
    List<quizi> allQuestions;
    LinearLayout nextBack;
    quizi quizi;
    int index = 0;
    int correctCount=0;
    int wrongCount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Hooks();

        allQuestions=list;
        Collections.shuffle(allQuestions);
        quizi=list.get(index);

        setAllData();
    }

    private void Hooks() {
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);

        nextBack = findViewById(R.id.nextBack);
    }

    private void setAllData() {
        option1.setText(quizi.getOption1());
        option2.setText(quizi.getOption2());
        option3.setText(quizi.getOption3());
    }
    public void Correct(){
        nextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                index++;
                quizi = list.get(index);
                setAllData();
            }
        });
    }

    public void Wrong(){
        wrongCount++;
        if(index<list.size()-1){
            index++;
            quizi = list.get(index);
            setAllData();
        }else {
            GameWon();
        }
    }

    private void GameWon() {
        Intent intent = Intent(quiz.this,WonActivity.class);
        startActivity(intent);
    }
}
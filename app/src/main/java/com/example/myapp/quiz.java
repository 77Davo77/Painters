package com.example.myapp;


import static com.example.myapp.quizi2.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class quiz extends AppCompatActivity {

    TextView option1, option2, option3;
    CardView card1, card2, card3;
    List<quizi> allQuestions;

    LinearLayout nextBack;

    quizi quizi;
    int index = 0;
    int correctCount = 0;
    int wrongCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Hooks();

        allQuestions = list;
        Collections.shuffle(allQuestions);
        quizi = list.get(index);

        card1.setBackgroundColor(getResources().getColor(R.color.white));
        card2.setBackgroundColor(getResources().getColor(R.color.white));
        card3.setBackgroundColor(getResources().getColor(R.color.white));

        enableButton();
        nextBack.setClickable(false);


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

    public void Correct(CardView cardView) {

        cardView.setBackgroundColor(getResources().getColor(R.color.green));
        nextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                if (index < list.size() - 1) {
                    index++;
                    quizi = list.get(index);
                    resetColor();
                    setAllData();
                    enableButton();
                }
            }
        });
    }
    public void Wrong(CardView card1) {

        card1.setBackgroundColor(getResources().getColor(R.color.red));

        nextBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrongCount++;
                if (index < list.size() - 1) {
                    index++;
                    quizi = list.get(index);
                    resetColor();
                    setAllData();
                    enableButton();
                } else {
                    GameWon();
                }
            }
        });
    }

    private void GameWon() {
        Intent intent = new Intent(quiz.this, WonActivity.class);
        intent.putExtra("correct", correctCount);
        intent.putExtra("wrong", wrongCount);
        startActivity(intent);
    }

    public void enableButton() {
        card1.setClickable(true);
        card2.setClickable(true);
        card3.setClickable(true);
    }

    public void disableButton() {
        card1.setClickable(false);
        card2.setClickable(false);
        card3.setClickable(false);
    }

    public void resetColor() {
        card1.setBackgroundColor(getResources().getColor(R.color.white));
        card2.setBackgroundColor(getResources().getColor(R.color.white));
        card3.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void Option1Click(View view) {
        disableButton();
        nextBack.setClickable(true);
        if (quizi.getOption1().equals(quizi.getAnswer())) {
            card1.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(card1);
            } else {
                GameWon();
            }
        } else {
            Wrong(card1);
        }
    }

    public void Option2Click(View view) {
        disableButton();
        nextBack.setClickable(true);
        if (quizi.getOption2().equals(quizi.getAnswer())) {
            card2.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(card2);
            } else {
                GameWon();
            }
        } else {
            Wrong(card2);
        }
    }

    public void Option3Click(View view) {
        disableButton();
        nextBack.setClickable(true);
        if (quizi.getOption3().equals(quizi.getAnswer())) {
            card3.setCardBackgroundColor(getResources().getColor(R.color.green));

            if (index < list.size() - 1) {
                Correct(card3);
            } else {
                GameWon();
            }
        } else {
            Wrong(card3);
        }
    }
}
package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.Random;

public class Verification extends AppCompatActivity {

    private EditText ver1, ver2, ver3, ver4;
    private TextView resendBtn;
    private boolean resendEnabled = false;
    private int resentTime = 60;

    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        ver1 = findViewById(R.id.ver1);
        ver2 = findViewById(R.id.ver2);
        ver3 = findViewById(R.id.ver3);
        ver4 = findViewById(R.id.ver4);

        resendBtn = findViewById(R.id.resendBtn);

        final TextView verEmail = findViewById(R.id.verEmail);

        final String getEmail = getIntent().getStringExtra("email");

        verEmail.setText(getEmail);

        showKeyboard(ver1);

        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(resendEnabled) {
                    startCountDownTimer();
                }
            }
        });
        Button verifyBtn;
        verifyBtn = findViewById(R.id.verifyBtn);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void showKeyboard(EditText ver) {

        ver.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(ver,InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer() {

        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resentTime * 1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code ("+(millisUntilFinished / 1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(com.google.android.material.R.color.material_blue_grey_950));
            }
        }.start();
    }
}
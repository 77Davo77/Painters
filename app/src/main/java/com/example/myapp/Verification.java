package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapp.databinding.ActivityVerificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Verification extends AppCompatActivity {
    private TextView resendBtn;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "Verification";
    ActivityVerificationBinding binding;
    EditText mEmail;
    private boolean resendEnable = true;
    private final int resendTime = 60;

    @SuppressLint({"RestrictedApi", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        final TextView verEmail = findViewById(R.id.verEmail);
        final String getEmail = getIntent().getStringExtra("email");
        verEmail.setText(getEmail);
        resendBtn = findViewById(R.id.resendBtn);
        startCountDownTimer();
        firebaseAuth = FirebaseAuth.getInstance();
        binding= ActivityVerificationBinding.inflate(getLayoutInflater());






        resendBtn.setOnClickListener(view -> {
            if(resendEnable){
                sendVerificationEmail2();
                Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void startCountDownTimer(){
        resendEnable = true;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 60L,100){

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                resendBtn.setText("Resend link ("+(l / 60)+")");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                resendEnable = true;
                resendBtn.setText("Resend link");
                resendBtn.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary_dark));
            }
        }.start();
    }
    private void sendVerificationEmail2() {
       Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).
        sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Verification.this, "Email sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Verification.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Verification.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Verification.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void setCurrentEmail(){
        Log.d(TAG, "setCurrentEmail: setting current email to EditText field");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(TAG, "setCurrentEmail: user is NOT null.");

            String email = user.getEmail();

            Log.d(TAG, "setCurrentEmail: got the email: " + email);

            mEmail.setText(email);
        }
    }

    }

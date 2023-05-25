package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.RegexValidator;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.databinding.ActivityRegistorBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class registor extends AppCompatActivity {

    public static WindowDecorActionBar.TabImpl Email;
    ActivityRegistorBinding binding;

    FirebaseAuth firebaseAuth;

    FirebaseFirestore firebaseFirestore;

    ProgressDialog progressDialog;
    boolean passwordVisible;
    boolean passwordVisible2;
    EditText password3;

    TextView verification;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        binding = ActivityRegistorBinding.inflate(getLayoutInflater());
        Language.updateLanguage(this);
        setContentView(binding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        verification = findViewById(R.id.verification);

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationEmail2();
                Toast.makeText(registor.this, "Email sent", Toast.LENGTH_SHORT).show();
            }
        });


        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.username.getText().toString();
                String email = binding.Email.getText().toString().trim();
                String password = binding.password1.getText().toString();
                String confirmPassword = binding.password3.getText().toString();
                sendVerificationEmail2();
                Toast.makeText(registor.this, "We sent you verification link to email", Toast.LENGTH_SHORT).show();
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(registor.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
                if(Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()){
                    Intent intent = new Intent(registor.this, login.class);
                    intent.putExtra("email", email);
                    intent.putExtra(Intent.EXTRA_SUBJECT, email);
                    startActivity(intent);
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    firebaseFirestore.collection("User")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .set(new UserModel(name, email));

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(registor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            });
                }else{
                    Toast.makeText(registor.this, "Pleas verify email", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registor.this, login.class));
            }
        });

        EditText password1 = findViewById(R.id.password1);
        password1.setOnTouchListener((view, motionEvent) -> {
            final int Right = 2;
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= password1.getRight() - password1.getCompoundDrawables()[Right].getBounds().width()) {
                    int selection = password1.getSelectionEnd();
                    if (passwordVisible) {
                        password1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible = false;
                    } else {
                        password1.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                        password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible = true;
                    }
                    password1.setSelection(selection);
                    return true;
                }
            }

            return false;
        });
        password3 = findViewById(R.id.password3);
        password3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password3.getRight() - password3.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password3.getSelectionEnd();
                        if (passwordVisible2) {
                            password3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            password3.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible2 = false;
                        } else {
                            password3.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            password3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible2 = true;
                        }
                        password3.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

    }
        private void sendVerificationEmail2() {
            FirebaseAuth.getInstance().getCurrentUser().
                    sendEmailVerification()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(registor.this, "Email sent", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(registor.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
}

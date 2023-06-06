package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.databinding.ActivityRegistorBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class registor extends AppCompatActivity {

    ActivityRegistorBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    boolean passwordVisible;
    boolean passwordVisible2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistorBinding.inflate(getLayoutInflater());
        Language.updateLanguage(this);
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.username.getText().toString();
                String email = binding.Email.getText().toString().trim();
                String password = binding.password1.getText().toString();
                String confirmPassword = binding.password3.getText().toString();

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(registor.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setTitle("Registering User");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    sendEmailVerification(user);
                                    Intent intent = new Intent(registor.this,login.class);
                                    intent.putExtra("name",name);
                                    startActivity(intent);
                                }
                                progressDialog.dismiss();
                                saveUserToFirestore(name, email);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(registor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
            }
        });

        // Rest of your code...

        // Password visibility toggle for password1 EditText
        EditText password1 = findViewById(R.id.password1);
        password1.setOnTouchListener((view, motionEvent) -> {
            final int Right = 2;
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= password1.getRight() - password1.getCompoundDrawables()[Right].getBounds().width()) {
                    togglePasswordVisibility(password1);
                    return true;
                }
            }
            return false;
        });

        // Password visibility toggle for password3 EditText
        EditText password3 = findViewById(R.id.password3);
        password3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password3.getRight() - password3.getCompoundDrawables()[Right].getBounds().width()) {
                        togglePasswordVisibility(password3);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(registor.this, "Email verification link sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registor.this, "Failed to send email verification", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToFirestore(String name, String email) {
        UserModel user = new UserModel(name, email);
        firebaseFirestore.collection("User")
                .document(FirebaseAuth.getInstance().getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(registor.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registor.this, login.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registor.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void togglePasswordVisibility(EditText passwordEditText) {
        int selection = passwordEditText.getSelectionEnd();
        if (passwordVisible) {
            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
            passwordEditText.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            passwordVisible = false;
        } else {
            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
            passwordEditText.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
            passwordVisible = true;
        }
        passwordEditText.setSelection(selection);
    }
}

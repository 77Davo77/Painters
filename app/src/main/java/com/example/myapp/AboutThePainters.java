package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class AboutThePainters extends AppCompatActivity {
    private TextView vernagir;
    private ImageView imageView11;
    private String currentImageId;
    private TextView text1;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_painters);

        currentImageId = "image1";

        imageView11 = findViewById(R.id.imageView11);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        Hooks();
        setAllData();
    }

    private void setAllData() {
        String painterName = getIntent().getStringExtra("painterName");
        databaseReference = FirebaseDatabase.getInstance().getReference(painterName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String vernagirData = dataSnapshot.child("vernagir").getValue(String.class);
                    String text1Data = dataSnapshot.child("text1").getValue(String.class);
                    String vernagir2Data = dataSnapshot.child("vernagir2").getValue(String.class);
                    String text2Data = dataSnapshot.child("text2").getValue(String.class);

                    vernagir.setText(vernagirData);
                    text1.setText(text1Data);

                    currentImageId = dataSnapshot.child("images").child("currentImageId").getValue(String.class);

                    loadCurrentImage();

                    TextView arrowBack = findViewById(R.id.textView5);
                    TextView arrowForward = findViewById(R.id.textView6);

                    arrowBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vernagir.setText(vernagirData);
                            text1.setText(text1Data);

                            currentImageId = "image1";
                            updateCurrentImage();
                            loadCurrentImage();
                        }
                    });

                    arrowForward.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vernagir.setText(vernagir2Data);
                            text1.setText(text2Data);

                            currentImageId = "image2";
                            updateCurrentImage();
                            loadCurrentImage();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutThePainters.this, "Ошибка при получении данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCurrentImage() {
        DatabaseReference imagesReference = databaseReference.child("images");
        DatabaseReference currentImageReference = imagesReference.child(currentImageId + "Url");

        currentImageReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imageUrl = dataSnapshot.getValue(String.class);
                    displayImage(imageUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AboutThePainters.this, "Ошибка при загрузке изображения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCurrentImage() {
        DatabaseReference imagesReference = databaseReference.child("images");
        DatabaseReference currentImageReference = imagesReference.child("currentImageId");

        currentImageReference.setValue(currentImageId);
    }

    private void displayImage(String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView11);
    }

    private void Hooks() {
        vernagir = findViewById(R.id.vernagir);
        text1 = findViewById(R.id.text1);
        imageView11 = findViewById(R.id.imageView11);
    }
}


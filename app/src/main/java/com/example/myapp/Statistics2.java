package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Statistics2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_statistics2);


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        Query topUsersQuery = usersRef.orderByChild("correctAnswers").limitToLast(10);

        // Инициализация списка пользователей и адаптера
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);

        // Настройка RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);

        // Получите список пользователей из базы данных и добавьте их в адаптер
        topUsersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();

                // Проходим по каждому дочернему элементу в DataSnapshot
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Получаем данные о пользователе из дочернего элемента
                    String name = userSnapshot.child("name").getValue(String.class);
                    int correctAnswers = userSnapshot.child("correctAnswers").getValue(Integer.class);

                    // Создаем объект пользователя и добавляем его в список
                    User user = new User(name, correctAnswers);
                    userList.add(user);
                }

                // Обновляем адаптер после получения данных
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибки
            }
        });
    }
}
    

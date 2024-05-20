package com.example.technology_app.activities.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.adapters.UserChatAdapter;
import com.example.technology_app.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private UserChatAdapter userChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initControl();
    }

    private void initControl() {
        actionToolBar();
        getUserChatFromFire();
    }

    private void getUserChatFromFire() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UserModel.User> userList = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                UserModel.User user = new UserModel.User();
                                user.set_id(documentSnapshot.getString("id"));
                                user.setEmail(documentSnapshot.getString("userName"));
                                userList.add(user);
                            }
                            if (!userList.isEmpty()) {
                                Log.d("SizeLa: ", "" + userList.size());
                                userChatAdapter = new UserChatAdapter(getApplicationContext(), userList);
                                recyclerView.setAdapter(userChatAdapter);
                            }
                        } else {
                            Log.e("Firestore Error", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarUserChat);
        recyclerView = findViewById(R.id.recycleview_user_chat);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
    }
}

package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.vecom.R;

public class PersonalActivity1 extends AppCompatActivity {
    private DatabaseReference userReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal1);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Assuming you have a node in your database named "users" where emails are stored
            userReference = FirebaseDatabase.getInstance().getReference().child("users");

            // Query the database based on email
            Query query = userReference.orderByChild("userEmail").equalTo(userEmail);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String username = userSnapshot.child("userName").getValue(String.class);
                        String phoneNumber = userSnapshot.child("userPhoneNumber").getValue(String.class);

                        // Set the username and phone number in TextViews
                        TextView userNameTextView = findViewById(R.id.userName);
                        userNameTextView.setText(username);

                        TextView phoneNumberTextView = findViewById(R.id.phoneNumber);
                        phoneNumberTextView.setText(phoneNumber);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout forumNavi = findViewById(R.id.forumNavi);
        forumNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, ForumActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, PersonalActivity1.class);
                startActivity(intent);
            }
        });

        RelativeLayout workAccount = findViewById(R.id.workAccount);
        workAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, PersonalStoreActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, PersonalActivity2.class);
                startActivity(intent);
            }
        });
        RelativeLayout logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        // Điều hướng người dùng đến màn hình đăng nhập hoặc màn hình khác theo nhu cầu của bạn.
        Intent intent = new Intent(PersonalActivity1.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đảm bảo người dùng không thể quay lại màn hình PersonalActivity bằng nút back.
    }
}
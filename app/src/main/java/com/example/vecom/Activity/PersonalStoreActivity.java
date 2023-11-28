package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class PersonalStoreActivity extends AppCompatActivity {
    private DatabaseReference userReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_store);
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
                        String email = userSnapshot.child("userEmail").getValue(String.class);

                        // Set the username and phone number in TextViews
                        TextView userNameTextView = findViewById(R.id.userName);
                        userNameTextView.setText(username);

                        TextView userEmailTextView = findViewById(R.id.userEmail);
                        userEmailTextView.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalStoreActivity.this, PersonalActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        TextView myProduct = findViewById(R.id.sanphamText);
        myProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalStoreActivity.this, MyProductsActivity.class);
                startActivity(intent);
            }
        });
        ImageView myProduct1 = findViewById(R.id.sanphamArrow);
        myProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalStoreActivity.this, MyProductsActivity.class);
                startActivity(intent);
            }
        });
    }
}
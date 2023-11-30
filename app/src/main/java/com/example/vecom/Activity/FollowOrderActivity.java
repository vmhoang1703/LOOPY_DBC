package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vecom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FollowOrderActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    Uri imageUri;
    StorageReference storageReference;
    private DatabaseReference cartItemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_order);

        // Retrieve product ID from Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        String productName = intent.getStringExtra("productName");
        String productPrice = intent.getStringExtra("productPrice");
        String productImg = intent.getStringExtra("productImg");

        TextView productNameTextView = findViewById(R.id.productName);
        productNameTextView.setText(productName);


        TextView productPriceTextView = findViewById(R.id.productPrice);
        productPriceTextView.setText(productPrice);

        ImageView productImageView = findViewById(R.id.productImg);
        Glide.with(FollowOrderActivity.this)
                .load(productImg)
                .placeholder(R.drawable.placeholder_img)
                .into(productImageView);

//        // Initialize Firebase Database reference for products
//        cartItemsRef = FirebaseDatabase.getInstance().getReference().child("cartItems");
//
//        Query query = cartItemsRef.orderByChild("productId").equalTo(productId);
//
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String productName = userSnapshot.child("name").getValue(String.class);
//                    String productPrice = userSnapshot.child("price").getValue(String.class);
//                    String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);
//
//                    TextView productNameTextView = findViewById(R.id.productName);
//                    productNameTextView.setText(productName);
//
//
//                    TextView productPriceTextView = findViewById(R.id.productPrice);
//                    productPriceTextView.setText(productPrice);
//
//                    ImageView productImageView = findViewById(R.id.productImg);
//                    Glide.with(FollowOrderActivity.this)
//                            .load(imageUrl)
//                            .placeholder(R.drawable.placeholder_img)
//                            .into(productImageView);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle database errors
//            }
//        });

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowOrderActivity.this, OrderManagerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
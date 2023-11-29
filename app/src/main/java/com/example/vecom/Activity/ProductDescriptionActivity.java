package com.example.vecom.Activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProductDescriptionActivity extends AppCompatActivity {
    private DatabaseReference productsRef;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        // Retrieve product ID from Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");

        // Initialize Firebase Database reference for products
        productsRef = FirebaseDatabase.getInstance().getReference().child("products");

        Query query = productsRef.orderByChild("productId").equalTo(productId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String productName = userSnapshot.child("name").getValue(String.class);
                    String productDesc = userSnapshot.child("desc").getValue(String.class);
                    String productPrice = userSnapshot.child("formattedPrice").getValue(String.class);
                    String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);

                    TextView productNameTextView = findViewById(R.id.productName);
                    productNameTextView.setText(productName);

                    TextView productDescTextView = findViewById(R.id.descriptionText);
                    productDescTextView.setText(productDesc);

                    TextView productPriceTextView = findViewById(R.id.price);
                    productPriceTextView.setText(productPrice);

                    ImageView productImageView = findViewById(R.id.productImg1);
                    Glide.with(ProductDescriptionActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.placeholder_img)
                            .into(productImageView);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
            }
        });

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RelativeLayout addToCartBtn = findViewById(R.id.addToCartBtn);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        TextView damphangia = findViewById(R.id.damphangiaText);
        damphangia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDamPhanDialog();
            }
        });
    }
    private void showDamPhanDialog() {
        // Your dialog code here
    }
}

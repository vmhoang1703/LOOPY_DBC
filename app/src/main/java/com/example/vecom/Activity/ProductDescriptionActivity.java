package com.example.vecom.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vecom.Model.CardItem;
import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductDescriptionActivity extends AppCompatActivity {
    private DatabaseReference productsRef;
    private static final String TAG = "";
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Uri imageUri;
    StorageReference storageReference;
    public String imageUrl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);


        // Retrieve product ID from Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");


        // Initialize Firebase Database reference for products
        productsRef = FirebaseDatabase.getInstance().getReference().child("products");
        databaseReference = FirebaseDatabase.getInstance().getReference("cardItems");
        storageReference = FirebaseStorage.getInstance().getReference("images");

        Query query = productsRef.orderByChild("productId").equalTo(productId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String productName = userSnapshot.child("name").getValue(String.class);
                    String productDesc = userSnapshot.child("desc").getValue(String.class);
                    String productPrice = userSnapshot.child("formattedPrice").getValue(String.class);
                    String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);
                    imageUrl1 = imageUrl;
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
                addToCart();
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

    private void addToCart() {
        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Lấy thông tin sản phẩm từ Firebase Realtime Database
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            TextView productNameEditText = findViewById(R.id.productName);
            TextView priceEditText = findViewById(R.id.price);

            String productName = productNameEditText.getText().toString();
            double price = priceEditText.getText().toString().isEmpty() ? 0 : Double.parseDouble(priceEditText.getText().toString());

            // Create a Product object with only non-image information
            CardItem newCardItem = new CardItem(productId, productName, price, imageUrl1, userEmail);

            // Push the non-image information to Realtime Database
            DatabaseReference cardItemRef = databaseReference.push();
            cardItemRef.setValue(newCardItem);

        }
        Intent cartIntent = new Intent(ProductDescriptionActivity.this, AddToCartActivity.class);
        startActivity(cartIntent);
    }
}




package com.example.vecom.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vecom.Adapter.ProductAdapter;
import com.example.vecom.Adapter.ProductAdapter1;
import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductDescriptionActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private DatabaseReference productsRef;
    private List<Product> productList;
    private List<Product> productList1;
    private ProductAdapter productAdapter;
    private ProductAdapter1 productAdapter1;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);


        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView1 = findViewById(R.id.recyclerView);
        productList1 = new ArrayList<>();
        productAdapter1 = new ProductAdapter1(this, productList1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(gridLayoutManager);
        recyclerView1.setAdapter(productAdapter1);
        createProductItems1();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
//
//        String productId = getIntent().getStringExtra("productId");
//
//        databaseReference.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Product product = dataSnapshot.getValue(Product.class);
//                    if (product != null) {
//                        updateProductViews(product);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error
//            }
//        });

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

//    private void updateProductViews(Product product) {
//        TextView productName = findViewById(R.id.productName);
//        TextView descriptionText = findViewById(R.id.descriptionText);
//        TextView price = findViewById(R.id.price);
//        ImageView imageViewImg = findViewById(R.id.productImg1);
//
//        productName.setText(product.getName());
//        descriptionText.setText(product.getDesc());
//        price.setText(product.getFormattedPrice());
//
//        // Load image using Glide or another image loading library
//        Glide.with(this).load(product.getImageUrl()).into(imageViewImg);
//    }
private void createProductItems1() {
    productsRef = FirebaseDatabase.getInstance().getReference("products");

    productsRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                Product product = productSnapshot.getValue(Product.class);

                try {
                    product = productSnapshot.getValue(Product.class);

                    if (product != null) {
                        productList1.add(product);
                    }
                } catch (Exception e) {
                    Log.e("Firebase", "Error converting to Product", e);
                }
            }

            // Update the product adapter
            productAdapter1.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Firebase", "Failed to read value.", error.toException());
        }
    });
}

    private void showDamPhanDialog() {
        // Your dialog code here
    }
}

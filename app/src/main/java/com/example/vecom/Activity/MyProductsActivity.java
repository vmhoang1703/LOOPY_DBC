package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.vecom.Adapter.MyProductAdapter;


import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyProductsActivity extends AppCompatActivity {


    private DatabaseReference userReference;

    private DatabaseReference productsRef;
    private List<Product> productList;

    private MyProductAdapter myProductAdapter;

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProductsActivity.this, PersonalStoreActivity.class);
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(backArrow, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView); // Ánh xạ RecyclerView từ layout
        productList = new ArrayList<>(); // Initialize the product list
        myProductAdapter = new MyProductAdapter(this, productList); // Initialize the product adapter with context
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myProductAdapter); // Set the product adapter to RecyclerView


        // Create product items from Firebase
        createProductItems();

        RelativeLayout addProductBtn = findViewById(R.id.addProductBtn);
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProductsActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createProductItems() {
        productsRef = FirebaseDatabase.getInstance().getReference("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear(); // Xóa danh sách sản phẩm hiện tại
                String userEmail = currentUser.getEmail();
                userReference = FirebaseDatabase.getInstance().getReference().child("users");

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    try {
                        product = productSnapshot.getValue(Product.class);

                        // Kiểm tra nếu sản phẩm thuộc về tài khoản đăng nhập, thêm vào danh sách
                        if (product != null && product.getUserEmail().equals(userEmail)) {
                            productList.add(product);
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error converting to Product", e);
                    }
                }

                // Kiểm tra nếu danh sách sản phẩm rỗng thì hiển thị no_myproduct, ngược lại hiển thị RecyclerView
                if (productList.isEmpty()) {
                    findViewById(R.id.recyclerView).setVisibility(View.GONE);
                    findViewById(R.id.no_myproduct).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                    findViewById(R.id.no_myproduct).setVisibility(View.GONE);
                    // Update the product adapter
                    myProductAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
}
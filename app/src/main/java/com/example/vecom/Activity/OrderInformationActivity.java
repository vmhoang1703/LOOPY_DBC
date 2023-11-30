package com.example.vecom.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vecom.Adapter.OrderInformationAdapter;
import com.example.vecom.Model.CardItem;
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

public class OrderInformationActivity extends AppCompatActivity {
    private View continuePaymentBtn;
    private List<Product> cartList;
    private LinearLayout emptyCartText;
    private DatabaseReference userReference;
    private DatabaseReference productsRef;
    private List<CardItem> productList;

    private OrderInformationAdapter OrderInformationAdapter;

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private double totalCartPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderInformationActivity.this, AddToCartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.cartListView); // Ánh xạ RecyclerView từ layout
        productList = new ArrayList<>(); // Initialize the product list
        OrderInformationAdapter = new OrderInformationAdapter(this, productList); // Initialize the product adapter with context
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(OrderInformationAdapter); // Set the product adapter to RecyclerView

        createProductItems();


        continuePaymentBtn = findViewById(R.id.continuePaymentBtn);

        continuePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInformationActivity.this, PaymentOptionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createProductItems() {
        productsRef = FirebaseDatabase.getInstance().getReference("cardItems");
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear(); // Xóa danh sách sản phẩm hiện tại
                String userEmail = currentUser.getEmail();
                userReference = FirebaseDatabase.getInstance().getReference().child("users");

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    try {
                        CardItem cardItem = productSnapshot.getValue(CardItem.class);

                        if (cardItem != null && cardItem.getUserEmail().equals(userEmail)) {
                            productList.add(cardItem);

                            // Cập nhật tổng giá mỗi khi thêm một CardItem
                            totalCartPrice += cardItem.getPrice();
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error converting to CardItem", e);
                    }
                }
                TextView priceTextView = findViewById(R.id.price);
                priceTextView.setText(String.valueOf(totalCartPrice));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
}
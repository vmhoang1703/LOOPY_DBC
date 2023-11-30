package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.vecom.Adapter.OrderManagerAdapter;
import com.example.vecom.Model.CardItem;
import com.example.vecom.Model.Order;
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

public class OrderManagerActivity extends AppCompatActivity {
    private List<Order> orderList;
    private ListView orderListView;
    private LinearLayout emptyOrderText;
    private DatabaseReference userReference;
    private DatabaseReference productsRef;
    private List<CardItem> productList;

    private OrderManagerAdapter orderManagerAdapter;

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, PersonalActivity1.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.cartListView); // Ánh xạ RecyclerView từ layout
        productList = new ArrayList<>(); // Initialize the product list
        orderManagerAdapter = new OrderManagerAdapter(this, productList); // Initialize the product adapter with context
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(orderManagerAdapter); // Set the product adapter to RecyclerView

        createOrderItems();


//        orderListView = findViewById(R.id.orderListView);
//        emptyOrderText = findViewById(R.id.noOrderLayout);
//
//        orderList = new ArrayList<>();
//        // Thêm sản phẩm vào giỏ hàng
//        orderList.add(new Order("Bàn phím cơ EK87", 299.000, R.drawable.product_test, 1, "Đang vận chuyển"));
//
//        updateOrderView();
//
//        // Xử lý sự kiện khi giỏ hàng trống
//        if (orderList.isEmpty()) {
//            orderListView.setVisibility(View.GONE);
//            emptyOrderText.setVisibility(View.VISIBLE);
//        }
    }
    private void createOrderItems() {
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
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error converting to CardItem", e);
                    }
                }

                if (productList.isEmpty()) {
                    findViewById(R.id.cartListView).setVisibility(View.GONE);
                    findViewById(R.id.noOrderLayout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.cartListView).setVisibility(View.VISIBLE);
                    findViewById(R.id.noOrderLayout).setVisibility(View.GONE);
                    // Update the product adapter
                    orderManagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

}
package com.example.vecom.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vecom.Adapter.AddToCardAdapter;


import com.example.vecom.Adapter.MyProductAdapter;
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

public class AddToCartActivity extends AppCompatActivity {
    private View paymentBtn;
    private LinearLayout emptyCartText;
    private DatabaseReference userReference;

    private DatabaseReference productsRef;
    private List<CardItem> productList;

    private AddToCardAdapter addToCardAdapter;

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private int totalCartPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCartActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCartActivity.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCartActivity.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCartActivity.this, PersonalActivity1.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.cartListView); // Ánh xạ RecyclerView từ layout
        productList = new ArrayList<>(); // Initialize the product list
        addToCardAdapter = new AddToCardAdapter(this, productList); // Initialize the product adapter with context
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(addToCardAdapter); // Set the product adapter to RecyclerView

        createProductItems();

        // Khởi tạo paymentBtn
        paymentBtn = findViewById(R.id.paymentBtn);

        // Thêm sự kiện click cho paymentBtn
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý khi button được nhấn
                Intent intent = new Intent(AddToCartActivity.this, OrderInformationActivity.class);
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(paymentBtn, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

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

                // Kiểm tra nếu danh sách sản phẩm rỗng thì hiển thị no_myproduct, ngược lại hiển thị RecyclerView
                if (productList.isEmpty()) {
                    findViewById(R.id.cartListView).setVisibility(View.GONE);
                    findViewById(R.id.noAddToCartLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.paymentBtn).setVisibility(View.GONE);
                    findViewById(R.id.totalBox).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.cartListView).setVisibility(View.VISIBLE);
                    findViewById(R.id.noAddToCartLayout).setVisibility(View.GONE);
                    findViewById(R.id.paymentBtn).setVisibility(View.VISIBLE);
                    findViewById(R.id.totalBox).setVisibility(View.VISIBLE);
                    // Update the product adapter
                    addToCardAdapter.notifyDataSetChanged();
                    TextView priceTextView = findViewById(R.id.price);
                    priceTextView.setText(String.valueOf(totalCartPrice));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
}
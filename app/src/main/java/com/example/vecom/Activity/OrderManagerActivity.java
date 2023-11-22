package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.vecom.Adapter.OrderManagementAdapter;
import com.example.vecom.Model.Order;
import com.example.vecom.R;

import java.util.List;

public class OrderManagerActivity extends AppCompatActivity {
    private List<Order> orderList;
    private ListView orderListView;
    private LinearLayout emptyOrderText;
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
                Intent intent = new Intent(OrderManagerActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout followBtn = findViewById(R.id.followBtn);
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManagerActivity.this, FollowOrderActivity.class);
                startActivity(intent);
            }
        });

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
    private void updateOrderView() {
        OrderManagementAdapter orderManagementAdapter = new OrderManagementAdapter(this, orderList);
        orderListView.setAdapter(orderManagementAdapter);
    }

}
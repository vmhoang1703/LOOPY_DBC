package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.vecom.Adapter.OrderItemsAdapter;
import com.example.vecom.Model.Product;
import com.example.vecom.R;

import java.util.ArrayList;
import java.util.List;

public class OrderInformationActivity extends AppCompatActivity {
    private View continuePaymentBtn;
    private List<Product> cartList;
    private ListView cartListView;
    private LinearLayout emptyCartText;
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

        cartListView = findViewById(R.id.cartListView);

        cartList = new ArrayList<>();
        // Thêm sản phẩm vào giỏ hàng
        cartList.add(new Product("Bàn phím cơ EK87", 299.000, desc, rate, quantity, cmt, R.drawable.product_test, productType));

        updateCartView();


        continuePaymentBtn = findViewById(R.id.continuePaymentBtn);

        continuePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInformationActivity.this, PaymentOptionsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateCartView() {
        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(this, cartList);
        cartListView.setAdapter(orderItemsAdapter);
    }
}
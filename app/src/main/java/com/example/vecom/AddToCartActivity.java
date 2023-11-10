package com.example.vecom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vecom.Adapter.CartAdapter;

import com.example.vecom.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class AddToCartActivity extends AppCompatActivity {
    private View paymentBtn;
    private List<Product> cartList;
    private ListView cartListView;
    private LinearLayout emptyCartText;

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
                Intent intent = new Intent(AddToCartActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        cartListView = findViewById(R.id.cartListView);
        emptyCartText = findViewById(R.id.noAddToCartLayout);

        cartList = new ArrayList<>();
        // Thêm sản phẩm vào giỏ hàng
        cartList.add(new Product("Bàn phím cơ EK87", 299.000, R.drawable.product_test));
//        cartList.add(new Product("Bàn phím cơ EK87", 299.000, R.drawable.product_test));
//        cartList.add(new Product("Bàn phím cơ EK87", 299.000, R.drawable.product_test));
//        cartList.add(new Product("Bàn phím cơ EK87", 299.000, R.drawable.product_test));

        updateCartView();

        // Xử lý sự kiện khi giỏ hàng trống
        if (cartList.isEmpty()) {
            cartListView.setVisibility(View.GONE);
            emptyCartText.setVisibility(View.VISIBLE);
        }

        // Khởi tạo paymentBtn
        paymentBtn = findViewById(R.id.paymentBtn);

        // Thêm sự kiện click cho paymentBtn
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý khi button được nhấn
                Intent intent = new Intent(AddToCartActivity.this, OrderInformationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateCartView() {
        CartAdapter cartAdapter = new CartAdapter(this, cartList);
        cartListView.setAdapter(cartAdapter);
    }
}
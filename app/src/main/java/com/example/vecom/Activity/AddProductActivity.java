package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ImageView backArrow = findViewById(R.id.back);

        TextView showBtn = findViewById(R.id.showBtn);
        TextView saveBtn = findViewById(R.id.saveBtn);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, MyProductsActivity.class);
                startActivity(intent);
                finish();
            }


        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayProductInformation();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProductToDatabase();
            }
        });
    }
    private void displayProductInformation() {
        EditText productNameEditText = findViewById(R.id.edit_addProductName);
        String productName = productNameEditText.getText().toString();
        Toast.makeText(this, "Tên sản phẩm: " + productName, Toast.LENGTH_SHORT).show();
    }

    private void saveProductToDatabase() {
        EditText productNameEditText = findViewById(R.id.edit_addProductName);
        EditText productDescriptionEditText = findViewById(R.id.edit_addProductDesc);
        EditText priceEditText = findViewById(R.id.edit_addProductPrice);
        EditText stockEditText = findViewById(R.id.edit_addProductQuantity);


        String productName = productNameEditText.getText().toString();
        String productDescription = productDescriptionEditText.getText().toString();

        // Lấy giá từ EditText, kiểm tra null để tránh lỗi khi parse
        double price = 0.0;
        if (!priceEditText.getText().toString().isEmpty()) {
            price = Double.parseDouble(priceEditText.getText().toString());
        }

        // Lấy số lượng từ EditText, kiểm tra null để tránh lỗi khi parse
        int stock = 0;
        if (!stockEditText.getText().toString().isEmpty()) {
            stock = Integer.parseInt(stockEditText.getText().toString());
        }


        // Tạo đối tượng Product với các giá trị lấy từ giao diện
        Product newProduct = new Product(productName, price, productDescription, 0.0, stock, "", R.drawable.product_test, "");

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("products");
        productsRef.push().setValue(newProduct);

        // Hiển thị thông báo hoặc thực hiện các thao tác khác nếu cần
        Toast.makeText(this, "Sản phẩm đã được lưu vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
    }

}
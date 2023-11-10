package com.example.vecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_store);

        TextView myProduct = findViewById(R.id.sanphamText);
        myProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalStoreActivity.this, MyProductsActivity.class);
                startActivity(intent);
            }
        });
        ImageView myProduct1 = findViewById(R.id.sanphamArrow);
        myProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalStoreActivity.this, MyProductsActivity.class);
                startActivity(intent);
            }
        });
    }
}
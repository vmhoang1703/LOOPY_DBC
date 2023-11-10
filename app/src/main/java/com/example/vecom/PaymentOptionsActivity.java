package com.example.vecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PaymentOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentOptionsActivity.this, OrderInformationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RelativeLayout confirmPaymentBtn = findViewById(R.id.confirmPaymentBtn);
        confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentOptionsActivity.this, PINHandlerActivity.class);
                startActivity(intent);
            }
        });
    }
}
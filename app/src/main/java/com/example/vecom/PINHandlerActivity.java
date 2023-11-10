package com.example.vecom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PINHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhandler);

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PINHandlerActivity.this, PaymentOptionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RelativeLayout continuePayment = findViewById(R.id.continueBtn);
        continuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the second dialog
                Dialog successDialog = new Dialog(PINHandlerActivity.this);

                // Set up the second dialog window with MATCH_PARENT width
                Window secondWindow = successDialog.getWindow();
                if (secondWindow != null) {
                    secondWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                }

                successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
                successDialog.setContentView(R.layout.popup_dathangthanhcong); // Your custom layout
                successDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the background

                // Show the second dialog
                successDialog.show();

                TextView xemDonHangBtn = successDialog.findViewById(R.id.xemdonhangBtn);
                xemDonHangBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PINHandlerActivity.this, OrderManagerActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
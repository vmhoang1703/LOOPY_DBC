package com.example.vecom.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vecom.R;

public class SuccessfulOrderPopupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_dathangthanhcong);

        TextView xemDonHangBtn = findViewById(R.id.xemdonhangBtn);
        xemDonHangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessfulOrderPopupActivity.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });
    }
}

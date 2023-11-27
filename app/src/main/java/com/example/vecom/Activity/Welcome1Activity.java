package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.vecom.R;

public class Welcome1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome1);

        RelativeLayout continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Welcome1Activity.this, Welcome2Activity.class);
            startActivity(intent);
        });
    }
}
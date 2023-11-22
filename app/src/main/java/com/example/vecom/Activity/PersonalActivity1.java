package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.vecom.R;

public class PersonalActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal1);

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout forumNavi = findViewById(R.id.forumNavi);
        forumNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, ForumActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, PersonalActivity1.class);
                startActivity(intent);
            }
        });

        RelativeLayout workAccount = findViewById(R.id.workAccount);
        workAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, PersonalStoreActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity1.this, PersonalActivity2.class);
                startActivity(intent);
            }
        });
    }
}
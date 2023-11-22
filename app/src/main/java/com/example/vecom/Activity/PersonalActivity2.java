package com.example.vecom.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vecom.R;

public class PersonalActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal2);

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity2.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity2.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout forumNavi = findViewById(R.id.forumNavi);
        forumNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity2.this, ForumActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity2.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity2.this, PersonalActivity1.class);
                startActivity(intent);
            }
        });

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity2.this, PersonalActivity1.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;

public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout workAccount = findViewById(R.id.workAccount);
        workAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, PersonalStoreActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout communitySpacing = findViewById(R.id.communitySapcing);
        communitySpacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, ForumActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
        logoutBtn.setVisibility(View.GONE);
    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        // Điều hướng người dùng đến màn hình đăng nhập hoặc màn hình khác theo nhu cầu của bạn.
        Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Đảm bảo người dùng không thể quay lại màn hình PersonalActivity bằng nút back.
    }

}
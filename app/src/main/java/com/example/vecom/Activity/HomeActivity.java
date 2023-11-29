package com.example.vecom.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.vecom.Adapter.ImageSliderAdapter;
import com.example.vecom.Adapter.ProductAdapter;
import com.example.vecom.Adapter.ProductAdapter1;
import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPagerAds;
    private int[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    private int currentPage = 0;
    private Timer timer;

    private DatabaseReference userReference;
    private FirebaseAuth mAuth;

    private DatabaseReference productsRef;
    private List<Product> productList;
    private List<Product> productList1;
    private ProductAdapter productAdapter;
    private ProductAdapter1 productAdapter1;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Assuming you have a node in your database named "users" where emails are stored
            userReference = FirebaseDatabase.getInstance().getReference().child("users");

            // Query the database based on email
            Query query = userReference.orderByChild("userEmail").equalTo(userEmail);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String username = userSnapshot.child("userName").getValue(String.class);

                        // Set the username and phone number in TextViews
                        TextView userNameTextView = findViewById(R.id.userName);
                        userNameTextView.setText(username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        viewPagerAds = findViewById(R.id.viewPagerAds);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        viewPagerAds.setAdapter(adapter);

        // Tạo chỉ mục (indicator)
        addIndicator(images.length);

        // Tự động chuyển slider sau mỗi 5 giây
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPagerAds.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 5000); // 5 giây delay và chuyển sau mỗi 5 giây

        recyclerView = findViewById(R.id.recyclerView); // Ánh xạ RecyclerView từ layout
        productList = new ArrayList<>(); // Initialize the product list
        productAdapter = new ProductAdapter(this, productList); // Initialize the product adapter with context
        recyclerView.setAdapter(productAdapter); // Set the product adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create product items from Firebase
        createProductItems();

        recyclerView1 = findViewById(R.id.recyclerView1);
        productList1 = new ArrayList<>();
        productAdapter1 = new ProductAdapter1(this, productList1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(gridLayoutManager);
        recyclerView1.setAdapter(productAdapter1);
        createProductItems1();

        ImageView customIcon = findViewById(R.id.customIcon);
        customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the second dialog
                Dialog filterDialog = new Dialog(HomeActivity.this);

                // Set up the second dialog window with MATCH_PARENT width
                Window secondWindow = filterDialog.getWindow();
                if (secondWindow != null) {
                    secondWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                }

                filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
                filterDialog.setContentView(R.layout.popup_filter); // Your custom layout
                filterDialog.getWindow().setBackgroundDrawableResource(R.drawable.filter_bg); // Apply the background

                // Show the second dialog
                filterDialog.show();
            }
        });

        RelativeLayout cartNavi = findViewById(R.id.cartNavi);
        cartNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout forumNavi = findViewById(R.id.forumNavi);
        forumNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout orderNavi = findViewById(R.id.orderNavi);
        orderNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout profileNavi = findViewById(R.id.profileNavi);
        profileNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonalActivity1.class);
                startActivity(intent);
            }
        });
    }

    private void createProductItems() {
        productsRef = FirebaseDatabase.getInstance().getReference("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    try {
                         product = productSnapshot.getValue(Product.class);

                        if (product != null) {
                            productList.add(product);
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error converting to Product", e);
                    }
                }

                // Update the product adapter
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
    private void createProductItems1() {
        productsRef = FirebaseDatabase.getInstance().getReference("products");

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    try {
                        product = productSnapshot.getValue(Product.class);

                        if (product != null) {
                            productList1.add(product);
                        }
                    } catch (Exception e) {
                        Log.e("Firebase", "Error converting to Product", e);
                    }
                }

                // Update the product adapter
                productAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

    private void addIndicator(int count) {
        LinearLayout indicatorLayout = findViewById(R.id.indicatorLayout);
        ImageView[] indicators = new ImageView[count];

        for (int i = 0; i < count; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageResource(R.drawable.point_notactive);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(8, 0, 8, 0);
            indicatorLayout.addView(indicators[i], layoutParams);
        }

        updateIndicator(0);
        viewPagerAds.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void updateIndicator(int position) {
        LinearLayout indicatorLayout = findViewById(R.id.indicatorLayout);
        int childCount = indicatorLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            ImageView indicator = (ImageView) indicatorLayout.getChildAt(i);
            indicator.setImageResource((i == position) ?
                    R.drawable.point_active : R.drawable.point_notactive);
        }
    }
}

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.vecom.Adapter.CardSliderAdapter;
import com.example.vecom.Adapter.ImageSliderAdapter;
import com.example.vecom.Model.CardItem;
import com.example.vecom.R;
import com.example.vecom.Adapter.ProductAdapter;
import com.example.vecom.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private List<CardItem> cardItems; // Khai báo danh sách cardItems
    private List<CardItem> cardItems1;

    private DatabaseReference productsRef;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        // Khởi tạo Firebase
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        productsRef = firebaseDatabase.getReference("products");
//
//        // Khởi tạo RecyclerView
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        productList = new ArrayList<>();
//        productAdapter = new ProductAdapter(productList);
//        recyclerView.setAdapter(productAdapter);
//
//        productsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                productList.clear();
//
//                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
//                    Product product = productSnapshot.getValue(Product.class);
//                    if (product != null) {
//                        productList.add(product);
//                    }
//                }
//
//                productAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "Failed to read value.", error.toException());
//            }
//        });





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

        ViewPager viewPager = findViewById(R.id.viewPager); // Ánh xạ ViewPager từ layout
        cardItems = createCardItems(); // Tạo danh sách card giả lập
        CardSliderAdapter cardSliderAdapter = new CardSliderAdapter(this, cardItems); // Sử dụng dữ liệu cardItems
        viewPager.setAdapter(cardSliderAdapter); // Gắn Adapter vào ViewPager

//        ViewPager viewPager1 = findViewById(R.id.viewPager1); // Ánh xạ ViewPager từ layout
//        cardItems1 = createCardItems(); // Tạo danh sách card giả lập
//        CardSliderAdapter cardSliderAdapter1 = new CardSliderAdapter(this, cardItems1); // Sử dụng dữ liệu cardItems
//        viewPager1.setAdapter(cardSliderAdapter); // Gắn Adapter vào ViewPager

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

//        viewPager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ProductDescription.class);
//                startActivity(intent);
//            }
//        });

        RelativeLayout homeNavi = findViewById(R.id.homeNavi);
        homeNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<CardItem> createCardItems() {
        // Tạo danh sách card giả lập
        List<CardItem> cardItems = new ArrayList<>();
        cardItems.add(new CardItem(R.drawable.product_test, "Bàn phím cơ Dareu EK87", R.string.description_text, 100.0));
//        cardItems.add(new CardItem(R.drawable.maclenin, "Bộ giáo trình Triết học", R.string.descriptionMaclenin, 30.0));
//        cardItems.add(new CardItem(R.drawable.balo, "Balo Kim Long KL024", R.string.descriptionBalo, 299.0));
//        cardItems.add(new CardItem(R.drawable.tugiay, "Tủ đựng giày gỗ Lương Sơn", R.string.descriptionTugiay, 199.0));
//        cardItems.add(new CardItem(R.drawable.tulanh, "Tủ lạnh Sharp chính hãng", R.string.descriptionTulanh, 1099.0));
//        cardItems.add(new CardItem(R.drawable.laptop, "Laptop cũ Dell inspiron 15R", R.string.descriptionLaptop, 5000.0));

        // Thêm các CardItem khác vào danh sách ở đây
        return cardItems;
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

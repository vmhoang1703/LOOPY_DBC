package com.example.vecom.Activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
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
    private int[] images = {R.drawable.slide4, R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
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
    private View searchBar;
    private View notiIcon;
    private View topBg;
    private int defaultProductCardColor;

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

//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                        String username = userSnapshot.child("userName").getValue(String.class);
//
//                        // Set the username and phone number in TextViews
//                        TextView userNameTextView = findViewById(R.id.userName);
//                        userNameTextView.setText(username);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle errors
//                }
//            });
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

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Handle the touch event when a product card is clicked
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        onProductCardClicked(position);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        recyclerView1 = findViewById(R.id.recyclerView1);
        productList1 = new ArrayList<>();
        productAdapter1 = new ProductAdapter1(this, productList1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(gridLayoutManager);
        recyclerView1.setAdapter(productAdapter1);


        recyclerView1.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                // Handle the touch event when a product card is clicked
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        onProductCard1Clicked(position);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        createProductItems1();

        ImageView customIcon = findViewById(R.id.customIcon);
        customIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View overlayView = findViewById(R.id.overlayView);
                overlayView.setVisibility(View.VISIBLE);
                // Create the first dialog
                Dialog filterDialog = new Dialog(HomeActivity.this);
                filterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
                filterDialog.setContentView(R.layout.popup_filter); // Your custom layout
                filterDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the rounded background

                // Lấy reference của content view của dialog
                View dialogView = filterDialog.findViewById(android.R.id.content);

                // Đặt sự kiện cho overlay view để ẩn khi chạm bên ngoài dialog
                overlayView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Lấy tọa độ của sự kiện chạm
                        float x = event.getX();
                        float y = event.getY();

                        // Lấy tọa độ tương đối của dialogView trong overlayView
                        int[] location = new int[2];
                        dialogView.getLocationOnScreen(location);
                        int dialogX = location[0];
                        int dialogY = location[1];

                        // Kiểm tra xem sự kiện chạm có xảy ra bên ngoài dialog hay không
                        if (x < dialogX || x > dialogX + dialogView.getWidth() ||
                                y < dialogY || y > dialogY + dialogView.getHeight()) {
                            // Ẩn overlay khi chạm bên ngoài dialog
                            overlayView.setVisibility(View.GONE);

                            // Đóng dialog khi chạm bên ngoài (tùy theo yêu cầu của bạn)
                            filterDialog.dismiss();
                        }
                        return false;
                    }
                });
                filterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // Ẩn overlay khi dialog đóng
                        overlayView.setVisibility(View.GONE);
                    }
                });
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

//        RelativeLayout forumNavi = findViewById(R.id.forumNavi);
//        forumNavi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
//                startActivity(intent);
//            }
//        });

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

        RelativeLayout don1dUti = findViewById(R.id.don1dUti);
        don1dUti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Deal1kActivity.class);
                applyButtonClickEffect(don1dUti);
                startActivity(intent);
            }
        });

        RelativeLayout forumUti = findViewById(R.id.forumUti);
        forumUti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ForumActivity.class);
                applyButtonClickEffect(forumUti);
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

    private void onProductCardClicked(int position) {
        // Add scale animation when clicking on a product card
        View productCard = recyclerView.getChildAt(position);

        if (productCard != null) {
            scaleAnimation(productCard);
            changeColorOnSelection(productCard);
        }
        // Handle other actions when a product card is clicked (if needed)
    }

    private void onProductCard1Clicked(int position) {
        // Add scale animation when clicking on a product card
        View productCard1 = recyclerView1.getChildAt(position);

        if (productCard1 != null) {
            scaleAnimation(productCard1);
            changeColorOnSelection(productCard1);
        }
        // Handle other actions when a product card is clicked (if needed)
    }
    private void scaleAnimation(View view) {
        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f);
        scaleAnimatorX.setDuration(800); // You can adjust the duration as needed
        scaleAnimatorX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f);
        scaleAnimatorY.setDuration(800); // You can adjust the duration as needed
        scaleAnimatorY.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleAnimatorX.start();
        scaleAnimatorY.start();
    }

    private void changeColorOnSelection(View view) {
        ColorStateList originalColor = view.getBackgroundTintList();
        // Change the background color of the selected product card
        view.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bg_des)));

        // Reset the background color after a delay (you can adjust the delay as needed)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundTintList(originalColor);
            }
        }, 500);  // 500 milliseconds delay
    }

    private void applyButtonClickEffect(View view) {
        view.animate()
                .scaleX(0.9f)
                .scaleY(0.9f)
                .alpha(0.7f)
                .setDuration(100)
                .withEndAction(() -> {
                    // Khôi phục trạng thái ban đầu khi kết thúc animation
                    view.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .alpha(1.0f)
                            .setDuration(100)
                            .start();
                })
                .start();
    }
}

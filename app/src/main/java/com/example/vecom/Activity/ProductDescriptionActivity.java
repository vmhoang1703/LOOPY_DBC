package com.example.vecom.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.vecom.Model.CardItem;
import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProductDescriptionActivity extends AppCompatActivity {
    private DatabaseReference productsRef;
    private static final String TAG = "";
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Uri imageUri;
    StorageReference storageReference;
    public String imageUrl1;
    public int pricePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);


        // Retrieve product ID from Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");


        // Initialize Firebase Database reference for products
        productsRef = FirebaseDatabase.getInstance().getReference().child("products");
        databaseReference = FirebaseDatabase.getInstance().getReference("cardItems");
        storageReference = FirebaseStorage.getInstance().getReference("images");

        Query query = productsRef.orderByChild("productId").equalTo(productId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String productName = userSnapshot.child("name").getValue(String.class);
                    String productDesc = userSnapshot.child("desc").getValue(String.class);
                    int productPrice = userSnapshot.child("price").getValue(Integer.class);
                    String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);
                    imageUrl1 = imageUrl;
                    pricePopup = productPrice;
                    TextView productNameTextView = findViewById(R.id.productName);
                    productNameTextView.setText(productName);

                    TextView productDescTextView = findViewById(R.id.descriptionText);
                    productDescTextView.setText(productDesc);

                    TextView productPriceTextView = findViewById(R.id.price);
                    productPriceTextView.setText(String.valueOf(productPrice));

                    ImageView productImageView = findViewById(R.id.productImg1);
                    Glide.with(ProductDescriptionActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.placeholder_img)
                            .into(productImageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
            }
        });

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RelativeLayout addToCartBtn = findViewById(R.id.addToCartBtn);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        TextView damphangia = findViewById(R.id.damphangiaText);
        damphangia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDamPhanDialog();
            }
        });
    }

    private void showDamPhanDialog() {
        // Lấy reference của overlay view
        View overlayView = findViewById(R.id.overlayView);
        overlayView.setVisibility(View.VISIBLE);

        // Create the first dialog
        Dialog firstDialog = new Dialog(ProductDescriptionActivity.this);
        firstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
        firstDialog.setContentView(R.layout.popup_damphan); // Your custom layout
        firstDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the rounded background

        TextView curentPrice = firstDialog.findViewById(R.id.currentPrice);
        curentPrice.setText(String.valueOf(pricePopup));

        // Lấy reference của content view của dialog
        View dialogView = firstDialog.findViewById(android.R.id.content);

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
                    firstDialog.dismiss();
                }

                return false;
            }
        });

        // Handle the "Đàm phán" button in the first dialog
        TextView damPhanButton = firstDialog.findViewById(R.id.damphanBtn);
        EditText damPhanEditText = firstDialog.findViewById(R.id.damphanEditText);
        damPhanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered negotiation price
                String damPhanPriceStr = damPhanEditText.getText().toString().trim();

                // Validate if the negotiation price is entered and greater than or equal to 2/3 of the current price
                if (!damPhanPriceStr.isEmpty()) {
                    int damPhanPrice = Integer.parseInt(damPhanPriceStr);
                    int currentPrice = pricePopup;

                    if (damPhanPrice >= currentPrice * 2 / 3) {
                        // Continue with your logic for the negotiation

                        // Dismiss the first dialog
                        firstDialog.dismiss();
                        // Delay the showing of the second dialog to avoid conflicts
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Show the second dialog
                                showSecondDialog();
                            }
                        }, 100);
                    } else {
                        // Show an error message indicating that the negotiation price is too low
                        Toast.makeText(ProductDescriptionActivity.this, "Negotiation price must be at least 2/3 of the current price", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show an error message indicating that the negotiation price is required
                    Toast.makeText(ProductDescriptionActivity.this, "Please enter negotiation price", Toast.LENGTH_SHORT).show();
                }
            }
        });
        firstDialog.show();
    }

    // Function to show the second dialog
    private void showSecondDialog() {
        // Lấy reference của overlay view
        View overlayView = findViewById(R.id.overlayView);
        overlayView.setVisibility(View.VISIBLE);

        // Create the second dialog
        Dialog secondDialog = new Dialog(ProductDescriptionActivity.this);
        secondDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
        secondDialog.setContentView(R.layout.popup_damphanyeucau); // Your custom layout
        secondDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the background

        // Lấy reference của content view của dialog
        View dialogView = secondDialog.findViewById(android.R.id.content);

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
                    secondDialog.dismiss();
                }

                return false;
            }
        });

        // Đặt sự kiện cho sự kiện đóng dialog để ẩn overlay khi dialog đóng
        secondDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Ẩn overlay khi dialog đóng
                overlayView.setVisibility(View.GONE);
            }
        });

        // Show the second dialog
        secondDialog.show();
    }

    private void addToCart() {
        // Lấy thông tin từ Intent
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Lấy thông tin sản phẩm từ Firebase Realtime Database
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            TextView productNameEditText = findViewById(R.id.productName);
            TextView priceEditText = findViewById(R.id.price);

            String productName = productNameEditText.getText().toString();
            int price = priceEditText.getText().toString().isEmpty() ? 0 : Integer.parseInt(priceEditText.getText().toString());

            // Create a Product object with only non-image information
            CardItem newCardItem = new CardItem(productId, productName, price, imageUrl1, userEmail);

            // Push the non-image information to Realtime Database
            DatabaseReference cardItemRef = databaseReference.push();
            cardItemRef.setValue(newCardItem);

        }
        Intent cartIntent = new Intent(ProductDescriptionActivity.this, AddToCartActivity.class);
        startActivity(cartIntent);
    }
}




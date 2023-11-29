package com.example.vecom.Activity;

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

import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDescriptionActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productId")) {
            productId = intent.getStringExtra("productId");
            loadProductData();
        }

        RelativeLayout addToCartBtn = findViewById(R.id.addToCartBtn);
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDescriptionActivity.this, AddToCartActivity.class);
                startActivity(intent);
            }
        });


        TextView damphangia = findViewById(R.id.damphangiaText);
        damphangia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog
                Dialog firstDialog = new Dialog(ProductDescriptionActivity.this);
                // Set up the dialog window with MATCH_PARENT width
                Window window = firstDialog.getWindow();
                if (window != null) {
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                }
                firstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
                firstDialog.setContentView(R.layout.popup_damphan); // Your custom layout
                firstDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the rounded background

                // Show the dialog
                firstDialog.show();

                // Handle the "Đàm phán" button in the first dialog
                TextView damPhanButton = firstDialog.findViewById(R.id.damphanBtn);
                damPhanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create the second dialog
                        Dialog secondDialog = new Dialog(ProductDescriptionActivity.this);

                        // Set up the second dialog window with MATCH_PARENT width
                        Window secondWindow = secondDialog.getWindow();
                        if (secondWindow != null) {
                            secondWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                        }

                        secondDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
                        secondDialog.setContentView(R.layout.popup_damphanyeucau); // Your custom layout
                        secondDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the background

                        // Show the second dialog
                        secondDialog.show();

                        // Dismiss the first dialog if needed
                        firstDialog.dismiss();
                    }
                });
            }
        });
    }
    private void loadProductData() {
        databaseReference.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null) {
                        // Update views with the product data
                        updateViews(product);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });
    }
    private void updateViews(Product product) {
        // Update views with the product data
        TextView productName = findViewById(R.id.productName);
        TextView descriptionText = findViewById(R.id.descriptionText);
        TextView price = findViewById(R.id.price);

        productName.setText(product.getName());
        descriptionText.setText(product.getDesc());
        price.setText(product.getFormattedPrice());

        // You can continue updating other views as needed
    }
}
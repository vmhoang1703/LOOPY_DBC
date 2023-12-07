package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vecom.Model.CardItem;
import com.example.vecom.Model.Order;
import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentOptionsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    private RadioButton radioBtnYourWallet, radioBtnPaypal, radioBtnMastercard, radioBtnCod;
    private RadioGroup paymentOptionsGroup;
    RelativeLayout confirmPaymentBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_options);
        radioBtnYourWallet = findViewById(R.id.radioBtnYourWallet);
        radioBtnPaypal = findViewById(R.id.radioBtnPaypal);
        radioBtnMastercard = findViewById(R.id.radioBtnMastercard);
        radioBtnCod = findViewById(R.id.radioBtnCod);
        paymentOptionsGroup = findViewById(R.id.paymentOptionsGroup);



        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentOptionsActivity.this, OrderInformationActivity.class);
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(backArrow, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                startActivity(intent);
                finish();
            }
        });

        confirmPaymentBtn = findViewById(R.id.confirmPaymentBtn);

        // Bắt sự kiện khi nút xác nhận thanh toán được nhấn
        confirmPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem RadioButton nào được chọn
                if (radioBtnYourWallet.isChecked()) {
                    // RadioButton "Ví của bạn" được chọn
                    handleYourWalletRadioButton();
                } else if (radioBtnPaypal.isChecked()) {
                    // RadioButton "Paypal" được chọn
                    handlePaypalRadioButton();
                } else if (radioBtnMastercard.isChecked()) {
                    // RadioButton "Mastercard" được chọn
                    handleMastercardRadioButton();
                } else if (radioBtnCod.isChecked()) {
                    // RadioButton "COD" được chọn
                    handleCodRadioButton();
                }
            }
        });
    }

    private void showSuccessPopup() {
        View overlayView = findViewById(R.id.overlayView);
        overlayView.setVisibility(View.VISIBLE);
        // Create the second dialog
        Dialog successDialog = new Dialog(PaymentOptionsActivity.this);

        // Set up the second dialog window with MATCH_PARENT width
        Window secondWindow = successDialog.getWindow();
        if (secondWindow != null) {
            secondWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }

        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // Optional, removes title bar
        successDialog.setContentView(R.layout.popup_dathangthanhcong); // Your custom layout
        successDialog.getWindow().setBackgroundDrawableResource(R.drawable.damphan_popup); // Apply the background

        // Lấy reference của content view của dialog
        View dialogView = successDialog.findViewById(android.R.id.content);

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
                    successDialog.dismiss();
                }
                return false;
            }
        });
        deleteUserCardItems(currentUser.getEmail());
        // Create an Order object with the necessary data\
        DatabaseReference cardItemsRef = FirebaseDatabase.getInstance().getReference("cardItems");

        // Query to find CardItems for the current user
        cardItemsRef.orderByChild("userEmail").equalTo(currentUser.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot cardItemSnapshot : dataSnapshot.getChildren()) {
                    CardItem cardItem = cardItemSnapshot.getValue(CardItem.class);

                    if (cardItem != null) {
                        // Generate a unique order ID


                        // Create an Order object from CardItem
                        Order order = createOrder( cardItem);

                        // Save the order to Firebase
                        saveOrderToFirebase(order);

                        // Remove the CardItem from Firebase
                        cardItemSnapshot.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });


        // Show the second dialog
        successDialog.show();

        TextView xemDonHangBtn = successDialog.findViewById(R.id.xemdonhangBtn);
        xemDonHangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentOptionsActivity.this, OrderManagerActivity.class);
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(xemDonHangBtn, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                startActivity(intent);
            }
        });
    }

    private void deleteUserCardItems(String userEmail) {
        DatabaseReference cardItemsRef = FirebaseDatabase.getInstance().getReference("cardItems");

        // Query to find CardItems for the current user
        cardItemsRef.orderByChild("userEmail").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot cardItemSnapshot : dataSnapshot.getChildren()) {
                    // Get the unique key of the CardItem to be deleted
                    String cardItemKey = cardItemSnapshot.getKey();

                    // Remove the CardItem from Firebase
                    cardItemsRef.child(cardItemKey).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
    private Order createOrder( CardItem cardItem) {
        // Create an Order object using CardItem data
        return new Order(
                cardItem.getProductId(),
                cardItem.getName(),
                cardItem.getPrice(),
                cardItem.getImageUrl(),
                cardItem.getUserEmail(),
                1, // Assuming quantity is 1 for each CardItem
                "Đang vận chuyển" // Assuming the initial status is "pending"
        );
    }

    private void saveOrderToFirebase(Order order) {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");

        // Save the order to Firebase
        ordersRef.child(order.getOrderId()).setValue(order);
    }
    private void handleYourWalletRadioButton() {
        // Viết mã xử lý tại đây
        Intent intent = new Intent(PaymentOptionsActivity.this, PINHandlerActivity.class);
        // Tạo hiệu ứng làm mờ nút
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(confirmPaymentBtn, "alpha", 1f, 0.5f);
        fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
        fadeOut.start();

        startActivity(intent);
    }

    // Xử lý khi RadioButton "Paypal" được chọn
    private void handlePaypalRadioButton() {
        // Viết mã xử lý tại đây
        Intent intent = new Intent(PaymentOptionsActivity.this, PINHandlerActivity.class);
        // Tạo hiệu ứng làm mờ nút
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(confirmPaymentBtn, "alpha", 1f, 0.5f);
        fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
        fadeOut.start();

        startActivity(intent);
    }

    // Xử lý khi RadioButton "Mastercard" được chọn
    private void handleMastercardRadioButton() {
        // Viết mã xử lý tại đây
        Intent intent = new Intent(PaymentOptionsActivity.this, PINHandlerActivity.class);
        // Tạo hiệu ứng làm mờ nút
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(confirmPaymentBtn, "alpha", 1f, 0.5f);
        fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
        fadeOut.start();

        startActivity(intent);
    }

    // Xử lý khi RadioButton "COD" được chọn
    private void handleCodRadioButton() {
        showSuccessPopup();
    }
}
package com.example.vecom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vecom.R;

public class PINHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhandler);

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PINHandlerActivity.this, PaymentOptionsActivity.class);
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(backArrow, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                startActivity(intent);
                finish();
            }
        });

        RelativeLayout continuePayment = findViewById(R.id.continueBtn);
        continuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(continuePayment, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                View overlayView = findViewById(R.id.overlayView);
                overlayView.setVisibility(View.VISIBLE);
                // Create the second dialog
                Dialog successDialog = new Dialog(PINHandlerActivity.this);

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

                // Show the second dialog
                successDialog.show();

                TextView xemDonHangBtn = successDialog.findViewById(R.id.xemdonhangBtn);
                xemDonHangBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PINHandlerActivity.this, OrderManagerActivity.class);
                        // Tạo hiệu ứng làm mờ nút
                        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(xemDonHangBtn, "alpha", 1f, 0.5f);
                        fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                        fadeOut.start();

                        startActivity(intent);
                    }
                });
            }
        });
    }
}
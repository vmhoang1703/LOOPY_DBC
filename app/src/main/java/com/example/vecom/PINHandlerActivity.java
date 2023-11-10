package com.example.vecom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PINHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhandler);

        RelativeLayout continuePayment = findViewById(R.id.continueBtn);
        continuePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // Show the second dialog
                successDialog.show();
            }
        });
    }
}
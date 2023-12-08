package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    Uri imageUri;
    StorageReference storageReference;
    Dialog progressDialog;
    DatabaseReference databaseReference;
    private DatabaseReference userReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ImageView backArrow = findViewById(R.id.back);
        TextView showBtn = findViewById(R.id.showBtn);
        TextView saveBtn = findViewById(R.id.saveBtn);
        TextView addProductImg = findViewById(R.id.addProductImg);
        CheckBox checkBoxOption1 = findViewById(R.id.checkBoxOption1);
        CheckBox checkBoxOption2 = findViewById(R.id.checkBoxOption2);
        CheckBox checkBoxOption3 = findViewById(R.id.checkBoxOption3);

        storageReference = FirebaseStorage.getInstance().getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        // Initialize Spinner
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Set up a listener for the Spinner to handle the selected gender
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedGender = parentView.getItemAtPosition(position).toString();
                // You can use the selectedGender as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToMyProductsActivity();
                applyClickEffect(view);
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayProductInformation();
                applyClickEffect(view);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProductToDatabase();
                applyClickEffect(view);
            }
        });

        addProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                applyClickEffect(view);
            }
        });
    }

    private void applyClickEffect(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(200); // You can adjust the duration as needed
        view.startAnimation(scaleAnimation);
    }

    private void navigateToMyProductsActivity() {
        Intent intent = new Intent(AddProductActivity.this, MyProductsActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayProductInformation() {
        EditText productNameEditText = findViewById(R.id.edit_addProductName);
        String productName = productNameEditText.getText().toString();
        Toast.makeText(this, "Tên sản phẩm: " + productName, Toast.LENGTH_SHORT).show();
    }
    public void onCheckboxClicked(View view) {
        // Your implementation
    }

    private void saveProductToDatabase() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            EditText productNameEditText = findViewById(R.id.edit_addProductName);
            EditText productDescriptionEditText = findViewById(R.id.edit_addProductDesc);
            EditText priceEditText = findViewById(R.id.edit_addProductPrice);
            EditText stockEditText = findViewById(R.id.edit_addProductQuantity);

            String productName = productNameEditText.getText().toString();
            String productDescription = productDescriptionEditText.getText().toString();
            String priceStr = priceEditText.getText().toString();
            String stockStr = stockEditText.getText().toString();

            // Check if any of the required fields are empty
            if (productName.isEmpty() || productDescription.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(this, "Please enter all required information", Toast.LENGTH_SHORT).show();
                return;
            }

            int price = Integer.parseInt(priceStr);
            int stock = Integer.parseInt(stockStr);

            List<String> deliveryOptions = getSelectedDeliveryOptions();
            List<String> deliveryLocationOptions = new ArrayList<>();

            // Iterate over each selected delivery option
            for (String deliveryOption : deliveryOptions) {
                EditText deliveryLocationEditText;
                // Use the appropriate EditText based on the selected delivery option
                switch (deliveryOption) {
                    case "Nhận tại điểm bán":
                        deliveryLocationEditText = findViewById(R.id.editDeliveryLocation);
                        deliveryLocationOptions.add(deliveryLocationEditText.getText().toString());
                        break;
                    case "Tự giao":
                        deliveryLocationEditText = findViewById(R.id.editDeliveryLocation1);
                        deliveryLocationOptions.add(deliveryLocationEditText.getText().toString());
                        break;
                    default:
                        break;
                }
            }

            // Create a Product object with the delivery options and locations
            Product newProduct = new Product(productName, price, productDescription, 0, stock, "", "", "", userEmail, deliveryOptions, deliveryLocationOptions);

            // Push the non-image information to Realtime Database
            DatabaseReference productRef = databaseReference.push();
            productRef.setValue(newProduct);

            // Get the unique key generated by push() and use it as the image file name
            String imageName = productRef.getKey();

            if (imageUri != null) {
                uploadImage(imageUri, imageName);
            } else {
                // Hiển thị thông báo hoặc thực hiện các thao tác khác nếu cần
                Toast.makeText(this, "Sản phẩm đã được lưu vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private List<String> getSelectedDeliveryOptions() {
        List<String> selectedOptions = new ArrayList<>();

        CheckBox checkBoxOption1 = findViewById(R.id.checkBoxOption1);
        CheckBox checkBoxOption2 = findViewById(R.id.checkBoxOption2);
        CheckBox checkBoxOption3 = findViewById(R.id.checkBoxOption3);

        if (checkBoxOption1.isChecked()) {
            selectedOptions.add("Nhận tại điểm bán");
        }
        if (checkBoxOption2.isChecked()) {
            selectedOptions.add("Tự giao");
        }
        if (checkBoxOption3.isChecked()) {
            selectedOptions.add("Shipping");
        }

        return selectedOptions;
    }


    private void uploadImage(Uri imageUri, String imageName) {
        progressDialog = createProgressDialog(this);
        progressDialog.show();

        // Reference to the specific image file in Firebase Storage
        StorageReference imageRef = storageReference.child(imageName);

        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddProductActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        // Pass the image URL to saveProductToDatabase after successful image upload
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Update the Product object with the image URL
                                databaseReference.child(imageName).child("imageUrl").setValue(uri.toString());

                                // Hiển thị thông báo hoặc thực hiện các thao tác khác nếu cần
                                Toast.makeText(AddProductActivity.this, "Sản phẩm đã được lưu vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
//            saveProductToDatabase(); // Now calling this here to ensure it's triggered after selecting an image
        }
    }

    private Dialog createProgressDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);

        Dialog progressDialog = builder.create();
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return progressDialog;
    }
}


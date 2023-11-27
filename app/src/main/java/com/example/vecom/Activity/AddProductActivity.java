package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity {

    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ImageView backArrow = findViewById(R.id.back);

        TextView showBtn = findViewById(R.id.showBtn);
        TextView saveBtn = findViewById(R.id.saveBtn);

        TextView addProductImg = findViewById(R.id.addProductImg);

        addProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectImage();


            }
        });

        addProductImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadImage();

            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, MyProductsActivity.class);
                startActivity(intent);
                finish();
            }


        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayProductInformation();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProductToDatabase();
            }
        });
    }
    private void displayProductInformation() {
        EditText productNameEditText = findViewById(R.id.edit_addProductName);
        String productName = productNameEditText.getText().toString();
        Toast.makeText(this, "Tên sản phẩm: " + productName, Toast.LENGTH_SHORT).show();
    }

    private void saveProductToDatabase() {

        EditText productNameEditText = findViewById(R.id.edit_addProductName);
        EditText productDescriptionEditText = findViewById(R.id.edit_addProductDesc);
        EditText priceEditText = findViewById(R.id.edit_addProductPrice);
        EditText stockEditText = findViewById(R.id.edit_addProductQuantity);


        String productName = productNameEditText.getText().toString();
        String productDescription = productDescriptionEditText.getText().toString();


        double price = 0.0;
        if (!priceEditText.getText().toString().isEmpty()) {
            price = Double.parseDouble(priceEditText.getText().toString());
        }


        int stock = 0;
        if (!stockEditText.getText().toString().isEmpty()) {
            stock = Integer.parseInt(stockEditText.getText().toString());
        }



        Product newProduct = new Product(productName, price, productDescription, 0.0, stock, "", R.drawable.product_test, "");

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference("products");
        productsRef.push().setValue(newProduct);

        // Hiển thị thông báo hoặc thực hiện các thao tác khác nếu cần
        Toast.makeText(this, "Sản phẩm đã được lưu vào cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
    }
    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                        binding.firebaseimage.setImageURI(null);
                        Toast.makeText(AddProductActivity.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(AddProductActivity.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
//            binding.firebaseimage.setImageURI(imageUri);


        }
    }
}
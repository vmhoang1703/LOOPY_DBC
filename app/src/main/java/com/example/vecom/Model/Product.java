package com.example.vecom.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DecimalFormat;

@IgnoreExtraProperties
public class Product {
    private String productId;
    private String name;
    private double price;
    private String desc;

    private double rate;

    private int quantity;

    private String cmt;

    private String productType;
    private String imageUrl;

    private String userEmail;

    public Product() {

    }
    public Product(String name, double price, String desc, double rate, int quantity, String cmt, String imageUrl, String productType, String userEmail) {

        this.productId = generateProductId();
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.rate = rate;
        this.quantity = quantity;
        this.cmt = cmt;
        this.productType = productType;
        this.imageUrl = imageUrl;
        this.userEmail = userEmail;
    }
    private String generateProductId() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        return databaseReference.push().getKey();
    }
    public String getDesc(){return desc;};
    public String getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public String getFormattedPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format(price);
    }
//    public int getImageResourceId() {
//        return imageResourceId;
//    }

    public double getPrice() {
        return price;
    }

    public String getFormattedRate() {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(rate);
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComment() {
        return cmt;
    }

    public String getProductType() {
        return productType;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}

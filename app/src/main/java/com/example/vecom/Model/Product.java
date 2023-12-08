package com.example.vecom.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DecimalFormat;
import java.util.List;

@IgnoreExtraProperties
public class Product {
    private String productId;
    private String name;
    private int price;
    private String desc;

    private double rate;

    private int quantity;

    private String cmt;

    private String productType;
    private String imageUrl;

    private String userEmail;
    private String deliveryOption;

    private String deliveryOption1;
    private String deliveryLocation1;
    private String deliveryOption2;
    private String deliveryLocation2;

    public Product() {

    }
    public Product(String name, int price, String desc, double rate, int quantity, String cmt, String imageUrl, String productType, String userEmail, String deliveryOption, String deliveryOption1, String deliveryLocation1, String deliveryOption2, String deliveryLocation2) {

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
        this.deliveryOption = deliveryOption;
        this.deliveryOption1 = deliveryOption1;
        this.deliveryLocation1 = deliveryLocation1;
        this.deliveryOption2 = deliveryOption2;
        this.deliveryLocation2 = deliveryLocation2;
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

    public int getPrice() {
        return price;
    }

    public String getFormattedRate() {
        DecimalFormat decimalFormat = new DecimalFormat("0");
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
    public String getDeliveryOption() {
        return deliveryOption;
    }

    public String getDeliveryOption1() {
        return deliveryOption1;
    }

    public String getDeliveryLocation1() {
        return deliveryLocation1;
    }
    public String getDeliveryOption2() {
        return deliveryOption2;
    }

    public String getDeliveryLocation2() {
        return deliveryLocation2;
    }
}

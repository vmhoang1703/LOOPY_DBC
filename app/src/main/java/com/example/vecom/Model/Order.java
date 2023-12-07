package com.example.vecom.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class Order {
    private String orderId; // Add a unique order ID
    private String productId;
    private String imageUrl;
    private String productName;
    private int price; // Consider changing this to match your use case
    private int totalPrice;
    private String userEmail;
    private int quantity;
    private String status;
    public Order() {}

    public Order( String productId, String productName, int price, String imageUrl,String userEmail, int quantity, String status) {
        this.orderId = generateOrderId();
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.price = price;
        this.userEmail = userEmail;
        this.quantity = quantity;
        this.status = status;
        
    }
    private String generateOrderId() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
        return databaseReference.push().getKey();
    }
    public String getOrderId() {
        return orderId;
    }

    public String getProductId() {
        return productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}


package com.example.vecom.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DecimalFormat;

@IgnoreExtraProperties
public class Product {
    private static int lastProductID = 0;
    private String productId;
    private String productName;
    private double productPrice;
    private String productDesc;
    private double productRate;
    private int productQuantity;
    private String productComment;
    private String productType;
    private String productImageUrl;
    private String userEmail;
    private String userID;

    public Product() {
        this.productId = generateProductId();
        // Firebase sẽ tự động gán productId khi thêm vào
    }

    public Product(String productName, double productPrice, String productDesc, double productRate, int productQuantity, String productComment, String productImageUrl, String productType, String userEmail) {
        this.productId = generateProductId();
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.productRate = productRate;
        this.productQuantity = productQuantity;
        this.productComment = productComment;
        this.productType = productType;
        this.productImageUrl = productImageUrl;
        this.userEmail = userEmail;
    }

    private String generateProductId() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        return "productID" + String.format("%09d", ++lastProductID);
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public double getProductRate() {
        return productRate;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductComment() {
        return productComment;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getFormattedProductPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        return decimalFormat.format(productPrice);
    }

    public String getFormattedProductRate() {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        return decimalFormat.format(productRate);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                ", productRate=" + productRate +
                ", productQuantity=" + productQuantity +
                ", productComment='" + productComment + '\'' +
                ", productType='" + productType + '\'' +
                ", productImageUrl='" + productImageUrl + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}

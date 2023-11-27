package com.example.vecom.Model;

import java.text.DecimalFormat;

public class Order {
    private String productName;
    private double totalPrice;
    private int productImgRsc;
    private int quantity;
    private String status;
    // Add more fields as needed...

    public Order(String productName, double totalPrice, int productImgRsc, int quantity, String status) {
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.productImgRsc = productImgRsc;
        this.quantity = quantity;
        this.status = status;
        // Initialize additional fields here...
    }

    public String getProductName() {
        return productName;
    }

    public String getFormattedTotalPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format(totalPrice);
    }

    public int getProductImgRsc() {
        return productImgRsc;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

}

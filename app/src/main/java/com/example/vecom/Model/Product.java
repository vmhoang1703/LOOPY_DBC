package com.example.vecom.Model;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private double price;
    private int imageResourceId;

    public Product(String name, double price, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getFormattedPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format(price);
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

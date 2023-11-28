package com.example.vecom.Model;

import java.text.DecimalFormat;

public class Product {
    private String name;
    private double price;
    private String desc;

    private double rate;

    private int quantity;

    private String cmt;


    private String productType;
    private String imageUrl;
    private int imageResourceId;
    private String userEmail;


    public Product(String name, double price, String desc, double rate, int quantity, String cmt, String imageUrl, String productType, String userEmail) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.rate = rate;
        this.quantity = quantity;
        this.cmt = cmt;
        this.productType = productType;
        this.imageResourceId = imageResourceId;
        this.imageUrl = imageUrl;
        this.userEmail = userEmail;
    }
    public String getDesc(){return desc;};
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

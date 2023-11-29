package com.example.vecom.Model;

public class CardItem {

    private String productId;
    private String imageUrl;
    private String name;
    private int description;
    private double totalPrice;
    private double price;
    private String userEmail;
    public CardItem(){}

    public CardItem(String productId, String name, double price, String imageUrl,String userEmail) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userEmail = userEmail;
        this.totalPrice = calculateTotalPrice();

    }
    public String getProductId() {
        return productId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public String getUserEmail() {
        return userEmail;
    }
    private double calculateTotalPrice() {

        return totalPrice;
    }
}

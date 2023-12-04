package com.example.vecom.Model;

public class CardItem {

    private String productId;
    private String imageUrl;
    private String name;
    private int description;
    private int totalPrice;
    private int price;
    private String userEmail;

    public CardItem() {}

    public CardItem(String productId, String name, int price, String imageUrl, String userEmail) {
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

    public int getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    private int calculateTotalPrice() {
        // Thực hiện tính toán total price tại đây
        return totalPrice;
    }
}

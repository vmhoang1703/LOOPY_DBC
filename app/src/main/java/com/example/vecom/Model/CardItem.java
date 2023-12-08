package com.example.vecom.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CardItem {
    private String cardItemId;

    private String productId;
    private String imageUrl;
    private String name;
    private int description;
    private int totalPrice;
    private int price;
    private String userEmail;
    private List<String> deliveryOptions;
    private List<String> deliveryLocationOptions;
    public CardItem() {}

    public CardItem(String productId, String name, int price, String imageUrl, String userEmail, List<String> deliveryOptions, List<String> deliveryLocationOptions) {
        this.cardItemId = generateCardItemId();
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userEmail = userEmail;
        this.totalPrice = calculateTotalPrice();
        this.deliveryOptions = deliveryOptions;
        this.deliveryLocationOptions = deliveryLocationOptions;
    }
    private String generateCardItemId() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("cardItems");
        return databaseReference.push().getKey();
    }
    public String getCardItemId() {
        return cardItemId;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getUserEmail() {
        return userEmail;
    }

    private int calculateTotalPrice() {
        // Thực hiện tính toán total price tại đây
        return totalPrice;
    }
    public List<String> getDeliveryOptions(){
        return deliveryOptions;
    }
    public List<String> getDeliveryLocationOptions() {
        return deliveryLocationOptions;
    }
}

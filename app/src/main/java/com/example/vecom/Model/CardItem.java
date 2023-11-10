package com.example.vecom.Model;

public class CardItem {
    private int imageUrl;
    private String name;
    private int description;
    private double price;

    public CardItem(int imageUrl, String name, int description, double price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getImageUrl() {
        return imageUrl;
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
}

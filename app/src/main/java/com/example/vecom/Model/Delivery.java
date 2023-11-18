package com.example.vecom.Model;

public class Delivery {
    private String trackingNumber;
    private String deliveryStatus;
    private String deliveryAddress;
    private double deliveryCost;

    public Delivery() {
        // Constructor mặc định
    }

    public Delivery(String trackingNumber, String deliveryStatus, String deliveryAddress, double deliveryCost) {
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = deliveryStatus;
        this.deliveryAddress = deliveryAddress;
        this.deliveryCost = deliveryCost;
    }

    // Getter và Setter cho thuộc tính trackingNumber
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    // Getter và Setter cho thuộc tính deliveryStatus
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    // Getter và Setter cho thuộc tính deliveryAddress
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    // Getter và Setter cho thuộc tính deliveryCost
    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "trackingNumber='" + trackingNumber + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryCost=" + deliveryCost +
                '}';
    }
}


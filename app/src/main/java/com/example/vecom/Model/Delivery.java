package com.example.vecom.Model;

public class Delivery {
    private String trackingNumber;
    private String deliveryStatus;
    private String deliveryAddress;
    private double deliveryCost;
    private String deliveryType;

    public Delivery() {

    }

    public Delivery(String trackingNumber, String deliveryStatus, String deliveryAddress, double deliveryCost, String deliveryType) {
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = deliveryStatus;
        this.deliveryAddress = deliveryAddress;
        this.deliveryCost = deliveryCost;
        this.deliveryType = deliveryType;
    }


    public String getTrackingNumber() {
        return trackingNumber;
    }


    public String getDeliveryStatus() {
        return deliveryStatus;
    }


    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryType() {
        return deliveryType;
    }


    public double getDeliveryCost() {
        return deliveryCost;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "trackingNumber='" + trackingNumber + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryCost=" + deliveryCost + '\'' +
                ", deliveryType" + deliveryType +
                '}';
    }
}


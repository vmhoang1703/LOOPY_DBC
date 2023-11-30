package com.example.vecom.Model;

public class Voucher {
    private String voucherId;
    private double discountPercentage;
    private boolean isActive;

    public Voucher() {
    }

    public Voucher(String voucherCode, double discountPercentage, boolean isActive) {
        this.voucherId = voucherCode;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherCode='" + voucherId + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", isActive=" + isActive +
                '}';
    }
}


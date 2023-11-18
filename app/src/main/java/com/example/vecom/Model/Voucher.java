package com.example.vecom.Model;

public class Voucher {
    private String voucherCode;
    private double discountPercentage;
    private boolean isActive;

    public Voucher() {
        // Constructor mặc định
    }

    public Voucher(String voucherCode, double discountPercentage, boolean isActive) {
        this.voucherCode = voucherCode;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
    }

    // Getter và Setter cho thuộc tính voucherCode
    public String getVoucherCode() {
        return voucherCode;
    }


    public double getDiscountPercentage() {
        return discountPercentage;
    }



    // Getter và Setter cho thuộc tính isActive
    public boolean isActive() {
        return isActive;
    }


    @Override
    public String toString() {
        return "Voucher{" +
                "voucherCode='" + voucherCode + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", isActive=" + isActive +
                '}';
    }
}


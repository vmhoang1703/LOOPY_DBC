package com.example.vecom.Model;

public class Payment {
    private static int lastPaymentId = 0;

    private String paymentId;
    private String paymentMethod;
    private double paymentAmount;
    private String paymentTransactionId;

    public Payment() {
        this.paymentId = generatePaymentId();
    }

    public Payment(String paymentMethod, double paymentAmount, String paymentTransactionId) {
        this.paymentId = generatePaymentId();
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.paymentTransactionId = paymentTransactionId;
    }

    private String generatePaymentId() {
        return "PM" + String.format("%09d", ++lastPaymentId);
    }

    // Getter và Setter cho thuộc tính paymentId
    public String getPaymentId() {
        return paymentId;
    }
    

    // Getter và Setter cho thuộc tính paymentMethod
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Getter và Setter cho thuộc tính paymentAmount
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}

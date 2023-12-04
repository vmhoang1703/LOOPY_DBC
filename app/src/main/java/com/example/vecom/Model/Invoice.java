package com.example.vecom.Model;

import java.util.Date;

public class Invoice {
    private int invoiceId;
    private String customerName;
    private Date issueDate;
    private int totalAmount;
    private boolean isPaid;

    public Invoice() {

    }

    public Invoice(int invoiceId, String customerName, Date issueDate, int totalAmount, boolean isPaid) {
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    // Getter và Setter cho thuộc tính invoiceId
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    // Getter và Setter cho thuộc tính customerName
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Getter và Setter cho thuộc tính issueDate
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    // Getter và Setter cho thuộc tính totalAmount
    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Getter và Setter cho thuộc tính isPaid
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", customerName='" + customerName + '\'' +
                ", issueDate=" + issueDate +
                ", totalAmount=" + totalAmount +
                ", isPaid=" + isPaid +
                '}';
    }
}


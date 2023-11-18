package com.example.vecom.Model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import java.util.ArrayList;

public class User {
    private int userID;
    private String userName;
    private String password;
    private String email;
    private String ID_card;//CCCD/CMT
    private String fullName;
    private String dob;
    private String gender;
    private String hometown;
    private String address;
    private String phoneNumber;
    private ArrayList<String> liked_product;
    private String hashedPIN; // Thêm thuộc tính để lưu mã PIN đã hash
    private String salt;



    // Constructors
    public User() {
        this.salt = generateSalt();
    }

    public User(int userID, String userName, String password, String email,String ID_card, String fullName,String dob,String gender,String hometown, String address, String phoneNumber,ArrayList<String> liked_product,String pin) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.ID_card = ID_card;//CCCD/CMT
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.hometown = hometown;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.liked_product = liked_product;
        this.salt = generateSalt();
        this.hashedPIN = hashPIN(pin, this.salt);
    }
    private String generateSalt() {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    private String hashPIN(String pin, String salt) {
        try {
            String saltedPIN = pin + salt;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(saltedPIN.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    public boolean isCorrectPIN(String pinToCheck) {
        String hashedPINToCheck = hashPIN(pinToCheck, this.salt);
        return hashedPINToCheck.equals(this.hashedPIN);
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public ArrayList<String> getLiked_products() {
        return liked_product;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public String getFullName() {
        return fullName;
    }

    public String getDob() {
        return dob;
    }
    public String getGender() {
        return gender;
    }
    public String getHometown() {
        return hometown;
    }
    public String getID_card() {
        return ID_card;
    }

    public String getAddress() {
        return address;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", ID_card='" + ID_card + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", hometown='" + hometown + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", likedProduct='" + liked_product + '\'' +
                ", Pin='" + hashedPIN + '\'' +
                '}';
    }
}


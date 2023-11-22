package com.example.vecom.Model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import java.util.ArrayList;

public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private String userEmail;
//    private String IdCard;//CCCD/CMT
    private String userFullName;
    private String userDob;
    private String userGender;
    private String userHometown;
    private String userAddress;
    private String userPhoneNumber;
    private ArrayList<String> liked_product;
    private String hashedPIN;
    private String salt;

    // Constructors
    public User() {
        this.salt = generateSalt();
    }

    public User(int userID, String userName, String userPassword, String userEmail, String userFullName, String userDob, String userGender, String userHometown, String userAddress, String userPhoneNumber, ArrayList<String> liked_product, String pin) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
//        this.IdCard = ID_card;// CCCD/CMT
        this.userFullName = userFullName;
        this.userDob = userDob;
        this.userGender = userGender;
        this.userHometown = userHometown;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
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

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserDob() {
        return userDob;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserHometown() {
        return userHometown;
    }

//    public String getIdCard() {
//        return IdCard;
//    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + userPassword + '\'' +
                ", email='" + userEmail + '\'' +
                ", fullName='" + userFullName + '\'' +
//                ", ID_card='" + IdCard + '\'' +
                ", dob='" + userDob + '\'' +
                ", gender='" + userGender + '\'' +
                ", hometown='" + userHometown + '\'' +
                ", address='" + userAddress + '\'' +
                ", phoneNumber='" + userPhoneNumber + '\'' +
                ", likedProduct='" + liked_product + '\'' +
                ", Pin='" + hashedPIN + '\'' +
                '}';
    }
}


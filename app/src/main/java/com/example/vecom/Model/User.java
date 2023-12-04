package com.example.vecom.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class User {
    private static int lastUserID = 0;
    private String userID;
    private String userName;
    private String userPassword;
    private String userEmail;
    private String userFullName;
    private String userDob;
    private String userGender;
    private String userAddress;
    private String userPhoneNumber;
    private ArrayList<String> liked_product;
    private String hashedPIN;
    private String salt;

    // Constructors
    public User() {
        this.userID = generateUserID();
        this.salt = generateSalt();
    }

    public User(String userName, String userPassword, String userEmail,
                String userFullName, String userDob, String userGender, String userPhoneNumber) {
        this.userID = generateUserID();
        this.userName = userName;
        this.userPassword = hashAndSaltPassword(userPassword);
        this.userEmail = userEmail;
        this.userFullName = userFullName;
        this.userDob = userDob;
        this.userGender = userGender;
        this.userPhoneNumber = userPhoneNumber;
        this.salt = generateSalt();
    }

    private String hashAndSaltPassword(String userPassword) {
        String saltedPassword = userPassword + this.salt;
        // Use a secure hashing algorithm like bcrypt
        // For example, using a simple demonstration (not recommended for production):
        return BCrypt.hashpw(saltedPassword, BCrypt.gensalt());
    }

    // Methods for hashing PIN
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
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public ArrayList<String> getLikedProduct() {
        return liked_product;
    }

    public void setLikedProduct(ArrayList<String> liked_product) {
        this.liked_product = liked_product;
    }

    public String getHashedPIN() {
        return hashedPIN;
    }

    public void setHashedPIN(String hashedPIN) {
        this.hashedPIN = hashedPIN;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    private String generateUserID() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        return "UID" + String.format("%09d", ++lastUserID);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ",userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", userDob='" + userDob + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", liked_product=" + liked_product +
                ", hashedPIN='" + hashedPIN + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}

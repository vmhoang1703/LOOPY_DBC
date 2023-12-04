package com.example.vecom.Model;

public class Category {
    private static int lastCategoryId = 0;

    private int categoryId;
    private String categoryName;
    private String categoryDescription;

    public Category() {
        this.categoryId = generateCategoryId();
    }

    public Category(String categoryName, String categoryDescription) {
        this.categoryId = generateCategoryId();
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    private int generateCategoryId() {
        return ++lastCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    // Getter và Setter cho thuộc tính categoryName
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter và Setter cho thuộc tính description
    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + categoryDescription + '\'' +
                '}';
    }
}

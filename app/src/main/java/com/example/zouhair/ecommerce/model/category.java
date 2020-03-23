package com.example.zouhair.ecommerce.model;

import android.widget.ImageView;
import android.widget.TextView;

public class category {
    public ImageView categoryImage;
    private String categoryText;
    public category() {
    }

    public category(ImageView categoryImage, String categoryText) {
        this.categoryImage = categoryImage;
        this.categoryText = categoryText;
    }

    public ImageView getCategoryImage() {
        return categoryImage;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryImage(ImageView categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }
}

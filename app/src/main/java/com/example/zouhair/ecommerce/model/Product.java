package com.example.zouhair.ecommerce.model;

public class Product
{
    private String id;
    private String description;
    private String  image;
    private String  designation;
    private float price;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public Product(String id, String description, String  image, String  designation, float price){
        this.id=id;
        this.description = description;
        this.image=image;
        this.price=price;
        this.designation=designation;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

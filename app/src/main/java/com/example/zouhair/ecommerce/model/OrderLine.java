package com.example.zouhair.ecommerce.model;

public class OrderLine
{
    public String name;
    String designation;
    int quantity;
    float price;
    String id;
    byte[] image;
    public OrderLine(){}
    public OrderLine(String designation, int Quantity, float price, String id, byte[] image){
        this.designation=designation;
        this.quantity=Quantity;
        this.price=price;
        this.id=id;
        this.image=image;
    }
    public OrderLine(String designation, int Quantity, float price,String id){
        this.designation=designation;
        this.quantity=Quantity;
        this.price=price;
        this.id=id;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getQuantity() {
        return quantity;
    }
    public void incrementQuantity(int q) {
        this.quantity+=q;
    }
    public void incrementPrice(float p) {
        this.price=this.price+p;
    }
    public void setQuantity(int quantity) {
        quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price += price;
    }
}

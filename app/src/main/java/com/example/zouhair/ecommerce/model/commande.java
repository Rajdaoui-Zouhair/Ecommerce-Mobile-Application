package com.example.zouhair.ecommerce.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class commande
{
    private ArrayList<Product> produts ;
    private double prix_total;
    private boolean status;
    public commande(ArrayList<Product> produts, double prix_total, boolean status) {
        this.produts = produts;
        this.prix_total = prix_total;
        this.status = status;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setProduts(ArrayList<Product> produts) {
        this.produts = produts;
    }

    public ArrayList<Product> getProduts() {
        return produts;
    }

    public double getPrix_total() {
        return prix_total;
    }

    public boolean isStatus() {
        return status;
    }


}

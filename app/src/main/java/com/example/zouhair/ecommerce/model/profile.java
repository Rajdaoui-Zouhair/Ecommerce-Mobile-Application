package com.example.zouhair.ecommerce.model;

import android.widget.ImageView;

public class profile {
    private String fullname;
    private String job;
    private String email;
    private String contact;
    private String facebook;
    private String twitter;
    private ImageView image;

    public profile(String fullname, String job, String email, String contact, String facebook, String twitter) {
        this.fullname = fullname;
        this.job = job;
        this.email = email;
        this.contact = contact;
        this.facebook = facebook;
        this.twitter = twitter;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getFullname() {
        return fullname;
    }

    public String getJob() {
        return job;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public ImageView getImage() {
        return image;
    }
}

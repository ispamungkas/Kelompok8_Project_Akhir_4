package com.uknown.kelompok8_project_akhir_8.homepage.model;


import java.io.Serializable;

public class User implements Serializable {
    public String name, email, phoneNumber, photo, userId;


    // Konstruktor kosong  untuk Firebase
    public User() {
    }

    public User(String email, String name, String phoneNumber, String photo, String userId) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.userId = userId;
    }

    // Metode getter dan setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

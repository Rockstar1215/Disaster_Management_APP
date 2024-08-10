package com.example.disaster;

public class CustomUser {
    private String name;
    private String email;
    private String city;
    private String phone;
    private String address;

    // Default constructor required for Firestore
    public CustomUser() {
    }

    public CustomUser(String name, String email, String city, String phone, String address) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.address = address;
    }

    // Getters and setters
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

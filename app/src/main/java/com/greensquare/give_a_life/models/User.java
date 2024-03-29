package com.greensquare.give_a_life.models;

public class User {

    private String name, bloodType, mobile, email;
    private boolean ableToDonate;

    public User() {

        name = "";
        bloodType = "";
        mobile = "";
        email = "";
        ableToDonate = true;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAbleToDonate() {
        return ableToDonate;
    }

    public void setAbleToDonate(boolean ableToDonate) {
        this.ableToDonate = ableToDonate;
    }
}

package com.greensquare.give_a_life.models;

public class Request {

    private String ID;
    private String name;
    private String postDate;
    private String dueDate;
    private String userContact;
    private String location;
    private String note;
    private String bloodType;
    private boolean donor;

    public Request() {
        this.ID = "";
        this.name = "";
        this.userContact="";
        this.postDate = "";
        this.dueDate = "";
        this.location = "";
        this.note = "";
        this.bloodType = "";
        this.donor = true;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isDonor() {
        return donor;
    }

    public void setDonor(boolean donor) {
        this.donor = donor;
    }
}

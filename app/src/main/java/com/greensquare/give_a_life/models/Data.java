package com.greensquare.give_a_life.models;

import java.util.ArrayList;

public class Data {

    private ArrayList<Request> donor,requesters;
    private ArrayList<User> users;
    private Request request;
    private User user;
    private static Data instance;

    private Data (){
        requesters = new ArrayList<>();
        donor = new ArrayList<>();
        users = new ArrayList<>();
        request = new Request();
        user = new User();
    }

    public static synchronized Data  getInstance(){
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    public ArrayList<Request> getRequesters() {
        return requesters;
    }

    public void setRequesters(ArrayList<Request> requesters) {
        this.requesters = requesters;
    }

    public ArrayList<Request> getDonor() {
        return donor;
    }

    public void setDonor(ArrayList<Request> donor) {
        this.donor = donor;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

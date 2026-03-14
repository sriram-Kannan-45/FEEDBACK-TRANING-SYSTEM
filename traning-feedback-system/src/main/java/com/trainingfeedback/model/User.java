package com.trainingfeedback.model;

public class User {

    protected int id;
    protected String name;
    protected String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
package com.trainingfeedback.model;

public class User {

    protected int    id;
    protected String name;
    protected String password;

    // Constructor
    public User(int id, String name, String password) {
        this.id       = id;
        this.name     = name;
        this.password = password;
    }

    // Getters
    public int    getId()       { return id; }
    public String getName()     { return name; }
    public String getPassword() { return password; }

    // Password check - login verify 
    public boolean checkPassword(String inputPassword) {
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            return false;
        }
        return this.password.equals(inputPassword);
    }
}
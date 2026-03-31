package com.trainingfeedback.model;

public class User {

<<<<<<< HEAD
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

    public String getName() {
        return name;
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    }
}
package com.trainingfeedback.model;

public class Admin extends User {

    public Admin(int id, String name, String password) {
        super(id, name, password);
    }

    public void display() {
        System.out.println("Admin ID: " + id);
        System.out.println("Admin Name: " + name);
    }
}  
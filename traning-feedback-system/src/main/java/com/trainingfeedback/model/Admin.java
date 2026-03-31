package com.trainingfeedback.model;

public class Admin extends User {

    private String role = "ADMIN";

    public Admin(int id, String name, String password) {
        super(id, name, password);
    }

    public String getRole() {
        return role;
    }

    public void display() {
        System.out.println("Admin ID   : " + id);
        System.out.println("Admin Name : " + name);
        System.out.println("Role       : " + role);
    }
}
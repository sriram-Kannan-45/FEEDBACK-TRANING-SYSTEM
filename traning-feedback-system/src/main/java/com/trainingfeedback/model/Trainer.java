package com.trainingfeedback.model;

public class Trainer extends User {

    private boolean approved;

    public Trainer(int id, String name, String password) {
        super(id, name, password);
        this.approved = false;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void display() {
        System.out.println("Trainer ID: " + id);
        System.out.println("Trainer Name: " + name);
        System.out.println("Approved: " + approved);
    }
}
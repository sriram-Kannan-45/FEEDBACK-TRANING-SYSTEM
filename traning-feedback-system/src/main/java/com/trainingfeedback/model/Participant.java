package com.trainingfeedback.model;

public class Participant extends User {

    public Participant(int id, String name, String password) {
        super(id, name, password);
    }

    public void display() {
        System.out.println("Participant ID: " + id);
        System.out.println("Participant Name: " + name);
    }
}
package com.trainingfeedback.model;

ppackage com.trainingfeedback.model;

import java.util.ArrayList;

public class Participant extends User {

    private ArrayList<String> feedbackList = new ArrayList<>();

    public Participant(int id, String name, String password) {
        super(id, name, password);
    }

    public void display() {
        System.out.println("Participant ID: " + id);
        System.out.println("Participant Name: " + name);
    }

    public void addFeedback(String feedback) {
        feedbackList.add(feedback);
    }

    public ArrayList<String> getFeedbackList() {
        return feedbackList;
    }
}

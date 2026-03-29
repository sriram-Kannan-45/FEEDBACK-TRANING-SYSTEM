package com.trainingfeedback.model;

import java.util.ArrayList;

public class Participant extends User {

    private String email;
    private String course;

    private ArrayList<String> feedbackList = new ArrayList<>();

    public Participant(int id,String name,String password,String email,String course) {

        super(id,name,password);

        this.email=email;
        this.course=course;
    }

    public void display() {

        System.out.println("ID: "+id);
        System.out.println("Name: "+name);
        System.out.println("Email: "+email);
        System.out.println("Course: "+course);
    }

    public void addFeedback(String feedback) {
        feedbackList.add(feedback);
    }

    public ArrayList<String> getFeedbackList() {
        return feedbackList;
    }
}
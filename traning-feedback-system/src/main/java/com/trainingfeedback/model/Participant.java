package com.trainingfeedback.model;

import java.util.ArrayList;

public class Participant extends User {

    private String email;
    private String dept;
    private String college;
    private String course;

    private ArrayList<String> feedbackList = new ArrayList<>();

    public Participant(int id,String name,String password,
                       String email,String dept,String college,String course) {

        super(id,name,password);

        this.email=email;
        this.dept=dept;
        this.college=college;
        this.course=course;
    }

    public void display() {

        System.out.println("ID: "+id);
        System.out.println("Name: "+name);
        System.out.println("Email: "+email);
        System.out.println("Dept: "+dept);
        System.out.println("College: "+college);
        System.out.println("Course: "+course);
    }

    public void addFeedback(String feedback) {
        feedbackList.add(feedback);
    }

    public ArrayList<String> getFeedbackList() {
        return feedbackList;
    }
}
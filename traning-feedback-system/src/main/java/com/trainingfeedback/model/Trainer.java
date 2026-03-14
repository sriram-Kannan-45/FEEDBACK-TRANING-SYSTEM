package com.trainingfeedback.model;

import java.util.ArrayList;

public class Trainer extends User {

    private boolean approved;
    private ArrayList<String> courses;

    public Trainer(int id, String name, String password) {
        super(id, name, password);
        this.approved = false;
        this.courses = new ArrayList<>();
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void addCourse(String course) {
        courses.add(course);
    }

    public void display() {
        System.out.println("Trainer ID: " + getId());
        System.out.println("Trainer Name: " + getName());
        System.out.println("Approved: " + approved);
        System.out.println("Courses: " + courses);
    }
}
/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.model;

import java.util.ArrayList;

/*
Class: Trainer
Module: Trainer
By: Shamiha

Purpose: Trainer model extending User
OOPS: Inheritance - extends User, Encapsulation - private courses + approved, Polymorphism - overrides display()
*/
public class Trainer extends User {

    private boolean approved = false;
    private ArrayList<String> courses = new ArrayList<>();

    public Trainer(int id, String name, String password) {
        super(id, name, password);
    }

    public void addCourse(String course) {
        courses.add(course);
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void display() {

        System.out.println("Trainer ID : " + id);
        System.out.println("Trainer Name : " + name);
        System.out.println("Approved : " + approved);
        System.out.println("Courses : " + courses);
    }
}
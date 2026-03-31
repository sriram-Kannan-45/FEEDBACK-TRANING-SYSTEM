package com.trainingfeedback.model;

import java.util.ArrayList;

public class Trainer extends User {

    private boolean approved = false;
    private String email;
    private String department;
    private String course;
    private ArrayList<String> courses = new ArrayList<>();

    // Constructor
    public Trainer(int id, String name, String password) {
        super(id, name, password);
    }

    // Constructor with all fields
    public Trainer(int id, String name, String password, String email, String department, String course) {
        super(id, name, password);
        this.email = email;
        this.department = department;
        this.course = course;
    }

    // Getters for new fields
    public String getEmail()       { return email; }
    public String getDepartment()  { return department; }
    public String getCourse()      { return course; }

    // Setters for new fields
    public void setEmail(String email)           { this.email = email; }
    public void setDepartment(String department) { this.department = department; }
    public void setCourse(String course)         { this.course = course; }

    // Add course - null check added
    public void addCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            System.out.println("Error: Course name cannot be empty!");
            return;
        }
        if (courses.contains(course.trim())) {
            System.out.println("Course already added: " + course);
            return;
        }
        courses.add(course.trim());
    }

    // Getters
    public ArrayList<String> getCourses() {
        return courses;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    // Display
    public void display() {
        System.out.println("Trainer ID   : " + id);
        System.out.println("Trainer Name : " + name);
        System.out.println("Approved     : " + (approved ? "Yes" : "Pending"));
        System.out.println("Courses      : " + (courses.isEmpty() ? "No courses assigned" : courses));
    }
}
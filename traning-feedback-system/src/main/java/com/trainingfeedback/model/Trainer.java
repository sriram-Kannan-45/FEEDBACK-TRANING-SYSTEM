package com.trainingfeedback.model;

import java.util.ArrayList;

public class Trainer extends User {

    private boolean approved = false;
<<<<<<< HEAD
    private ArrayList<String> courses = new ArrayList<>();
=======
    private String email;
    private String department;
    private String course;
    private ArrayList<String> courses = new ArrayList<>();

    // Constructor
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    public Trainer(int id, String name, String password) {
        super(id, name, password);
    }

<<<<<<< HEAD
    public void addCourse(String course) {
        courses.add(course);
    }

=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    public ArrayList<String> getCourses() {
        return courses;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

<<<<<<< HEAD
    public void display() {

        System.out.println("Trainer ID : " + id);
        System.out.println("Trainer Name : " + name);
        System.out.println("Approved : " + approved);
        System.out.println("Courses : " + courses);
=======
    // Display
    public void display() {
        System.out.println("Trainer ID   : " + id);
        System.out.println("Trainer Name : " + name);
        System.out.println("Approved     : " + (approved ? "Yes" : "Pending"));
        System.out.println("Courses      : " + (courses.isEmpty() ? "No courses assigned" : courses));
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    }
}
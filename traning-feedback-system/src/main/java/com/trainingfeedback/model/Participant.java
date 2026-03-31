package com.trainingfeedback.model;

import java.util.ArrayList;

public class Participant extends User {

    private String email;
<<<<<<< HEAD
    private String course;

    // sessions this participant registered 
    private ArrayList<TrainingSession> registeredSessions = new ArrayList<>();

    // tracks submitted feedback history
    private ArrayList<Feedback> feedbackHistory = new ArrayList<>();

    // has pending session without feedback
    private boolean feedbackReminderPending = false;

    public Participant(int id, String name, String password, String email, String course) {
        super(id, name, password);
        this.email  = email;
        this.course = course;
    }

    public String getEmail()  { return email; }
    public String getCourse() { return course; }

    // Session registration
    public ArrayList<TrainingSession> getRegisteredSessions() { return registeredSessions; }

    public void registerSession(TrainingSession ts) {
=======
    private String dept;
    private String college;
    private String course;

    // Sessions this participant registered
    private ArrayList<TrainingSession> registeredSessions = new ArrayList<>();

    // Tracks submitted feedback history
    private ArrayList<Feedback> feedbackHistory = new ArrayList<>();

    // Has pending session without feedback
    private boolean feedbackReminderPending = false;

    // Constructor (6 params - for DB queries)
    public Participant(int id, String name, String password, String email, String dept, String college, String course) {
        super(id, name, password);
        this.email = email;
        this.dept = dept;
        this.college = college;
        this.course = course;
    }

    // Constructor (5 params - legacy)
    public Participant(int id, String name, String password, String email, String course) {
        this(id, name, password, email, "", "", course);
    }

    // Getters
    public String getEmail()  { return email; }
    public String getDept()   { return dept; }
    public String getCollege() { return college; }
    public String getCourse() { return course; }

    // Setters
    public void setEmail(String email)    { this.email = email; }
    public void setDept(String dept)      { this.dept = dept; }
    public void setCollege(String college) { this.college = college; }
    public void setCourse(String course)  { this.course = course; }

    // Session registration
    public ArrayList<TrainingSession> getRegisteredSessions() {
        return registeredSessions;
    }

    public void registerSession(TrainingSession ts) {
        if (ts == null) {
            System.out.println("Error: Session cannot be null!");
            return;
        }
        if (registeredSessions.contains(ts)) {
            System.out.println("Already registered for this session!");
            return;
        }
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        registeredSessions.add(ts);
    }

    public boolean isRegisteredFor(TrainingSession ts) {
        return registeredSessions.contains(ts);
    }

<<<<<<< HEAD
    // Feedback history 
    public void addFeedbackHistory(Feedback f) {
=======
    // Feedback history
    public void addFeedbackHistory(Feedback f) {
        if (f == null) {
            System.out.println("Error: Feedback cannot be null!");
            return;
        }
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        feedbackHistory.add(f);
    }

    public ArrayList<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    // Feedback reminder
<<<<<<< HEAD
    public boolean isFeedbackReminderPending(){ 
    	return feedbackReminderPending; 
    	}
    public void setFeedbackReminderPending(boolean b){
    	this.feedbackReminderPending = b; 
    	}

    public void display() {
        System.out.println("ID     : " + id);
        System.out.println("Name   : " + name);
        System.out.println("Email  : " + email);
        System.out.println("Course : " + course);
=======
    public boolean isFeedbackReminderPending() {
        return feedbackReminderPending;
    }

    public void setFeedbackReminderPending(boolean b) {
        this.feedbackReminderPending = b;
    }

    // Display
    public void display() {
        System.out.println("ID       : " + id);
        System.out.println("Name     : " + name);
        System.out.println("Email    : " + email);
        System.out.println("Dept     : " + dept);
        System.out.println("College  : " + college);
        System.out.println("Course   : " + course);
        System.out.println("Sessions : " + registeredSessions.size());
        System.out.println("Feedback : " + feedbackHistory.size());
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    }
}
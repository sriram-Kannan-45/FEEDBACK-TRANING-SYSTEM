package com.trainingfeedback.model;

import java.util.ArrayList;

public class Participant extends User {

    private String email;
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
        registeredSessions.add(ts);
    }

    public boolean isRegisteredFor(TrainingSession ts) {
        return registeredSessions.contains(ts);
    }

    // Feedback history
    public void addFeedbackHistory(Feedback f) {
        if (f == null) {
            System.out.println("Error: Feedback cannot be null!");
            return;
        }
        feedbackHistory.add(f);
    }

    public ArrayList<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    // Feedback reminder
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
    }

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
}
package com.trainingfeedback.model;

import java.util.ArrayList;

public class Participant extends User {

	private String name;
	private String email;
	private String dept;
	private String college;
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
        registeredSessions.add(ts);
    }

    public boolean isRegisteredFor(TrainingSession ts) {
        return registeredSessions.contains(ts);
    }

    // Feedback history 
    public void addFeedbackHistory(Feedback f) {
        feedbackHistory.add(f);
    }

    public ArrayList<Feedback> getFeedbackHistory() {
        return feedbackHistory;
    }

    // Feedback reminder
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
    }

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public String getDept() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		
	}

	public String getCollege() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDept(String dept) {
		// TODO Auto-generated method stub
		
	}
}
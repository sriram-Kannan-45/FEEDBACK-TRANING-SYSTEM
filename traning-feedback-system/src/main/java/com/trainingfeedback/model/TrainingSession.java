package com.trainingfeedback.model;

import java.util.*;

public class TrainingSession {

    private int sessionId;
    private String title;
    private String startDate;
    private String endDate;
    private String time;
    private int duration;

    private Trainer trainer;

    private Set<Integer> feedbackGiven = new HashSet<>();
    private List<String> feedbackList = new ArrayList<>();

    public TrainingSession(int id, String title, String startDate, String endDate, String time, int duration) {
        this.sessionId = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.duration = duration;
    }

    public int getSessionId() { return sessionId; }
    public String getTitle() { return title; }

    public void assignTrainer(Trainer t) {
        this.trainer = t;
    }

    public Trainer getTrainer() { return trainer; }

    public boolean hasGivenFeedback(int participantId) {
        return feedbackGiven.contains(participantId);
    }

    public void addFeedback(int participantId, String feedback) {
        feedbackGiven.add(participantId);
        feedbackList.add(feedback);
    }

    public void viewFeedback() {
        for(String f : feedbackList)
            System.out.println(f);
    }

    public void displaySession() {
        System.out.println("\nSession ID: " + sessionId);
        System.out.println("Title: " + title);
        System.out.println("Start Date: " + startDate);
        System.out.println("End Date: " + endDate);
        System.out.println("Time: " + time);
        System.out.println("Duration: " + duration + " hrs");

        if(trainer != null)
            System.out.println("Trainer: " + trainer.getName());
    }
}
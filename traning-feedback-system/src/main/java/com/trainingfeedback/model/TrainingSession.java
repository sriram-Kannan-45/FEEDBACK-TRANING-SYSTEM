package com.trainingfeedback.model;

import java.util.*;

public class TrainingSession {

    private int sessionId;
    private String title;
    private Trainer trainer;

    private Set<Integer> feedbackGiven = new HashSet<>();
    private List<String> feedbackList = new ArrayList<>();

    public TrainingSession(int id, String title) {
        this.sessionId = id;
        this.title = title;
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
}
package com.trainingfeedback.model;

import java.util.*;

public class TrainingSession {

	private int sessionId;
    private String title, startDate, endDate, time;
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

    public void assignTrainer(Trainer t) { this.trainer = t; }
    public Trainer getTrainer() { return trainer; }
    public String getTitle() { return title; }

    public boolean hasGivenFeedback(int id) {
        return feedbackGiven.contains(id);
    }

    public void addFeedback(int id, String fb) {
        feedbackGiven.add(id);
        feedbackList.add(fb);
    }

    public void viewFeedback() {
        for(String f : feedbackList)
            System.out.println(f);
    }

    public void displaySession() {
        System.out.println(sessionId + " | " + title + " | " + startDate + " | Trainer: "
                + (trainer!=null ? trainer.getName() : "Not Assigned"));
    }
}
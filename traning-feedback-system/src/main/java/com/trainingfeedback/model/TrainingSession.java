package com.trainingfeedback.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainingSession {

    private int sessionId;
    private String title;
    private String startDate;
    private String endDate;
    private String time;
    private int duration;

    private Trainer trainer;

    // Track feedback submitted participants
    private Set<Integer> feedbackGiven = new HashSet<>();

    // Store feedbacks
    private List<Feedback> feedbackList = new ArrayList<>();

    // Participants
    private ArrayList<Participant> participants = new ArrayList<>();

    // Constructor
    public TrainingSession(int sessionId, String title,
                           String startDate, String endDate,
                           String time, int duration) {

        this.sessionId = sessionId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.time = time;
        this.duration = duration;
    }

    // Getters
    public int getSessionId() { return sessionId; }
    public String getTitle() { return title; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getTime() { return time; }
    public int getDuration() { return duration; }

    // Trainer
    public void assignTrainer(Trainer t) {
        this.trainer = t;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    // Participants
    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    // Check feedback submitted
    public boolean hasGivenFeedback(int participantId) {
        return feedbackGiven.contains(participantId);
    }

    // Add feedback
    public void addFeedback(Feedback f) {
        feedbackGiven.add(f.getParticipantId());
        feedbackList.add(f);
    }

    // Get feedback list
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    // View feedback
    public void viewSessionFeedback() {

        if (feedbackList.isEmpty()) {
            System.out.println("No feedback submitted yet.");
            return;
        }

        for (Feedback f : feedbackList) {
            System.out.println(f);
        }
    }

    // Feedback analytics
    public void printFeedbackAnalytics() {

        if (feedbackList.isEmpty()) {
            System.out.println("No feedback submitted yet.");
            return;
        }

        double total = 0;
        int count = feedbackList.size();

        for (Feedback f : feedbackList) {
            total += f.getRating();   // 🔥 important line
        }

        double avg = total / count;

        System.out.println("Total Feedbacks : " + count);
        System.out.println("Average Rating  : " + avg);
    }

    // Display session
    public void displaySession() {

        System.out.println("\nSession ID: " + sessionId);
        System.out.println("Title: " + title);
        System.out.println("Date: " + startDate + " to " + endDate);
        System.out.println("Time: " + time);
        System.out.println("Duration: " + duration + " hrs");

        if (trainer != null)
            System.out.println("Trainer: " + trainer.getName());
        else
            System.out.println("Trainer: Not Assigned");
    }
}
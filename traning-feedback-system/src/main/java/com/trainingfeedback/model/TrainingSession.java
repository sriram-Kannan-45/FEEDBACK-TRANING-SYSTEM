package com.trainingfeedback.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainingSession {

    private int    sessionId;
    private String title, startDate, endDate, time;
    private int    duration;

    private Trainer trainer;

    // Tracks which participant IDs have submitted feedback
    private Set<Integer> feedbackGiven = new HashSet<>();

    // Full Feedback objects — used for analytics + performance report
    private List<Feedback> feedbackList = new ArrayList<>();

    // Participants registered
    private ArrayList<Participant> participants = new ArrayList<>();

    public TrainingSession(int id, String title, String startDate,
                           String endDate, String time, int duration) {
        this.sessionId = id;
        this.title     = title;
        this.startDate = startDate;
        this.endDate   = endDate;
        this.time      = time;
        this.duration  = duration;
    }

    public int    getSessionId()  { return sessionId; }
    public String getTitle()      { return title; }
    public String getStartDate()  { return startDate; }
    public String getEndDate()    { return endDate; }
    public String getTime()       { return time; }
    public int    getDuration()   { return duration; }

    public void    assignTrainer(Trainer t) { this.trainer = t; }
    public Trainer getTrainer()             { return trainer; }

    public ArrayList<Participant> getParticipants() { return participants; }

    // ---- Feedback --------------------------------------------------------

    public boolean hasGivenFeedback(int participantId) {
        return feedbackGiven.contains(participantId);
    }

   
    public void addFeedback(Feedback f) {
        feedbackGiven.add(f.getParticipantId());
        feedbackList.add(f);
    }

    public List<Feedback> getFeedbackList() { return feedbackList; }

    // Session Feedback Analytics
    public void printFeedbackAnalytics() {
        if (feedbackList.isEmpty()) {
            System.out.println("  No feedback submitted yet.");
            return;
        }
        double total = 0;
        int count    = feedbackList.size();
        int[] dist   = new int[6];

        for (Feedback f : feedbackList) {
            int r = f.getRating();
            total += r;
            if (r >= 1 && r <= 5) dist[r]++;
        }

        double avg = total / count;
        System.out.printf("  Total Feedbacks : %d%n", count);
        System.out.printf("  Average Rating  : %.2f / 5.00%n", avg);
        System.out.println("  Rating Distribution:");
        for (int i = 5; i >= 1; i--) {
            String bar = "".repeat(dist[i]);
            System.out.printf("    %d star : %s (%d)%n", i, bar, dist[i]);
        }
    }

    // View all feedback
    public void viewSessionFeedback() {
        if (feedbackList.isEmpty()) {
            System.out.println("  No feedback submitted yet.");
            return;
        }
        for (Feedback f : feedbackList) {
            System.out.println("  " + f);
        }
    }

    public void displaySession() {
        System.out.println(sessionId + " | " + title + " | " + startDate
                + " - " + endDate + " | Trainer: "
                + (trainer != null ? trainer.getName() : "Not Assigned"));
    }
}
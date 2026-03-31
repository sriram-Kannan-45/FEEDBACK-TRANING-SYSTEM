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
<<<<<<< HEAD

=======
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
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
<<<<<<< HEAD

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
=======
        this.sessionId = sessionId;
        this.title     = title;
        this.startDate = startDate;
        this.endDate   = endDate;
        this.time      = time;
        this.duration  = duration;
    }

    // Getters
    public int    getSessionId() { return sessionId; }
    public String getTitle()     { return title; }
    public String getStartDate() { return startDate; }
    public String getEndDate()   { return endDate; }
    public String getTime()      { return time; }
    public int    getDuration()  { return duration; }

    // Trainer
    public void assignTrainer(Trainer t) {
        if (t == null) {
            System.out.println("Error: Trainer cannot be null!");
            return;
        }
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        this.trainer = t;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    // Participants
    public ArrayList<Participant> getParticipants() {
        return participants;
    }

<<<<<<< HEAD
=======
    public void addParticipant(Participant p) {
        if (p == null) {
            System.out.println("Error: Participant cannot be null!");
            return;
        }
        if (participants.contains(p)) {
            System.out.println("Participant already registered: " + p.getName());
            return;
        }
        participants.add(p);
    }

    public int getParticipantCount() {
        return participants.size();
    }

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    // Check feedback submitted
    public boolean hasGivenFeedback(int participantId) {
        return feedbackGiven.contains(participantId);
    }

    // Add feedback
    public void addFeedback(Feedback f) {
<<<<<<< HEAD
=======
        if (f == null) {
            System.out.println("Error: Feedback cannot be null!");
            return;
        }
        if (feedbackGiven.contains(f.getParticipantId())) {
            System.out.println("Feedback already submitted by this participant!");
            return;
        }
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        feedbackGiven.add(f.getParticipantId());
        feedbackList.add(f);
    }

    // Get feedback list
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    // View feedback
    public void viewSessionFeedback() {
<<<<<<< HEAD

=======
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback submitted yet.");
            return;
        }
<<<<<<< HEAD

=======
        System.out.println("--- Feedback for: " + title + " ---");
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        for (Feedback f : feedbackList) {
            System.out.println(f);
        }
    }

    // Feedback analytics
    public void printFeedbackAnalytics() {
<<<<<<< HEAD

=======
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback submitted yet.");
            return;
        }

        double total = 0;
        int count = feedbackList.size();

        for (Feedback f : feedbackList) {
<<<<<<< HEAD
            total += f.getRating();   // 🔥 important line
=======
            total += f.getRating();
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        }

        double avg = total / count;

        System.out.println("Total Feedbacks : " + count);
<<<<<<< HEAD
        System.out.println("Average Rating  : " + avg);
=======
        System.out.printf ("Average Rating  : %.2f / 5%n", avg);

        // Performance label
        if (avg >= 4.5) {
            System.out.println("Performance     : Excellent!");
        } else if (avg >= 3.5) {
            System.out.println("Performance     : Good");
        } else if (avg >= 2.5) {
            System.out.println("Performance     : Average - Needs Improvement");
        } else {
            System.out.println("Performance     : Poor - Please review feedback");
        }
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    }

    // Display session
    public void displaySession() {
<<<<<<< HEAD

        System.out.println("\nSession ID: " + sessionId);
        System.out.println("Title: " + title);
        System.out.println("Date: " + startDate + " to " + endDate);
        System.out.println("Time: " + time);
        System.out.println("Duration: " + duration + " hrs");

        if (trainer != null)
            System.out.println("Trainer: " + trainer.getName());
        else
            System.out.println("Trainer: Not Assigned");
=======
        System.out.println("\nSession ID    : " + sessionId);
        System.out.println("Title         : " + title);
        System.out.println("Date          : " + startDate + " to " + endDate);
        System.out.println("Time          : " + time);
        System.out.println("Duration      : " + duration + " hrs");
        System.out.println("Participants  : " + participants.size());
        System.out.println("Trainer       : " + (trainer != null ? trainer.getName() : "Not Assigned"));
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    }
}
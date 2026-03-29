package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;

public class ParticipantService {

    Scanner sc = new Scanner(System.in);

    // Register for Training Session
    public void registerForSession(Participant p) {

        System.out.println("\n--- Available Sessions ---");
        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No sessions available.");
            return;
        }

        for (TrainingSession ts : DataStorage.sessions.values()) {
            ts.displaySession();
        }

        System.out.print("Enter Session ID: ");
        int sid = sc.nextInt();

        TrainingSession ts = DataStorage.sessions.get(sid);

        if (ts == null) {
            System.out.println("Invalid Session ID!");
            return;
        }

        if (p.isRegisteredFor(ts)) {
            System.out.println("Already registered!");
            return;
        }

        p.registerSession(ts);
        ts.getParticipants().add(p);

        p.setFeedbackReminderPending(true);

        System.out.println("Registered successfully: " + ts.getTitle());
    }

    // Submit Feedback
    public void submitFeedback(Participant p) {

        if (p.getRegisteredSessions().isEmpty()) {
            System.out.println("No sessions registered.");
            return;
        }

        System.out.println("\n--- Your Sessions ---");
        for (TrainingSession ts : p.getRegisteredSessions()) {
            System.out.println(ts.getSessionId() + " | " + ts.getTitle());
        }

        System.out.print("Enter Session ID: ");
        int sid = sc.nextInt();
        sc.nextLine();

        TrainingSession ts = DataStorage.sessions.get(sid);

        if (ts == null || !p.isRegisteredFor(ts)) {
            System.out.println("Invalid session.");
            return;
        }

        if (ts.hasGivenFeedback(p.getId())) {
            System.out.println("Already submitted.");
            return;
        }

        System.out.print("Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        System.out.print("Comment: ");
        String comment = sc.nextLine();

        // SIMPLE constructor (important fix)
        Feedback f = new Feedback(p.getId(), rating, comment);

        ts.addFeedback(f);
        p.addFeedbackHistory(f);

        System.out.println("Feedback submitted!");
    }

    // View Feedback History
    public void viewFeedbackHistory(Participant p) {

        if (p.getFeedbackHistory().isEmpty()) {
            System.out.println("No feedback history.");
            return;
        }

        for (Feedback f : p.getFeedbackHistory()) {
            System.out.println(f);
        }
    }

    // Reminder
    public void checkFeedbackReminders(Participant p) {

        boolean pending = false;

        for (TrainingSession ts : p.getRegisteredSessions()) {
            if (!ts.hasGivenFeedback(p.getId())) {
                System.out.println("Pending: " + ts.getTitle());
                pending = true;
            }
        }

        if (!pending) {
            System.out.println("No pending feedback.");
        }
    }

    // Profile
    public void viewProfile(Participant p) {
        p.display();
    }
}
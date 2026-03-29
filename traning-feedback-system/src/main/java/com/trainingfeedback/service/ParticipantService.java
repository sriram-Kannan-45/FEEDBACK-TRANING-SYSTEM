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
            System.out.println("You are already registered for this session!");
            return;
        }

        // Register
        p.registerSession(ts);
        ts.getParticipants().add(p);

        // mark that this participant now has a session pending feedback
        p.setFeedbackReminderPending(true);

        System.out.println("Successfully registered for: " + ts.getTitle());

        // show immediate reminder
        System.out.println("[Reminder] Don't forget to submit feedback after the session!");
    }

    // Submit Training Feedback 
    public void submitFeedback(Participant p) {

        if (p.getRegisteredSessions().isEmpty()) {
            System.out.println("You have not registered for any session yet.");
            return;
        }

        System.out.println("\n--- Your Registered Sessions ---");
        for (TrainingSession ts : p.getRegisteredSessions()) {
            String status = ts.hasGivenFeedback(p.getId()) ? "[Feedback Submitted]" : "[Pending Feedback]";
            System.out.println("  " + ts.getSessionId() + " | " + ts.getTitle() + " " + status);
        }

        System.out.print("Session ID to submit feedback: ");
        int sid = sc.nextInt();
        sc.nextLine();

        TrainingSession ts = DataStorage.sessions.get(sid);

        if (ts == null) {
            System.out.println("Invalid Session ID.");
            return;
        }

        if (!p.isRegisteredFor(ts)) {
            System.out.println("You are not registered for this session.");
            return;
        }

        if (ts.hasGivenFeedback(p.getId())) {
            System.out.println("You have already submitted feedback for this session.");
            return;
        }

        // Collect session rating
        System.out.print("Session Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please enter a value between 1 and 5.");
            return;
        }

        System.out.print("Comment: ");
        String comment = sc.nextLine();

        //create and store proper Feedback object
        Feedback f = new Feedback(p.getId(), p.getName(),
                                   ts.getSessionId(), ts.getTitle(),
                                   rating, comment);

        // Instructor Evaluation
        if (ts.getTrainer() != null) {
            System.out.print("Instructor Rating (1-5): ");
            int instrRating = sc.nextInt();
            sc.nextLine();

            System.out.print("Instructor Comment: ");
            String instrComment = sc.nextLine();

            f.setInstructorEvaluation(instrRating, instrComment);
        }

        ts.addFeedback(f);               
        p.addFeedbackHistory(f);     

        // clear reminder flag if all registered sessions have feedback
        boolean allDone = p.getRegisteredSessions().stream()
                           .allMatch(s -> s.hasGivenFeedback(p.getId()));
        p.setFeedbackReminderPending(!allDone);

        // notify admin
        String notification = "New feedback by " + p.getName()
                + " for session '" + ts.getTitle() + "' | Rating: " + rating;
        DataStorage.notifyAdmin(notification);
        System.out.println("[Admin Notified] Feedback recorded.");

        System.out.println("Feedback submitted successfully!");
    }

    //View Feedback History
    public void viewFeedbackHistory(Participant p) {

        System.out.println("\n--- Your Feedback History ---");

        if (p.getFeedbackHistory().isEmpty()) {
            System.out.println("You have not submitted any feedback yet.");
            return;
        }

        for (Feedback f : p.getFeedbackHistory()) {
            System.out.println("  Session : " + f.getSessionTitle());
            System.out.println("  Rating  : " + f.getRating() + "/5");
            System.out.println("  Comment : " + f.getComment());
            if (f.hasInstructorEval()) {
                System.out.println("  Instructor Rating  : " + f.getInstructorRating() + "/5");
                System.out.println("  Instructor Comment : " + f.getInstructorComment());
            }
            System.out.println("  --------------------");
        }
    }

    //  Feedback Reminder Notification
    public void checkFeedbackReminders(Participant p) {

        boolean hasUnsubmitted = false;

        for (TrainingSession ts : p.getRegisteredSessions()) {
            if (!ts.hasGivenFeedback(p.getId())) {
                System.out.println("[REMINDER] You have not submitted feedback for: "
                        + ts.getTitle());
                hasUnsubmitted = true;
            }
        }

        if (!hasUnsubmitted) {
            System.out.println("No pending feedback reminders.");
        }
    }

    // View profile
    public void viewProfile(Participant p) {
        System.out.println("\n--- Your Profile ---");
        p.display();
        System.out.println("Sessions Registered : " + p.getRegisteredSessions().size());
        System.out.println("Feedbacks Submitted : " + p.getFeedbackHistory().size());
    }
}
package com.trainingfeedback.service;

import com.trainingfeedback.model.*;

public class TrainerService {

    // View trainer's courses
    public void viewCourses(Trainer t) {
        System.out.println("\n--- Your Courses ---");
        if (t.getCourses().isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        for (String c : t.getCourses()) {
            System.out.println("  " + c);
        }
    }

    // View all participants
    public void viewParticipants() {
        System.out.println("\n--- All Participants ---");
        if (DataStorage.participants.isEmpty()) {
            System.out.println("No participants found.");
            return;
        }
        for (Participant p : DataStorage.participants) {
            p.display();
            System.out.println();
        }
    }

    // View sessions assigned to this trainer + their feedback
    public void viewMySessions(Trainer t) {

        System.out.println("\n--- My Sessions ---");
        boolean found = false;

        for (TrainingSession ts : DataStorage.sessions.values()) {
            if (ts.getTrainer() != null && ts.getTrainer().getId() == t.getId()) {
                found = true;
                ts.displaySession();
                System.out.println("  Participants : " + ts.getParticipants().size());
                System.out.println("  Feedback:");
                ts.viewSessionFeedback();
                System.out.println();
            }
        }

        if (!found) {
            System.out.println("No sessions assigned to you.");
        }
    }
}
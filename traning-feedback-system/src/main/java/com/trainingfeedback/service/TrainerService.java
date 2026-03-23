package com.trainingfeedback.service;

import com.trainingfeedback.model.*;

public class TrainerService {

    public void viewCourses(Trainer t) {
        for (String c : t.getCourses()) {
            System.out.println(c);
        }
    }

    public void viewParticipants() {
        for (Participant p : DataStorage.participants) {
            p.display();
        }
    }

    public void viewMySessions(Trainer t) {

        for (TrainingSession ts : DataStorage.sessions.values()) {

            if (ts.getTrainer() == t) {

                System.out.println("\nSession: " + ts.getTitle());
                ts.viewFeedback();
            }
        }
    }
}
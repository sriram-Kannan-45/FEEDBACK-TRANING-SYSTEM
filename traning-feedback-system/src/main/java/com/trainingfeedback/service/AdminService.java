package com.trainingfeedback.service;

import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.model.Participant;

public class AdminService {

    // View Participants
    public void viewParticipants() {

        System.out.println("\nParticipant List");

        for (Participant p : DataStorage.participants) {
            p.display();
        }
    }

    // View Trainers
    public void viewTrainers() {

        System.out.println("\nTrainer List");

        for (Trainer t : DataStorage.trainers) {
            t.display();
        }
    }

    // Approve Trainer
    public void approveTrainer(int id) {

        for (Trainer t : DataStorage.trainers) {

            if (t.getId() == id) {
                t.setApproved(true);
                System.out.println("Trainer Approved Successfully");
                return;
            }
        }

        System.out.println("Trainer not found");
    }
}
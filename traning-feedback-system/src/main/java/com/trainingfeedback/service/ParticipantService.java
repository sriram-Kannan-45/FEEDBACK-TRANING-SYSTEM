package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;

public class ParticipantService {

    Scanner sc = new Scanner(System.in);

    public void submitFeedback(Participant p) {

        System.out.print("Session ID: ");
        int sid = sc.nextInt();
        sc.nextLine();

        TrainingSession ts = DataStorage.sessions.get(sid);

        if (ts == null) {
            System.out.println("Invalid Session ❌");
            return;
        }

        if (ts.hasGivenFeedback(p.getId())) {
            System.out.println("Already Submitted ❌");
            return;
        }

        System.out.print("Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        System.out.print("Comment: ");
        String comment = sc.nextLine();

        String fb = "Rating: " + rating +
                    " | By: " + p.getName() +
                    " | " + comment;

        ts.addFeedback(p.getId(), fb);

        System.out.println("Feedback Submitted ✅");
    }

    public void viewProfile(Participant p) {
        p.display();
    }

    public void viewFeedback(Participant p) {
        System.out.println("👉 Feedback is stored per session now");
    }
}
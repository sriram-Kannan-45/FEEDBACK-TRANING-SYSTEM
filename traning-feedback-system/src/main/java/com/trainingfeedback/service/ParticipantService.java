package com.trainingfeedback.service;

package com.trainingfeedback.service;

import com.trainingfeedback.model.Participant;

public class ParticipantService {

    public void submitFeedback(Participant p, String feedback) {

        p.addFeedback(feedback);

        System.out.println("Feedback submitted successfully");
    }

    public void viewFeedback(Participant p) {

        if (p.getFeedbackList().isEmpty()) {

            System.out.println("No feedback submitted yet");

        } else {

            System.out.println("Your Feedback History:");

            for (String f : p.getFeedbackList()) {
                System.out.println("- " + f);
            }
        }
    }

    public void viewProfile(Participant p) {

        System.out.println("\n===== Participant Profile =====");

        p.display();
    }
}

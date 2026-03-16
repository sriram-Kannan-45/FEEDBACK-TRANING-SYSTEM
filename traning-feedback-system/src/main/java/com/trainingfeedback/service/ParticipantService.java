package com.trainingfeedback.service;

import com.trainingfeedback.model.Participant;

public class ParticipantService {

    public void submitFeedback(Participant p,String feedback){

        p.addFeedback(feedback);
        System.out.println("Feedback Submitted");
    }

    public void viewFeedback(Participant p){

        for(String f:p.getFeedbackList())
            System.out.println(f);
    }

    public void viewProfile(Participant p){

        p.display();
    }
}
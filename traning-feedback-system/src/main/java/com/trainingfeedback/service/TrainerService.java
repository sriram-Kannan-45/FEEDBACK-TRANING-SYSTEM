package com.trainingfeedback.service;

import com.trainingfeedback.model.*;

public class TrainerService {

    public void viewCourses(Trainer t){

        for(String c:t.getCourses())
            System.out.println(c);
    }

    public void viewParticipants(){

        for(Participant p:DataStorage.participants)
            p.display();
    }
}
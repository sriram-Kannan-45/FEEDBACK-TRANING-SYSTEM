package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService {

    Scanner sc = new Scanner(System.in);

    public void createTrainer(){

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Trainer Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        Trainer t = new Trainer(id,name,pass);
        t.addCourse(course);

        DataStorage.trainers.add(t);

        System.out.println("Trainer Created");
    }

    public void viewTrainers(){

        for(Trainer t:DataStorage.trainers)
            t.display();
    }

    public void viewParticipants(){

        for(Participant p:DataStorage.participants)
            p.display();
    }

    public void approveTrainer(){

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        for(Trainer t:DataStorage.trainers){

            if(t.getId()==id){

                t.setApproved(true);
                System.out.println("Trainer Approved");
                return;
            }
        }

        System.out.println("Trainer not found");
    }
}
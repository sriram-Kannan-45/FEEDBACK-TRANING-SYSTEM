package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService {

    Scanner sc = new Scanner(System.in);

    public void createTrainer(){

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        // ✅ ONLY ID CHECK
        if(DataStorage.trainers.containsKey(id)){
            System.out.println("Error: Trainer ID already exists!");
            return;
        }

        System.out.print("Trainer Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        Trainer t = new Trainer(id,name,pass);
        t.addCourse(course);

        DataStorage.trainers.put(id, t);

        System.out.println("Trainer Created Successfully ✅");
    }

    public void viewTrainers(){
        for(Trainer t : DataStorage.trainers.values()){
            t.display();
        }
    }

    public void viewParticipants(){
        for(Participant p:DataStorage.participants){
            p.display();
        }
    }

    public void approveTrainer(){

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        Trainer t = DataStorage.trainers.get(id);

        if(t != null){
            t.setApproved(true);
            System.out.println("Trainer Approved ✅");
        } else {
            System.out.println("Trainer not found ❌");
        }
    }
}
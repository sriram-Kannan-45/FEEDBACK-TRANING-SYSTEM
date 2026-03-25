package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService {

    Scanner sc = new Scanner(System.in);
    
    public void createTrainer() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        if (DataStorage.trainers.containsKey(id)) {
            System.out.println("Trainer ID already exists!");
            return;
        }

        System.out.print("Trainer Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        Trainer t = new Trainer(id, name, pass);
        t.addCourse(course);

        DataStorage.trainers.put(id, t);

        System.out.println("Trainer Created Successfully ");
    }

    public void viewTrainers() {

        if (DataStorage.trainers.isEmpty()) {
            System.out.println("No Trainers Available");
            return;
        }

        for (Trainer t : DataStorage.trainers.values()) {
            t.display();
        }
    }

    public void viewParticipants() {

        if (DataStorage.participants.isEmpty()) {
            System.out.println("No Students Registered");
            return;
        }

        for (Participant p : DataStorage.participants) {
            p.display();
        }
    }

    public void approveTrainer() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        Trainer t = DataStorage.trainers.get(id);

        if (t != null) {
            t.setApproved(true);
            System.out.println("Trainer Approved Successfully ");
        } else {
            System.out.println("Trainer not found ");
        }
    }

    public void createSession() {

        System.out.print("Session ID: ");
        int id = sc.nextInt();

        if (DataStorage.sessions.containsKey(id)) {
            System.out.println("Session already exists!");
            return;
        }

        System.out.print("Title: ");
        String title = sc.next();

        System.out.print("Start Date: ");
        String start = sc.next();

        System.out.print("End Date: ");
        String end = sc.next();

        System.out.print("Time: ");
        String time = sc.next();

        System.out.print("Duration (hrs): ");
        int duration = sc.nextInt();

        TrainingSession ts = new TrainingSession(id, title, start, end, time, duration);


        System.out.print("Enter Trainer ID to assign: ");
        int tid = sc.nextInt();

        Trainer t = DataStorage.trainers.get(tid);

        if (t != null && t.isApproved()) {
            ts.assignTrainer(t);
            System.out.println("Trainer Assigned ");
        } else {
            System.out.println("Trainer invalid or not approved ");
        }

        DataStorage.sessions.put(id, ts);

        System.out.println("Session Created Successfully ");
    }


    public void assignTrainer() {

        System.out.print("Session ID: ");
        int sid = sc.nextInt();

        System.out.print("Trainer ID: ");
        int tid = sc.nextInt();

        TrainingSession ts = DataStorage.sessions.get(sid);
        Trainer t = DataStorage.trainers.get(tid);

        if (ts == null || t == null) {
            System.out.println("Invalid IDs ");
            return;
        }

        if (!t.isApproved()) {
            System.out.println("Trainer not approved ");
            return;
        }

        ts.assignTrainer(t);

        System.out.println("Trainer Assigned Successfully ");
    }


    public void viewSessions() {

        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No Sessions Available");
            return;
        }

        for (TrainingSession ts : DataStorage.sessions.values()) {
            ts.displaySession();
        }
    }

    public void viewReports() {

        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No Sessions Available");
            return;
        }

        for (TrainingSession ts : DataStorage.sessions.values()) {

            System.out.println("\nSession: " + ts.getTitle());

            if (ts.getTrainer() != null)
                System.out.println("Trainer: " + ts.getTrainer().getName());

            ts.viewFeedback();
        }
    }
}
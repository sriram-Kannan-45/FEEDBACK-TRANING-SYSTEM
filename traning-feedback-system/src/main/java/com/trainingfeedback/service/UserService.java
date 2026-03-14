package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.TrainerDashboard;

public class UserService {

    Scanner sc = new Scanner(System.in);
    
    public void trainerLogin() {

        System.out.println("Enter Trainer ID:");
        int id = sc.nextInt();

        System.out.println("Enter Password:");
        String pass = sc.next();

        for (Trainer t : DataStorage.trainers) {

            if (t.getId() == id && t.getPassword().equals(pass)) {

                if (t.isApproved()) {

                    System.out.println("Trainer Login Success");

                    TrainerDashboard td = new TrainerDashboard();
                    td.menu(t);

                } else {

                    System.out.println("Trainer not approved by Admin");

                }

                return;
            }
        }

        System.out.println("Invalid Trainer Login");
    }

    public void adminLogin() {

        System.out.println("Enter Admin ID:");
        int id = sc.nextInt();

        System.out.println("Enter Password:");
        String pass = sc.next();

        if (DataStorage.admin.getId() == id &&
            DataStorage.admin.getPassword().equals(pass)) {

            System.out.println("Admin Login Success");
        } else {
            System.out.println("Invalid Admin Login");
        }
    }

    public void registerParticipant() {

        System.out.println("Enter ID:");
        int id = sc.nextInt();

        System.out.println("Enter Name:");
        String name = sc.next();

        System.out.println("Enter Password:");
        String pass = sc.next();

        DataStorage.participants.add(new Participant(id, name, pass));

        System.out.println("Participant Registered Successfully");
    }

    public void loginParticipant() {

        System.out.println("Enter ID:");
        int id = sc.nextInt();

        System.out.println("Enter Password:");
        String pass = sc.next();

        for (Participant p : DataStorage.participants) {

            if (p.getId() == id && p.getPassword().equals(pass)) {
                System.out.println("Participant Login Success");
                return;
            }
        }

        System.out.println("Invalid Login");
    }
}
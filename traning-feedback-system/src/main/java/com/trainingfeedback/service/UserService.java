package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.*;

public class UserService {

    Scanner sc = new Scanner(System.in);

    // 1. Admin Login
    public void adminLogin() {

        System.out.print("Admin ID   : ");
        int id = sc.nextInt();

        System.out.print("Password   : ");
        String pass = sc.next();

        if (DataStorage.admin.getId() == id
                && DataStorage.admin.getPassword().equals(pass)) {

            System.out.println("Admin login successful.");
            new AdminDashboard().menu();

        } else {
            System.out.println("Unauthorized access.");
        }
    }

    // 2. Trainer Login
    public void trainerLogin() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Password   : ");
        String pass = sc.next();

        Trainer t = DataStorage.trainers.get(id);

        if (t != null && t.getPassword().equals(pass)) {
            if (t.isApproved()) {
                System.out.println("Trainer login successful.");
                new TrainerDashboard().menu(t);
            } else {
                System.out.println("Your account is not yet approved by the admin.");
            }
        } else {
            System.out.println("Unauthorized access.");
        }
    }

    // 3. Register Participant
    public void registerParticipant() {

        System.out.print("ID       : ");
        int id = sc.nextInt();

        // check duplicate ID
        boolean idExists = DataStorage.participants.stream()
                            .anyMatch(p -> p.getId() == id);
        if (idExists) {
            System.out.println("Error: Participant ID already exists!");
            return;
        }

        System.out.print("Name     : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Email    : ");
        String email = sc.next();

        System.out.print("Course   : ");
        String course = sc.next();

        Participant p = new Participant(id, name, pass, email, course);
        DataStorage.participants.add(p);

        System.out.println("Registration successful! You can now login.");
    }

    // 4. Login Participant
    public void loginParticipant() {

        System.out.print("ID       : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        for (Participant p : DataStorage.participants) {
            if (p.getId() == id && p.getPassword().equals(pass)) {
                System.out.println("Login successful. Welcome, " + p.getName() + "!");

                // show reminder on login if pending
                if (p.isFeedbackReminderPending()) {
                    System.out.println("\n[REMINDER] You have sessions pending feedback!");
                }

                new ParticipantDashboard().menu(p);
                return;
            }
        }

        System.out.println("Unauthorized access.");
    }
}
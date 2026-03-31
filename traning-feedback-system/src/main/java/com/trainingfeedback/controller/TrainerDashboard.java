package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.service.TrainerService;

public class TrainerDashboard {

    public void menu(Trainer t) {

        Scanner sc = new Scanner(System.in);
        TrainerService service = new TrainerService();

        while (true) {

            System.out.println("\n===== Trainer Dashboard =====");
            System.out.println("1  View My Courses");
            System.out.println("2  View All Students");
            System.out.println("3  View My Sessions & Feedback");
            System.out.println("4  Logout");

            System.out.print("Choice : ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: service.viewCourses(t);      break;
                case 2: service.viewParticipants();  break;
                case 3: service.viewMySessions(t);   break;
                case 4: System.out.println("Logged out."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }
}
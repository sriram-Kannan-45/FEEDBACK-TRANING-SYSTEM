package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.service.TrainerService;

public class TrainerDashboard {

    Scanner sc = new Scanner(System.in);
    TrainerService service = new TrainerService();

    public void menu(Trainer t) {

        while (true) {

            System.out.println("\n===== Trainer Dashboard =====");
            System.out.println("1 View Courses");
            System.out.println("2 View Students");
            System.out.println("3 View My Sessions");
            System.out.println("4 Logout");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: service.viewCourses(t); break;
                case 2: service.viewParticipants(); break;
                case 3: service.viewMySessions(t); break;
                case 4: return;
                default: System.out.println("Invalid Choice");
            }
        }
    }
}
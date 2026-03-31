package com.trainingfeedback.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.service.TrainerService;
import com.trainingfeedback.util.InputUtil;

public class TrainerDashboard {

    public void menu(Trainer t) {

        Scanner sc = new Scanner(System.in);
        TrainerService service = new TrainerService();
        int choice = 0;
        boolean fileMode = InputUtil.isFileMode();

        while (true) {
            System.out.println("\n===== Trainer Dashboard =====");
            System.out.println("1  View My Courses");
            System.out.println("2  View All Students");
            System.out.println("3  View My Sessions & Feedback");
            System.out.println("4  Logout");

            System.out.print("Choice : ");
            
            if (fileMode) {
                choice = InputUtil.nextInt();
                if (choice == -1) continue;
            } else {
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    sc.nextLine();
                    continue;
                }
            }

            switch (choice) {
                case 1:
                    service.viewCourses(t);
                    break;
                case 2:
                    service.viewParticipants();
                    break;
                case 3:
                    service.viewMySessions(t);
                    break;
                case 4:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Error: Invalid choice. Please select 1-4.");
            }
        }
    }
}
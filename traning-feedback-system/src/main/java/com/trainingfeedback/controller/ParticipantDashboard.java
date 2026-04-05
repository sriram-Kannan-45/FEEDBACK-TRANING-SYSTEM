package com.trainingfeedback.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.trainingfeedback.model.Participant;
import com.trainingfeedback.service.ParticipantService;
import com.trainingfeedback.util.InputUtil;

public class ParticipantDashboard {

    public void menu(Participant p) {

        Scanner sc = new Scanner(System.in);
        ParticipantService service = new ParticipantService();
        int choice = 0;
        boolean fileMode = InputUtil.isFileMode();

        while (true) {
            System.out.println("\n===== Student Dashboard =====");
            System.out.println("1  View Profile");
            System.out.println("2  Register for Session");
            System.out.println("3  Submit Feedback");
            System.out.println("4  View Feedback History");
            System.out.println("5  Check Feedback Reminders");
            System.out.println("6  Take Survey");
            System.out.println("7  Logout");

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
                    service.viewProfile1(p);
                    break;
                case 2:
                    service.registerForSession(p);
                    break;
                case 3:
                    service.submitFeedback(p);
                    break;
                case 4:
                    service.viewFeedbackHistory(p);
                    break;
                case 5:
                    service.checkFeedbackReminders(p);
                    break;
                case 6:
                    service.takeSurvey(p);
                    break;
                case 7:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Error: Invalid choice. Please select 1-7.");
            }
        }
    }
}
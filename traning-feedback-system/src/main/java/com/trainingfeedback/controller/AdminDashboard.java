package com.trainingfeedback.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.trainingfeedback.service.AdminService;
import com.trainingfeedback.util.InputUtil;

public class AdminDashboard {

    public void menu() {

        Scanner sc = new Scanner(System.in);
        AdminService service = new AdminService();
        int choice = 0;
        boolean fileMode = InputUtil.isFileMode();

        while (true) {

            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("1  Create Trainer");
            System.out.println("2  View Trainers");
            System.out.println("3  View Students");
            System.out.println("4  Approve Trainer");
            System.out.println("5  Delete Trainer");
            System.out.println("6  Delete Student");
            System.out.println("7  Create Session");
            System.out.println("8  Delete Session");
            System.out.println("9  View Session Reports");
            System.out.println("10 Trainer Performance Report");
            System.out.println("11 Session Feedback Analytics");
            System.out.println("12 Create Survey");
            System.out.println("13 View All Surveys");
            System.out.println("14 Clear All Data");
            System.out.println("15 Logout");

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
                    service.createTrainer();
                    break;

                case 2:
                    service.viewTrainers();
                    break;

                case 3:
                    service.viewParticipants();
                    break;

                case 4:
                    service.approveTrainer();
                    break;

                case 5:
                    service.deleteTrainer();
                    break;

                case 6:
                    service.deleteParticipant();
                    break;

                case 7:
                    service.createSession();
                    break;

                case 8:
                    service.deleteSession();
                    break;

                case 9:
                    service.viewSessionReports();
                    break;

                case 10:
                    service.viewTrainerPerformance();
                    break;

                case 11:
                    service.viewSessionFeedbackAnalytics();
                    break;

                case 12:
                    service.createSurvey();
                    break;

                case 13:
                    service.viewAllSurveys();
                    break;

                case 14:
                    service.clearAllData();
                    break;

                case 15:
                    System.out.println("Logged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
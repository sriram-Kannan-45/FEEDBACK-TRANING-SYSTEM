package com.trainingfeedback.controller;
import com.trainingfeedback.service.AdminService;
import java.util.Scanner;
import com.trainingfeedback.service.AdminService;

public class AdminDashboard {

    private AdminService adminService;
    private Scanner sc;

    public AdminDashboard() {
        adminService = new AdminService();
        sc = new Scanner(System.in);
    }

    public void showDashboard() {
        int choice = -1;
        
        do {
            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("1. Manage Feedback Surveys");
            System.out.println("2. Manage Participants");
            System.out.println("3. View Trainers / Approve Trainers");
            System.out.println("4. Feedback Analysis & Reports");
            System.out.println("5. Instructor Evaluations");
            System.out.println("6. View Dashboard Metrics");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline
            
            switch (choice) {
            case 1:
                manageFeedbackSurveys();
                break;
            case 2:
                manageParticipants();
                break;
            case 3:
                manageTrainers();
                break;
            case 4:
                generateReports();
                break;
            case 5:
                viewInstructorEvaluations();
                break;
            case 6:
                viewDashboardMetrics();
                break;
            case 0:
                System.out.println("Exiting Admin Dashboard...");
                break;
            default:
                System.out.println("Invalid choice! Try again.");
                
        } 
        } while (choice != 0);
    }
    }

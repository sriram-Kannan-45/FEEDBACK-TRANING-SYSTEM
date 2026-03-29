package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.AdminService;

public class AdminDashboard {

    public void menu() {

        Scanner sc = new Scanner(System.in);
        AdminService service = new AdminService();

        while (true) {

            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("1  Create Trainer");
            System.out.println("2  View Trainers");
            System.out.println("3  View Students");
            System.out.println("4  Approve Trainer");
            System.out.println("5  Create Session");
            System.out.println("6  View Session Reports");
            System.out.println("7  Trainer Performance Report");   
            System.out.println("8  Session Feedback Analytics");   
            System.out.println("9  View Notifications");            
            System.out.println("10 Logout");

            System.out.print("Choice : ");
            int choice = sc.nextInt();

<<<<<<< HEAD
            switch (choice)
            {
                case 1: service.createTrainer(); break;
                case 2: service.viewTrainers(); break;
                case 3: service.viewParticipants(); break;
                case 4: service.approveTrainer(); break;
                case 5: service.createSession(); break;
                case 6: service.assignTrainer(); break;
                case 7: service.viewReports(); break;
                case 8: return;
                default: System.out.println("Invalid Choice");
=======
            switch (choice) {
                case 1:  service.createTrainer(); break;
                case 2:  service.viewTrainers();break;
                case 3:  service.viewParticipants();break;
                case 4:  service.approveTrainer();break;
                case 5:  service.createSession(); break;
                case 6:  service.viewSessionReports();break;
                case 7:  service.viewTrainerPerformance();break;
                case 8:  service.viewSessionFeedbackAnalytics(); break;
                case 9:  service.viewAdminNotifications();break;
                case 10: System.out.println("Logged out."); return;
                default: System.out.println("Invalid choice.");
>>>>>>> 51802582a260581689d9b3f200f00c920fa685f4
            }
        }
    }
}
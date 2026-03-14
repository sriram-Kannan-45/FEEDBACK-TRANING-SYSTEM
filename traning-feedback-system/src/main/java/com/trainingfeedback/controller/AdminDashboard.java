package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.AdminService;

public class AdminDashboard {

    public void showDashboard() {

        Scanner sc = new Scanner(System.in);
        AdminService service = new AdminService();

        while(true) {

            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("1 View Participants");
            System.out.println("2 View Trainers");
            System.out.println("3 Approve Trainer");
            System.out.println("4 Logout");

            int choice = sc.nextInt();

            switch(choice) {

                case 1:
                    service.viewParticipants();
                    break;

                case 2:
                    service.viewTrainers();
                    break;

                case 3:
                    System.out.println("Enter Trainer ID:");
                    int id = sc.nextInt();
                    service.approveTrainer(id);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
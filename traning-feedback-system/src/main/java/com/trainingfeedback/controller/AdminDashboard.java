package com.trainingfeedback.controller;
import com.trainingfeedback.service.AdminService;
import java.util.Scanner;
import com.trainingfeedback.service.AdminService;

public class AdminDashboard {

    public void menu(){

        Scanner sc = new Scanner(System.in);
        AdminService service = new AdminService();

        while(true){

            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("1 Create Trainer");
            System.out.println("2 View Trainers");
            System.out.println("3 View Students");
            System.out.println("4 Approve Trainer");
            System.out.println("5 Logout");

            System.out.print("Choice : ");
            int choice = sc.nextInt();

            switch(choice){

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
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }

    }
}
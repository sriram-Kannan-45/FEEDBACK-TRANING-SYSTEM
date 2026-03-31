package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.DBConnection;
import com.trainingfeedback.service.UserService;

public class MainSystem {

    public static void main(String[] args) {

        DBConnection.getConnection();

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        while (true) {

            System.out.println("\n===== Training Feedback System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Trainer Login");
            System.out.println("3. Student Login / Register");
            System.out.println("4. Exit");

            System.out.print("Choice : ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                sc.next();
                continue;
            }

            int role = sc.nextInt();

            switch (role) {

                case 1:
                    service.adminLogin();
                    break;

                case 2:
                    service.trainerLogin();
                    break;

                case 3:
                    System.out.println("\n1. Login");
                    System.out.println("2. Register");
                    System.out.print("Choice : ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid input!");
                        sc.next();
                        break;
                    }

                    int c = sc.nextInt();

                    if (c == 1) {
                        service.loginParticipant();
                    } else if (c == 2) {
                        service.registerParticipant();
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you! Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please choose between 1 and 4.");
            }
        }
    }
}
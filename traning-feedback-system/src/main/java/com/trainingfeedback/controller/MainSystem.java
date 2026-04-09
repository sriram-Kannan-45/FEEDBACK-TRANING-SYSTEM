/*
Project: Training Feedback System
Type: Console-Based Java Application

CMD: javac -d . *.java | java Main

Team:
Admin → Mylambikai
Trainer → Shamiha
Participant → Tamilarasu
JDBC → Sriram K

Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.trainingfeedback.service.DBConnection;
import com.trainingfeedback.service.UserService;
import com.trainingfeedback.util.InputUtil;

/*
Class: MainSystem
Module: Main Entry Point

Purpose: Entry point of the application, displays main menu
CMD: java com.trainingfeedback.controller.MainSystem
Flow: Main → UserService → Module → Service → DB
OOPS: Abstraction - hides service implementation
*/
public class MainSystem {

    public static void main(String[] args) {

        DBConnection.initializeDatabase();

        InputUtil.init("input.txt");
        boolean fileMode = InputUtil.isFileMode();
        
        if (fileMode) {
            System.out.println("Running in file input mode");
        }

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        while (true) {

            System.out.println("\n===== Training Feedback System =====");
            System.out.println("1  Admin");
            System.out.println("2  Trainer");
            System.out.println("3  Student");
            System.out.println("4  Exit");

            System.out.print("Choice : ");
            int role = 0;
            
            if (fileMode) {
                role = InputUtil.nextInt();
                if (role == -1) continue;
            } else {
                try {
                    role = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    sc.nextLine();
                    continue;
                }
            }

            switch (role) {

                case 1:
                    service.adminLogin();
                    break;

                case 2:
                    service.trainerLogin();
                    break;

                case 3:
                    if (fileMode) {
                        int c = InputUtil.nextInt();
                        if (c == -1) break;
                        if (c == 1) {
                            service.loginParticipant();
                        } else if (c == 2) {
                            service.registerParticipant();
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    } else {
                        System.out.println("1  Login");
                        System.out.println("2  Register");
                        System.out.print("Choice : ");
                        int c = 0;
                        try {
                            c = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Error: Invalid input. Please enter a number.");
                            sc.nextLine();
                            continue;
                        }

                        if (c == 1) {
                            service.loginParticipant();
                        } else if (c == 2) {
                            service.registerParticipant();
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    InputUtil.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.DBConnection;
import com.trainingfeedback.service.UserService;

public class MainSystem {

    public static void main(String[] args) {

<<<<<<< HEAD
        DBConnection.initializeDatabase();
=======
        DBConnection.getConnection();
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        while (true) {

            System.out.println("\n===== Training Feedback System =====");
<<<<<<< HEAD
            System.out.println("1  Admin");
            System.out.println("2  Trainer");
            System.out.println("3  Student");
            System.out.println("4  Exit");

            System.out.print("Choice : ");
=======
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

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            int role = sc.nextInt();

            switch (role) {

                case 1:
                    service.adminLogin();
                    break;

                case 2:
                    service.trainerLogin();
                    break;

                case 3:
<<<<<<< HEAD
                    System.out.println("1  Login");
                    System.out.println("2  Register");
                    System.out.print("Choice : ");
=======
                    System.out.println("\n1. Login");
                    System.out.println("2. Register");
                    System.out.print("Choice : ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid input!");
                        sc.next();
                        break;
                    }

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
                    int c = sc.nextInt();

                    if (c == 1) {
                        service.loginParticipant();
                    } else if (c == 2) {
                        service.registerParticipant();
                    } else {
                        System.out.println("Invalid choice.");
                    }
<<<<<<< HEAD

                    break;

                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
=======
                    break;

                case 4:
                    System.out.println("Thank you! Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Please choose between 1 and 4.");
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            }
        }
    }
}
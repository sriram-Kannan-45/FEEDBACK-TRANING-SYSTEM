package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.UserService;

public class MainSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();

        while(true){

            System.out.println("\n===== Training Feedback System =====");
            System.out.println("1 Admin");
            System.out.println("2 Trainer");
            System.out.println("3 Student");
            System.out.println("4 Exit");

            System.out.print("Choice : ");
            int role = sc.nextInt();
            switch(role){

                case 1:
                    service.adminLogin();
                    break;

                case 2:
                    service.trainerLogin();
                    break;

                case 3:

                    System.out.println("1 Login");
                    System.out.println("2 Register");

                    System.out.print("Choice : ");
                    int c = sc.nextInt();

                    if(c==1)
                        service.loginParticipant();
                    else if(c==2)
                        service.registerParticipant();
                    else
                        System.out.println("Invalid choice");

                    break;

                case 4:
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
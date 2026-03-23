package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Participant;
import com.trainingfeedback.service.ParticipantService;

public class ParticipantDashboard {

    Scanner sc = new Scanner(System.in);
    ParticipantService service = new ParticipantService();

    public void menu(Participant p){

        while(true){

            System.out.println("\n===== Student Dashboard =====");
            System.out.println("1 View Profile");
            System.out.println("2 Submit Feedback");
            System.out.println("3 View Feedback");
            System.out.println("4 Logout");

            System.out.print("Choice : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    service.viewProfile(p);
                    break;

                case 2:
                    service.submitFeedback(p);
                    break;

                case 3:
                    service.viewFeedback(p);
                    break;

                case 4:
                    System.out.println("Logout Success");
                    return;

                    
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
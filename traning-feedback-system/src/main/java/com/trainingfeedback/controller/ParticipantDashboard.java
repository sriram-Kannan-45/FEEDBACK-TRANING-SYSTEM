package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Participant;
import com.trainingfeedback.service.ParticipantService;

public class ParticipantDashboard {

    public void menu(Participant p) {

        Scanner sc = new Scanner(System.in);
        ParticipantService service = new ParticipantService();

        while (true) {

            System.out.println("\n===== Student Dashboard =====");
            System.out.println("1  View Profile");
            System.out.println("2  Register for Session");     
            System.out.println("3  Submit Feedback");       
            System.out.println("4  View Feedback History");      
            System.out.println("5  Check Feedback Reminders");  
            System.out.println("6  Logout");
            System.out.println("1 View Profile");
            System.out.println("2 Submit Feedback"); 
            System.out.println("3 View Feedback");
            System.out.println("4 Logout");


            System.out.print("Choice : ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    service.viewProfile(p);
                    break;

                case 2:

                    service.registerForSession(p);

                    service.submitFeedback(p); 

                    break;

                case 3:
                    service.submitFeedback(p);
                    break;

                case 4:
                    service.viewFeedbackHistory(p);
                    break;

                case 5:
                    service.checkFeedbackReminders(p);
                    break;

                case 6:
                    System.out.println("Logged out successfully.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
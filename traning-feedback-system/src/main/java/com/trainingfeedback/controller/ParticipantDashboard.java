package com.trainingfeedback.controller;

package com.trainingfeedback.controller;

import java.util.Scanner;

import com.trainingfeedback.model.Participant;
import com.trainingfeedback.service.ParticipantService;

public class ParticipantDashboard {

    Scanner sc = new Scanner(System.in);
    ParticipantService service = new ParticipantService();

    public void menu(Participant p) {

        while (true) {

            System.out.println("\n===== Participant Dashboard =====");
            System.out.println("1. View Profile");
            System.out.println("2. Submit Feedback");
            System.out.println("3. View Feedback History");
            System.out.println("4. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    service.viewProfile(p);
                    break;

                case 2:

                    System.out.println("Enter your feedback:");
                    String feedback = sc.nextLine();

                    service.submitFeedback(p, feedback);
                    break;

                case 3:
                    service.viewFeedback(p);
                    break;

                case 4:
                    System.out.println("Logout Successful");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}


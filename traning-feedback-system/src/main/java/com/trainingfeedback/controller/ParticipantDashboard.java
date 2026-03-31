package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Participant;
import com.trainingfeedback.service.ParticipantService;

public class ParticipantDashboard {

    public void menu(Participant p) {

        if (p == null) {
            System.out.println("Error: Participant session invalid! Please login again.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        ParticipantService service = new ParticipantService();

        while (true) {

            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║         PARTICIPANT DASHBOARD - TRAINING FEEDBACK SYSTEM     ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  Welcome, " + padRight(p.getName(), 42) + "║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  1.  Profile Management                                       ║");
            System.out.println("║      ├─ 1.1 View My Profile                                  ║");
            System.out.println("║      └─ 1.2 Update Profile                                    ║");
            System.out.println("║                                                              ║");
            System.out.println("║  2.  Training Sessions                                        ║");
            System.out.println("║      ├─ 2.1 View Available Sessions                           ║");
            System.out.println("║      ├─ 2.2 Register for Session                              ║");
            System.out.println("║      ├─ 2.3 View My Sessions                                  ║");
            System.out.println("║      └─ 2.4 Cancel Registration                               ║");
            System.out.println("║                                                              ║");
            System.out.println("║  3.  Feedback                                                ║");
            System.out.println("║      ├─ 3.1 Submit Feedback                                   ║");
            System.out.println("║      ├─ 3.2 View Feedback History                            ║");
            System.out.println("║      ├─ 3.3 View Feedback Reports                            ║");
            System.out.println("║      └─ 3.4 Check Feedback Reminders                         ║");
            System.out.println("║                                                              ║");
            System.out.println("║  4.  Notifications                                            ║");
            System.out.println("║      └─ 4.1 View My Notifications                            ║");
            System.out.println("║                                                              ║");
            System.out.println("║  0.  Logout                                                   ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            System.out.print("\nEnter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1.1":
                    service.viewProfile(p);
                    break;
                case "1.2":
                    service.updateProfile(p);
                    break;

                case "2.1":
                    service.viewAvailableSessions();
                    break;
                case "2.2":
                    service.registerForSession(p);
                    break;
                case "2.3":
                    service.viewMySessions(p);
                    break;
                case "2.4":
                    service.cancelRegistration(p);
                    break;

                case "3.1":
                    service.submitFeedback(p);
                    break;
                case "3.2":
                    service.viewFeedbackHistory(p);
                    break;
                case "3.3":
                    service.viewFeedbackReports(p);
                    break;
                case "3.4":
                    service.checkFeedbackReminders(p);
                    break;

                case "4.1":
                    service.viewMyNotifications(p);
                    break;

                case "0":
                    System.out.println("Logged out successfully. Goodbye, " + p.getName() + "!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}

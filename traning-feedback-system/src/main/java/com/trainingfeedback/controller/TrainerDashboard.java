package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.service.TrainerService;

public class TrainerDashboard {

    public void menu(Trainer t) {

        if (t == null) {
            System.out.println("Error: Trainer session invalid! Please login again.");
            return;
        }

        if (!t.isApproved()) {
            System.out.println("Access Denied! Your account is not approved yet.");
            System.out.println("Please contact Admin for approval.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        TrainerService service = new TrainerService();

        while (true) {

            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║           TRAINER DASHBOARD - TRAINING FEEDBACK SYSTEM        ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  Welcome, " + padRight(t.getName(), 42) + "║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  1.  Profile Management                                       ║");
            System.out.println("║      ├─ 1.1 View My Profile                                    ║");
            System.out.println("║      ├─ 1.2 View My Courses                                    ║");
            System.out.println("║      └─ 1.3 Update Profile                                    ║");
            System.out.println("║                                                              ║");
            System.out.println("║  2.  Training Sessions                                        ║");
            System.out.println("║      ├─ 2.1 View My Sessions                                  ║");
            System.out.println("║      ├─ 2.2 View Session Details                              ║");
            System.out.println("║      └─ 2.3 View Attendance                                   ║");
            System.out.println("║                                                              ║");
            System.out.println("║  3.  Participants                                             ║");
            System.out.println("║      ├─ 3.1 View All Participants                              ║");
            System.out.println("║      └─ 3.2 Search Participant                                ║");
            System.out.println("║                                                              ║");
            System.out.println("║  4.  Feedback Management                                     ║");
            System.out.println("║      ├─ 4.1 View Feedback Report                              ║");
            System.out.println("║      ├─ 4.2 Respond to Feedback                               ║");
            System.out.println("║      └─ 4.3 View Session Feedback                             ║");
            System.out.println("║                                                              ║");
            System.out.println("║  5.  Instructor Evaluation                                    ║");
            System.out.println("║      └─ 5.1 View Instructor Evaluation Summary                ║");
            System.out.println("║                                                              ║");
            System.out.println("║  6.  Notifications                                            ║");
            System.out.println("║      └─ 6.1 View My Notifications                             ║");
            System.out.println("║                                                              ║");
            System.out.println("║  0.  Logout                                                   ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            System.out.print("\nEnter choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1.1":
                    service.viewProfile(t);
                    break;
                case "1.2":
                    service.viewCourses(t);
                    break;
                case "1.3":
                    service.updateProfile(t);
                    break;

                case "2.1":
                    service.viewMySessions(t);
                    break;
                case "2.2":
                    service.viewSessionDetails(t);
                    break;
                case "2.3":
                    service.viewAttendance(t);
                    break;

                case "3.1":
                    service.viewParticipants();
                    break;
                case "3.2":
                    System.out.print("Enter participant name to search: ");
                    String searchName = sc.nextLine();
                    service.searchParticipant(searchName);
                    break;

                case "4.1":
                    service.viewFeedbackReport(t);
                    break;
                case "4.2":
                    service.respondToFeedback(t);
                    break;
                case "4.3":
                    System.out.print("Enter Session ID: ");
                    int sid = sc.nextInt();
                    sc.nextLine();
                    service.viewSessionFeedbackSummary(sid);
                    break;

                case "5.1":
                    service.viewInstructorEvaluation(t);
                    break;

                case "6.1":
                    service.viewMyNotifications(t);
                    break;

                case "0":
                    System.out.println("Logged out successfully. Goodbye, " + t.getName() + "!");
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

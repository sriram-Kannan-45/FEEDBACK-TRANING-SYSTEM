package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.AdminService;

public class AdminDashboard {

    public void menu() {

        Scanner sc = new Scanner(System.in);
        AdminService service = new AdminService();

        while (true) {

<<<<<<< HEAD
            System.out.println("\n===== Admin Dashboard =====");
            System.out.println("1  Create Trainer");
            System.out.println("2  View Trainers");
            System.out.println("3  View Students");
            System.out.println("4  Approve Trainer");
            System.out.println("5  Create Session");
<<<<<<< HEAD
            System.out.println("6  Assign Trainer");
=======
            System.out.println("6  View Session Reports");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
            System.out.println("7  Trainer Performance Report");
            System.out.println("8  Session Feedback Analytics");
            System.out.println("9  View Notifications");
            System.out.println("10 Logout");

            System.out.print("Choice : ");
            int choice = sc.nextInt();

            switch (choice) {
<<<<<<< HEAD

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
                    service.createSession();
                    break;

                case 6:
                    service.assignTrainer();
                    break;

                case 7:
                    service.viewTrainerPerformance();
                    break;

                case 8:
                    service.viewSessionFeedbackAnalytics();
                    break;

                case 9:
                    service.viewAdminNotifications();
                    break;

                case 10:
=======
            System.out.println("\n");
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║              ADMIN DASHBOARD - TRAINING FEEDBACK SYSTEM      ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║  1.  Trainer Management                                      ║");
            System.out.println("║      ├─ 1.1 Create Trainer                                    ║");
            System.out.println("║      ├─ 1.2 View All Trainers                                ║");
            System.out.println("║      ├─ 1.3 Approve Trainer                                  ║");
            System.out.println("║      └─ 1.4 Deactivate Trainer                               ║");
            System.out.println("║                                                              ║");
            System.out.println("║  2.  Participant Management                                  ║");
            System.out.println("║      ├─ 2.1 View All Participants                            ║");
            System.out.println("║      ├─ 2.2 View Participant Details                         ║");
            System.out.println("║      └─ 2.3 Track Attendance                                  ║");
            System.out.println("║                                                              ║");
            System.out.println("║  3.  Session Management                                      ║");
            System.out.println("║      ├─ 3.1 Create Training Session                           ║");
            System.out.println("║      ├─ 3.2 View All Sessions                                ║");
            System.out.println("║      └─ 3.3 Assign Trainer to Session                        ║");
            System.out.println("║                                                              ║");
            System.out.println("║  4.  Survey Management                                       ║");
            System.out.println("║      ├─ 4.1 Create Survey                                     ║");
            System.out.println("║      ├─ 4.2 View All Surveys                                 ║");
            System.out.println("║      ├─ 4.3 Activate Survey                                   ║");
            System.out.println("║      └─ 4.4 Deactivate Survey                                ║");
            System.out.println("║                                                              ║");
            System.out.println("║  5.  Feedback & Reports                                      ║");
            System.out.println("║      ├─ 5.1 View Session Reports                             ║");
            System.out.println("║      ├─ 5.2 Trainer Performance Report                       ║");
            System.out.println("║      ├─ 5.3 Session Feedback Analytics                       ║");
            System.out.println("║      ├─ 5.4 Feedback Trends & Insights                       ║");
            System.out.println("║      └─ 5.5 Comprehensive Report                             ║");
            System.out.println("║                                                              ║");
            System.out.println("║  6.  Reminders & Notifications                                ║");
            System.out.println("║      ├─ 6.1 Send Feedback Reminders                          ║");
            System.out.println("║      └─ 6.2 View Notifications                               ║");
            System.out.println("║                                                              ║");
            System.out.println("║  7.  Clear Notifications                                     ║");
            System.out.println("║                                                              ║");
            System.out.println("║  0.  Logout                                                  ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");

            System.out.print("\nEnter main choice (0-7) or sub-choice (e.g., 1.1): ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1.1":
                    service.createTrainer();
                    break;
                case "1.2":
                    service.viewTrainers();
                    break;
                case "1.3":
                    service.approveTrainer();
                    break;
                case "1.4":
                    service.deactivateTrainer();
                    break;

                case "2.1":
                    service.viewParticipants();
                    break;
                case "2.2":
                    service.viewParticipantDetails();
                    break;
                case "2.3":
                    service.trackAttendance();
                    break;

                case "3.1":
                    service.createSession();
                    break;
                case "3.2":
                    service.viewSessions();
                    break;
                case "3.3":
                    service.assignTrainer();
                    break;

                case "4.1":
                    service.createSurvey();
                    break;
                case "4.2":
                    service.viewSurveys();
                    break;
                case "4.3":
                    service.activateSurvey();
                    break;
                case "4.4":
                    service.deactivateSurvey();
                    break;

                case "5.1":
                    service.viewSessionReports();
                    break;
                case "5.2":
                    service.viewTrainerPerformance();
                    break;
                case "5.3":
                    service.viewSessionFeedbackAnalytics();
                    break;
                case "5.4":
                    service.viewFeedbackTrends();
                    break;
                case "5.5":
                    service.generateComprehensiveReport();
                    break;

                case "6.1":
                    service.sendFeedbackReminders();
                    break;
                case "6.2":
                    service.viewAdminNotifications();
                    break;

                case "7":
                    service.clearNotifications();
                    break;

                case "0":
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
                    System.out.println("Logged out successfully.");
                    return;

                default:
<<<<<<< HEAD
                    System.out.println("Invalid choice. Try again.");
=======
                case 1: service.createTrainer(); break;
                case 2: service.viewTrainers(); break;
                case 3: service.viewParticipants(); break;
                case 4: service.approveTrainer(); break;
                case 5: service.createSession(); break;
                case 6: service.viewSessionReports(); break;
                case 7: service.viewTrainerPerformance(); break;
                case 8: service.viewSessionFeedbackAnalytics(); break;
                case 9: service.viewAdminNotifications(); break;
                case 10: 
                    System.out.println("Logged out.");
                    return;
                default: 
                    System.out.println("Invalid choice.");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
            }
        }
    }
}
=======
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

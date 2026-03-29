package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService 
{

    Scanner sc = new Scanner(System.in);

<<<<<<< HEAD
	public void createTrainer() 
	{
=======
    // 1. Create Trainer
    public void createTrainer() {
>>>>>>> 51802582a260581689d9b3f200f00c920fa685f4

        System.out.print("Trainer ID   : ");
        int id = sc.nextInt();

<<<<<<< HEAD
		
		if (DataStorage.trainers.containsKey(id))
		{
			System.out.println("Error: Trainer ID already exists!");
			return;
		}
=======
        if (DataStorage.trainers.containsKey(id)) {
            System.out.println("Error: Trainer ID already exists!");
            return;
        }
>>>>>>> 51802582a260581689d9b3f200f00c920fa685f4

        System.out.print("Trainer Name : ");
        String name = sc.next();

        System.out.print("Password     : ");
        String pass = sc.next();

        System.out.print("Course       : ");
        String course = sc.next();

        Trainer t = new Trainer(id, name, pass);
        t.addCourse(course);

<<<<<<< HEAD
		DataStorage.trainers.put(id, t); 
=======
        DataStorage.trainers.put(id, t);
        System.out.println("Trainer created successfully.");
    }
>>>>>>> 51802582a260581689d9b3f200f00c920fa685f4

    // 2. View Trainers
    public void viewTrainers() {
        if (DataStorage.trainers.isEmpty()) {
            System.out.println("No trainers found.");
            return;
        }
        for (Trainer t : DataStorage.trainers.values()) {
            t.display();
            System.out.println();
        }
    }

    // 3. View Participants
    public void viewParticipants() {
        if (DataStorage.participants.isEmpty()) {
            System.out.println("No participants found.");
            return;
        }
        for (Participant p : DataStorage.participants) {
            p.display();
            System.out.println();
        }
    }

    // 4. Approve Trainer
    public void approveTrainer() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        Trainer t = DataStorage.trainers.get(id);

        if (t == null) {
            System.out.println("Trainer not found.");
            return;
        }
        if (t.isApproved()) {
            System.out.println("Trainer is already approved.");
            return;
        }

        t.setApproved(true);
        System.out.println("Trainer '" + t.getName() + "' approved successfully.");
    }

    // 5. Create Session + Assign Trainer 
    public void createSession() {

        System.out.print("Session ID : ");
        int sid = sc.nextInt();

        if (DataStorage.sessions.containsKey(sid)) {
            System.out.println("Error: Session ID already exists!");
            return;
        }

        System.out.print("Title      : ");
        String title = sc.next();
        sc.nextLine();

        System.out.print("Start Date : ");
        String start = sc.next();
        sc.nextLine();

        System.out.print("End Date   : ");
        String end = sc.next();
        sc.nextLine();

        System.out.print("Time       : ");
        String time = sc.next();
        sc.nextLine();

        System.out.print("Duration (hrs) : ");
        int duration = sc.nextInt();

        TrainingSession ts = new TrainingSession(sid, title, start, end, time, duration);

        System.out.print("Assign Trainer ID (0 to skip): ");
        int tid = sc.nextInt();

        if (tid != 0) {
            Trainer trainer = DataStorage.trainers.get(tid);
            if (trainer == null) {
                System.out.println("Warning: Invalid Trainer ID. Session saved without trainer.");
            } else if (!trainer.isApproved()) {
                System.out.println("Warning: Trainer not approved. Session saved without trainer.");
            } else {
                ts.assignTrainer(trainer);
                System.out.println("Trainer '" + trainer.getName() + "' assigned.");
            }
        }

        DataStorage.sessions.put(sid, ts);
        System.out.println("Session created successfully.");
    }

    // 6. View Session Reports
    public void viewSessionReports() {

        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No sessions available.");
            return;
        }

        for (TrainingSession ts : DataStorage.sessions.values()) {
            System.out.println("\n============================");
            ts.displaySession();
            System.out.println("Feedback:");
            ts.viewSessionFeedback();
        }
    }

    // Session Feedback Analytics
    public void viewSessionFeedbackAnalytics() {

        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No sessions available.");
            return;
        }

        System.out.println("\n===== Session Feedback Analytics =====");

        for (TrainingSession ts : DataStorage.sessions.values()) {
            System.out.println("\nSession : " + ts.getTitle()
                    + " [" + ts.getStartDate() + " - " + ts.getEndDate() + "]");
            System.out.println("Trainer : "
                    + (ts.getTrainer() != null ? ts.getTrainer().getName() : "Not Assigned"));
            ts.printFeedbackAnalytics();
        }
    }

    // Trainer Performance Report
    public void viewTrainerPerformance() {

        System.out.print("Enter Trainer ID: ");
        int tid = sc.nextInt();

        Trainer trainer = DataStorage.trainers.get(tid);

        if (trainer == null) {
            System.out.println("Invalid Trainer ID.");
            return;
        }

        System.out.println("\n--- Trainer Performance Report ---");
        System.out.println("Trainer : " + trainer.getName());

        double totalRating = 0;
        double totalInstrRating = 0;
        int count = 0;
        int instrCount = 0;

        for (TrainingSession ts : DataStorage.sessions.values()) {
   
            if (ts.getTrainer() != null && ts.getTrainer().getId() == tid) {

                System.out.println("\n  Session: " + ts.getTitle());

                for (Feedback f : ts.getFeedbackList()) {
                    totalRating += f.getRating();
                    count++;

                    //include instructor evaluation in performance
                    if (f.hasInstructorEval()) {
                        totalInstrRating += f.getInstructorRating();
                        instrCount++;
                    }

                    System.out.println("    " + f.getParticipantName()
                            + " | Session: " + f.getRating() + "/5"
                            + (f.hasInstructorEval()
                               ? " | Instructor: " + f.getInstructorRating() + "/5" : "")
                            + " | " + f.getComment());
                }
            }
        }

        System.out.println("\n  Summary");
        System.out.println("  -------");
        if (count == 0) {
            System.out.println("  No feedback received yet.");
        } else {
            System.out.printf("  Total Feedbacks     : %d%n", count);
            System.out.printf("  Avg Session Rating  : %.2f / 5.00%n", totalRating / count);
            if (instrCount > 0)
                System.out.printf("  Avg Instructor Rating: %.2f / 5.00%n",
                        totalInstrRating / instrCount);
        }
    }

    //Admin Notification for New Feedback
    public void viewAdminNotifications() {

        System.out.println("\n===== Admin Notifications =====");

        if (DataStorage.adminNotifications.isEmpty()) {
            System.out.println("No new notifications.");
            return;
        }

        int i = 1;
        for (String note : DataStorage.adminNotifications) {
            System.out.println(i++ + ". " + note);
        }
    }
}
package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.service.AdminService;

public class AdminDashboard {

	public void showDashboard() {
		int choice = -1;
		do {
			System.out.println("\n===== Admin Dashboard =====");
			System.out.println("1. Manage Feedback Surveys");
			System.out.println("2. Create Feedback Surveys");
			System.out.println("3. Manage Participants");
			System.out.println("4. View participant");
			System.out.println("5. Manage Trainers");
			System.out.println("6. View Trainers");
			System.out.println("7. Generate Reports");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine(); // consume newline

			switch (choice) {
			case 1:
				adminService.manageFeedbackSurveys(); // Service handles logic
				break;
			case 2:
				adminService.createFeedbackSurveys(); // Service handles logic
				break;
			case 3:
				adminService.manageParticipants(); // Service handles logic
				break;
			case 4:
				adminService.viewParticipants(); // Service handles logic
				break;
			case 5:
				adminService.manageTrainers(); // Service handles logic
				break;
			case 6:
				adminService.viewTrainers(); // Service handles logic
				break;
			case 7:
				adminService.generateReports(); // Service handles logic
				break;
			case 0:
				System.out.println("Exiting Admin Dashboard...");
				break;
			default:
				System.out.println("Invalid choice!");
			}

		} while (choice != 0);
	}

}

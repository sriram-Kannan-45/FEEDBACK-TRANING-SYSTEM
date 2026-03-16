package com.trainingfeedback.service;

import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.model.Participant;

package service;

import java.util.*;

public class AdminService {

	Scanner sc = new Scanner(System.in);

	// Case 1
	public void manageFeedbackSurveys() {

		System.out.println("1. View Surveys");
		System.out.println("2. Delete Survey");
		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {

		case 1:
			if (surveys.isEmpty()) {
				System.out.println("No surveys available");
			} else {
				for (String s : surveys) {
					System.out.println(s);
				}
			}
			break;

		case 2:
			System.out.print("Enter survey name to delete: ");
			String name = sc.nextLine();

			if (surveys.remove(name)) {
				System.out.println("Survey deleted");
			} else {
				System.out.println("Survey not found");
			}
			break;
		}
	}

	// Case 2
	public void createFeedbackSurveys() {

		System.out.print("Enter Survey Name: ");
		String surveyName = sc.nextLine();

		surveys.add(surveyName);

		System.out.println("Survey created successfully");

		createQuestions(); // calling another method
	}

	public void createQuestions() {

		ArrayList<String> questions = new ArrayList<>();

		System.out.println("Enter number of questions:");
		int n = sc.nextInt();
		sc.nextLine();

		for (int i = 1; i <= n; i++) {

			System.out.print("Enter Question " + i + ": ");
			String q = sc.nextLine();
			questions.add(q);
		}

		System.out.println("Questions added successfully");

		System.out.println("Survey Questions:");
		for (String q : questions) {
			System.out.println(q);
		}
	}

	// Case 3
	public void manageParticipants() {

		System.out.println("1. Add Participant");
		System.out.println("2. Remove Participant");
		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {

		case 1:
			System.out.print("Enter participant name: ");
			String name = sc.nextLine();
			participants.add(name);
			System.out.println("Participant added");
			break;

		case 2:
			System.out.print("Enter participant name to remove: ");
			String remove = sc.nextLine();

			if (participants.remove(remove)) {
				System.out.println("Participant removed");
			} else {
				System.out.println("Participant not found");
			}
			break;
		}
	}

	// Case 4
	public void viewParticipants() {

		if (participants.isEmpty()) {
			System.out.println("No participants available");
		} else {
			for (String p : participants) {
				System.out.println(p);
			}
		}
	}

	// Case 5
	public void manageTrainers() {

		System.out.println("1. Add Trainer");
		System.out.println("2. Remove Trainer");
		int choice = sc.nextInt();
		sc.nextLine();

		switch (choice) {

		case 1:
			System.out.print("Enter trainer name: ");
			String trainer = sc.nextLine();
			trainers.add(trainer);
			System.out.println("Trainer added");
			break;

		case 2:
			System.out.print("Enter trainer name to remove: ");
			String remove = sc.nextLine();

			if (trainers.remove(remove)) {
				System.out.println("Trainer removed");
			} else {
				System.out.println("Trainer not found");
			}
			break;
		}
	}

	// Case 6
	public void viewTrainers() {

		if (trainers.isEmpty()) {
			System.out.println("No trainers available");
		} else {
			for (String t : trainers) {
				System.out.println(t);
			}
		}
	}

	// Case 7
	public void generateReports() {

		System.out.println("------ REPORT ------");

		System.out.println("Total Surveys: " + surveys.size());
		System.out.println("Total Participants: " + participants.size());
		System.out.println("Total Trainers: " + trainers.size());
	}
}
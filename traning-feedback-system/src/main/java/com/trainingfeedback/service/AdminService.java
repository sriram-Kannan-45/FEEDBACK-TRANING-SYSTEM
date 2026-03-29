package com.trainingfeedback.service;
import com.trainingfeedback.model.TrainingSession;
import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService {

	Scanner sc = new Scanner(System.in);
	//1. create trainer
	public void createTrainer() {

		System.out.print("Trainer ID : ");
		int id = sc.nextInt();

		//ONLY ID CHECK
		if (DataStorage.trainers.containsKey(id)) {
			System.out.println("Error: Trainer ID already exists!");
			return;
		}

		System.out.print("Trainer Name : ");
		String name = sc.next();

		System.out.print("Password : ");
		String pass = sc.next();

		System.out.print("Course : ");
		String course = sc.next();

		Trainer t = new Trainer(id, name, pass);
		t.addCourse(course);

		DataStorage.trainers.put(id, t);

		System.out.println("Trainer Created Successfully ");
	}
	
	//2. view report
	public void viewReports(){

	    for(TrainingSession ts : DataStorage.sessions.values()){

	        System.out.println("\nSession: " + ts.getTitle());

	        if(ts.getTrainer()!=null)
	            System.out.println("Trainer: " + ts.getTrainer().getName());

	        ts.viewFeedback();
	    }
	}
	//3. assign trainer
	public void assignTrainer() {

		System.out.print("Session ID: ");
		int sid = sc.nextInt();

		System.out.print("Trainer ID: ");
		int tid = sc.nextInt();

		TrainingSession ts = DataStorage.sessions.get(sid);
		Trainer t = DataStorage.trainers.get(tid);

		if (ts == null || t == null) {
			System.out.println("Invalid IDs ");
			return;
		}

		if (!t.isApproved()) {
			System.out.println("Trainer not approved ");
			return;
		}

		ts.assignTrainer(t);

		System.out.println("Trainer Assigned ");
	}
	//4. create session
	 public void createSession(){

	        System.out.print("Session ID: ");
	        int id = sc.nextInt();

	        System.out.print("Title: ");
	        String title = sc.next();

	        System.out.print("Start: ");
	        String s = sc.next();

	        System.out.print("End: ");
	        String e = sc.next();

	        System.out.print("Time: ");
	        String t = sc.next();

	        System.out.print("Duration: ");
	        int d = sc.nextInt();

	        TrainingSession ts = new TrainingSession(id,title,s,e,t,d);

	        System.out.print("Trainer ID: ");
	        int tid = sc.nextInt();

	        Trainer tr = DataStorage.trainers.get(tid);
	        if(tr!=null && tr.isApproved())
	            ts.assignTrainer(tr);

	        DataStorage.sessions.put(id,ts);

	        System.out.println("Session Created");
	    }
	 // 5. view trainers
	public void viewTrainers() {
		for (Trainer t : DataStorage.trainers.values()) {
			t.display();
		}
	}
	//6. view participants
	public void viewParticipants() {
		for (Participant p : DataStorage.participants) {
			p.display();
		}
	}
	//7. approve trainer
	public void approveTrainer() {

		System.out.print("Trainer ID : ");
		int id = sc.nextInt();

		Trainer t = DataStorage.trainers.get(id);

		if (t != null) {
			t.setApproved(true);
			System.out.println("Trainer Approved ");
		} else {
			System.out.println("Trainer not found ");
		}
	}
}
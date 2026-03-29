package com.trainingfeedback.service;

import java.util.ArrayList;
import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.dbconnection.AdminDAO;
import com.trainingfeedback.dbconnection.SessionDAO;
import com.trainingfeedback.dbconnection.TrainerDAO;

public class AdminService {

    Scanner sc = new Scanner(System.in);

    // ─────────────────────────────────────────
    // CREATE TRAINER
    // Before → DataStorage.trainers.put(id, t)
    // After  → saves to MySQL trainers table via AdminDAO
    // ─────────────────────────────────────────
    public void createTrainer() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Trainer Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        Trainer t = new Trainer(id, name, pass);

        AdminDAO dao = new AdminDAO();
        dao.createTrainer(t, course);
    }

    // ─────────────────────────────────────────
    // APPROVE TRAINER
    // Before → DataStorage.trainers.get(id) → t.setApproved(true)
    // After  → updates approved = TRUE in MySQL via AdminDAO
    // ─────────────────────────────────────────
    public void approveTrainer() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        AdminDAO dao = new AdminDAO();
        dao.approveTrainer(id);
    }

    // ─────────────────────────────────────────
    // ASSIGN TRAINER TO SESSION
    // Before → DataStorage.sessions.get() + DataStorage.trainers.get()
    // After  → validates from MySQL, updates via SessionDAO
    // ─────────────────────────────────────────
    public void assignTrainer() {

        System.out.print("Session ID: ");
        int sid = sc.nextInt();

        System.out.print("Trainer ID: ");
        int tid = sc.nextInt();

        // validate both exist in DB
        SessionDAO sessionDAO = new SessionDAO();
        TrainerDAO trainerDAO = new TrainerDAO();

        TrainingSession ts = sessionDAO.findById(sid);
        Trainer t = trainerDAO.findById(tid);

        if (ts == null || t == null) {
            System.out.println("Invalid IDs!");
            return;
        }

        if (!t.isApproved()) {
            System.out.println("Trainer not approved!");
            return;
        }

        sessionDAO.assignTrainer(sid, tid);
    }

    // ─────────────────────────────────────────
    // CREATE SESSION
    // Before → DataStorage.sessions.put(id, ts)
    // After  → saves to MySQL sessions table via SessionDAO
    // ─────────────────────────────────────────
    public void createSession() {

        System.out.print("Session ID: ");
        int id = sc.nextInt();

        System.out.print("Session Title: ");
        String title = sc.next();

        TrainingSession ts = new TrainingSession(id, title);

        SessionDAO dao = new SessionDAO();
        dao.createSession(ts);
    }

    // ─────────────────────────────────────────
    // VIEW ALL TRAINERS
    // Before → DataStorage.trainers.values()
    // After  → fetches from MySQL trainers table via AdminDAO
    // ─────────────────────────────────────────
    public void viewTrainers() {

        AdminDAO dao = new AdminDAO();
        ArrayList<Trainer> trainers = dao.getAllTrainers();

        if (trainers.isEmpty()) {
            System.out.println("No trainers found!");
            return;
        }

        for (Trainer t : trainers) {
            t.display();
            System.out.println("----------");
        }
    }

    // ─────────────────────────────────────────
    // VIEW ALL PARTICIPANTS
    // Before → DataStorage.participants
    // After  → fetches from MySQL participants table via AdminDAO
    // ─────────────────────────────────────────
    public void viewParticipants() {

        AdminDAO dao = new AdminDAO();
        ArrayList<Participant> participants = dao.getAllParticipants();

        if (participants.isEmpty()) {
            System.out.println("No participants found!");
            return;
        }

        for (Participant p : participants) {
            p.display();
            System.out.println("----------");
        }
    }

    // ─────────────────────────────────────────
    // VIEW REPORTS
    // Before → DataStorage.sessions.values()
    // After  → fetches all sessions + feedback from MySQL via SessionDAO
    // ─────────────────────────────────────────
    public void viewReports() {

        SessionDAO dao = new SessionDAO();
        ArrayList<TrainingSession> sessions = dao.getAllSessions();

        if (sessions.isEmpty()) {
            System.out.println("No sessions found!");
            return;
        }

        for (TrainingSession ts : sessions) {
            System.out.println("\nSession : " + ts.getTitle());

            if (ts.getTrainer() != null) {
                System.out.println("Trainer : " + ts.getTrainer().getName());
            } else {
                System.out.println("Trainer : Not Assigned");
            }

            ts.viewFeedback();
            System.out.println("----------");
        }
    }
}

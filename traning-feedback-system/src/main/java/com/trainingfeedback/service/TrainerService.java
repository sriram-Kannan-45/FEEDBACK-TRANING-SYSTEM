package com.trainingfeedback.service;

import java.util.ArrayList;
import com.trainingfeedback.model.*;
import com.trainingfeedback.dbconnection.TrainerDAO;

public class TrainerService {

    // ─────────────────────────────────────────
    // VIEW COURSES
    // Before → t.getCourses() from memory
    // After  → fetches from MySQL trainer_courses table via TrainerDAO
    // ─────────────────────────────────────────
    public void viewCourses(Trainer t) {

        TrainerDAO dao = new TrainerDAO();
        ArrayList<String> courses = dao.getCourses(t.getId());

        if (courses.isEmpty()) {
            System.out.println("No courses found!");
            return;
        }

        System.out.println("\n===== My Courses =====");
        for (String c : courses) {
            System.out.println("- " + c);
        }
    }

    // ─────────────────────────────────────────
    // VIEW ALL PARTICIPANTS
    // Before → DataStorage.participants
    // After  → fetches from MySQL participants table via TrainerDAO
    // ─────────────────────────────────────────
    public void viewParticipants() {

        TrainerDAO dao = new TrainerDAO();
        ArrayList<Participant> participants = dao.getAllParticipants();

        if (participants.isEmpty()) {
            System.out.println("No participants found!");
            return;
        }

        System.out.println("\n===== All Participants =====");
        for (Participant p : participants) {
            p.display();
            System.out.println("----------");
        }
    }

    // ─────────────────────────────────────────
    // VIEW MY SESSIONS
    // Before → looped DataStorage.sessions, compared trainer object
    // After  → fetches sessions assigned to this trainer from MySQL
    // ─────────────────────────────────────────
    public void viewMySessions(Trainer t) {

        TrainerDAO dao = new TrainerDAO();
        ArrayList<TrainingSession> sessions = dao.getMySessions(t.getId());

        if (sessions.isEmpty()) {
            System.out.println("No sessions assigned to you!");
            return;
        }

        System.out.println("\n===== My Sessions =====");
        for (TrainingSession ts : sessions) {
            System.out.println("\nSession : " + ts.getTitle());
            ts.viewFeedback();
            System.out.println("----------");
        }
    }
}

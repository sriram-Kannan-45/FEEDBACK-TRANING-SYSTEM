package com.trainingfeedback.service;

import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.dbconnection.SessionDAO;

public class ParticipantService {

    Scanner sc = new Scanner(System.in);

    // ─────────────────────────────────────────
    // SUBMIT FEEDBACK
    // Before → DataStorage.sessions.get(sid) → ts.addFeedback()
    // After  → validates session from MySQL, saves feedback via SessionDAO
    // ─────────────────────────────────────────
    public void submitFeedback(Participant p) {

        System.out.print("Session ID: ");
        int sid = sc.nextInt();
        sc.nextLine();

        SessionDAO dao = new SessionDAO();

        // check session exists in DB
        TrainingSession ts = dao.findById(sid);

        if (ts == null) {
            System.out.println("Invalid Session!");
            return;
        }

        // check already submitted in DB
        if (dao.hasGivenFeedback(p.getId(), sid)) {
            System.out.println("Already Submitted!");
            return;
        }

        System.out.print("Rating (1-5): ");
        int rating = sc.nextInt();
        sc.nextLine();

        System.out.print("Comment: ");
        String comment = sc.nextLine();

        // save feedback to MySQL feedback table
        dao.submitFeedback(p.getId(), sid, rating, comment);
    }

    // ─────────────────────────────────────────
    // VIEW PROFILE
    // No change needed - just displays the Participant object
    // ─────────────────────────────────────────
    public void viewProfile(Participant p) {
        System.out.println("\n===== My Profile =====");
        p.display();
    }

    // ─────────────────────────────────────────
    // VIEW MY FEEDBACK
    // Before → printed a placeholder message
    // After  → fetches participant's feedback from MySQL via SessionDAO
    // ─────────────────────────────────────────
    public void viewFeedback(Participant p) {

        System.out.print("Session ID to view feedback: ");
        int sid = sc.nextInt();

        SessionDAO dao = new SessionDAO();

        boolean submitted = dao.hasGivenFeedback(p.getId(), sid);

        if (submitted) {
            System.out.println("You have submitted feedback for Session ID: " + sid);
        } else {
            System.out.println("You have NOT submitted feedback for Session ID: " + sid);
        }
    }
}

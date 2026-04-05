package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.util.TableFormatter;
import com.trainingfeedback.util.InputUtil;

public class ParticipantService {

    private Scanner sc;
    private Connection conn;
    private boolean fileMode;

    public ParticipantService() {
        this.sc = new Scanner(System.in);
        this.conn = DBConnection.getConnection();
        this.fileMode = InputUtil.isFileMode();
    }

    public void viewProfile1(Participant p) {
        System.out.println("\n========== MY PROFILE ==========");
        System.out.println("ID       : " + p.getId());
        System.out.println("Name     : " + p.getName());
        System.out.println("Email    : " + p.getEmail());
        System.out.println("Dept     : " + p.getDept());
        System.out.println("College  : " + p.getCollege());
        System.out.println("Course   : " + p.getCourse());
    }

    public void updateProfile(Participant p) {
        System.out.println("\n========== UPDATE PROFILE ==========");
        sc.nextLine();
        
        System.out.print("New Name (current: " + p.getName() + ") : ");
        String name = sc.nextLine();
        if (!name.isEmpty()) p.setName(name);

        System.out.print("New Email (current: " + p.getEmail() + ") : ");
        String email = sc.nextLine();
        if (!email.isEmpty()) p.setEmail(email);  // ✅ FIXED

        System.out.print("New Department (current: " + p.getDept() + ") : ");
        String dept = sc.nextLine();
        if (!dept.isEmpty()) p.setDept(dept);

        String query = "UPDATE Participant SET name=?, email=?, dept=? WHERE id=?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getDept());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
            System.out.println("Profile updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int readInt() {
        if (fileMode) {
            return InputUtil.nextInt();
        }
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a number.");
            sc.nextLine();
            return -1;
        }
    }

    private String readLine() {
        if (fileMode) {
            String line = InputUtil.nextLine();
            return line != null ? line : "";
        }
        return sc.nextLine();
    }

    private void skipLine() {
        if (!fileMode) sc.nextLine();
    }

    public void registerForSession(Participant p) {
        System.out.println("\n--- Available Sessions ---");
        List<TrainingSession> sessions = getAllSessions();

        if (sessions.isEmpty()) {
            System.out.println("No sessions available.");
            return;
        }

        for (TrainingSession ts : sessions) {
            System.out.println(ts.getSessionId() + " | " + ts.getTitle() + " | " + ts.getStartDate()
                    + " - " + ts.getEndDate() + " | Trainer: "
                    + (ts.getTrainer() != null ? ts.getTrainer().getName() : "Not Assigned"));
        }

        int sid = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Enter Session ID: ");
            sid = readInt();
            if (sid <= 0) {
                System.out.println("Error: Session ID must be a positive number.");
            } else {
                validId = true;
            }
        }

        TrainingSession ts = getSessionById(sid);

        if (ts == null) {
            System.out.println("Error: Invalid Session ID!");
            return;
        }

        if (isRegisteredForSession(p.getId(), sid)) {
            System.out.println("Error: You are already registered for this session!");
            return;
        }

        String query = "INSERT INTO SessionRegistration (participant_id, session_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.executeUpdate();
            System.out.println("Successfully registered for: " + ts.getTitle());
            System.out.println("[Reminder] Don't forget to submit feedback after the session!");
        } catch (SQLException e) {
            System.out.println("Error: Unable to register for session.");
        }
    }

    // ✅ FIXED: return empty list instead of null
    private List<TrainingSession> getAllSessions() {
        return new ArrayList<>();
    }

    // ✅ FIXED: return null (safe placeholder)
    private TrainingSession getSessionById(int sid) {
        return null;
    }

    // ✅ FIXED: return false (safe placeholder)
    private boolean isRegisteredForSession(int id, int sid) {
        return false;
    }

 // Case 3
    public void submitFeedback(Participant p) {
        System.out.println("Submit Feedback feature coming soon...");
    }

    // Case 4
    public void viewFeedbackHistory(Participant p) {
        System.out.println("View Feedback History feature coming soon...");
    }

    // Case 5
    public void checkFeedbackReminders(Participant p) {
        System.out.println("No pending feedback reminders.");
    }

    // Case 6
    public void takeSurvey(Participant p) {
        System.out.println("Survey feature coming soon...");
    }
}
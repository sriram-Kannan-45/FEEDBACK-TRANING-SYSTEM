package com.trainingfeedback.service;

import java.sql.*;
import java.util.*;
import com.trainingfeedback.model.*;

public class ParticipantService {

    Scanner sc = new Scanner(System.in);
    private Connection conn;

    public ParticipantService() {
        this.conn = DBConnection.getConnection();
    }

  
    private int getValidInt(String message, int min, int max) {
        int value;
        while (true) {
            System.out.print(message);
            try {
                value = Integer.parseInt(sc.nextLine());
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Enter value between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number.");
            }
        }
    }

    private String getValidString(String message) {
        String input;
        while (true) {
            System.out.print(message);
            input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Input cannot be empty!");
            }
        }
    }

 

    public void registerForSession(Participant p) {

        System.out.println("\n--- Available Sessions ---");
        List<TrainingSession> sessions = getAllSessions();

        if (sessions.isEmpty()) {
            System.out.println("No sessions available.");
            return;
        }

        for (TrainingSession ts : sessions) {
            System.out.println(ts.getSessionId() + " | " + ts.getTitle() + " | "
                    + ts.getStartDate() + " - " + ts.getEndDate()
                    + " | Trainer: "
                    + (ts.getTrainer() != null ? ts.getTrainer().getName() : "Not Assigned"));
        }

        int sid = getValidInt("Enter Session ID: ", 1, Integer.MAX_VALUE);

        TrainingSession ts = getSessionById(sid);

        if (ts == null) {
            System.out.println("Invalid Session ID!");
            return;
        }

        if (isRegisteredForSession(p.getId(), sid)) {
            System.out.println("Already registered!");
            return;
        }

        String query = "INSERT INTO SessionRegistration (participant_id, session_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.executeUpdate();

            System.out.println("Registered successfully: " + ts.getTitle());
            System.out.println("[Reminder] Submit feedback after session!");

        } catch (SQLException e) {
            System.out.println("Error registering!");
            e.printStackTrace();
        }
    }

    private boolean isRegisteredForSession(int participantId, int sessionId) {
        String query = "SELECT * FROM SessionRegistration WHERE participant_id=? AND session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

   

    private List<TrainingSession> getAllSessions() {
        List<TrainingSession> list = new ArrayList<>();

        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved "
                + "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TrainingSession ts = new TrainingSession(
                        rs.getInt("session_id"),
                        rs.getString("title"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("time"),
                        rs.getInt("duration")
                );

                if (rs.getString("trainer_name") != null) {
                    Trainer t = new Trainer(rs.getInt("trainer_id"),
                            rs.getString("trainer_name"), "");
                    t.setApproved(rs.getBoolean("trainer_approved"));
                    ts.assignTrainer(t);
                }

                list.add(ts);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private TrainingSession getSessionById(int id) {
        String query = "SELECT ts.*, t.name as trainer_name "
                + "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id "
                + "WHERE ts.session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TrainingSession ts = new TrainingSession(
                        rs.getInt("session_id"),
                        rs.getString("title"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("time"),
                        rs.getInt("duration")
                );

                if (rs.getString("trainer_name") != null) {
                    Trainer t = new Trainer(rs.getInt("trainer_id"),
                            rs.getString("trainer_name"), "");
                    ts.assignTrainer(t);
                }

                return ts;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

 

    public void submitFeedback(Participant p) {

        List<TrainingSession> sessions = getRegisteredSessions(p.getId());

        if (sessions.isEmpty()) {
            System.out.println("No registered sessions.");
            return;
        }

        System.out.println("\n--- Your Sessions ---");
        for (TrainingSession ts : sessions) {
            String status = hasGivenFeedback(p.getId(), ts.getSessionId())
                    ? "[Submitted]" : "[Pending]";
            System.out.println(ts.getSessionId() + " | " + ts.getTitle() + " " + status);
        }

        int sid = getValidInt("Enter Session ID: ", 1, Integer.MAX_VALUE);

        if (!isRegisteredForSession(p.getId(), sid)) {
            System.out.println("Not registered!");
            return;
        }

        if (hasGivenFeedback(p.getId(), sid)) {
            System.out.println("Already submitted!");
            return;
        }

        int rating = getValidInt("Rating (1-5): ", 1, 5);
        String comment = getValidString("Comment: ");

        String query = "INSERT INTO Feedback (participant_id, session_id, rating, comment, instructor_rating, instructor_comment) VALUES (?, ?, ?, ?, 0, '')";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.executeUpdate();

            System.out.println("Feedback submitted!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean hasGivenFeedback(int pid, int sid) {
        String query = "SELECT * FROM Feedback WHERE participant_id=? AND session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pid);
            ps.setInt(2, sid);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private List<TrainingSession> getRegisteredSessions(int pid) {
        List<TrainingSession> list = new ArrayList<>();

        String query = "SELECT ts.* FROM TrainingSession ts "
                + "JOIN SessionRegistration sr ON ts.session_id = sr.session_id "
                + "WHERE sr.participant_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TrainingSession(
                        rs.getInt("session_id"),
                        rs.getString("title"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getString("time"),
                        rs.getInt("duration")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

   

    public void viewFeedbackHistory(Participant p) {

        String query = "SELECT f.*, ts.title FROM Feedback f "
                + "JOIN TrainingSession ts ON f.session_id = ts.session_id "
                + "WHERE f.participant_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("\nSession: " + rs.getString("title"));
                System.out.println("Rating: " + rs.getInt("rating"));
                System.out.println("Comment: " + rs.getString("comment"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  

    public void checkFeedbackReminders(Participant p) {
        List<TrainingSession> sessions = getRegisteredSessions(p.getId());

        boolean hasUnsubmitted = false;

        for (TrainingSession ts : sessions) {
            if (!hasGivenFeedback(p.getId(), ts.getSessionId())) {
                System.out.println("Pending: " + ts.getTitle());
                hasUnsubmitted = true;
            }
        }

        if (!hasUnsubmitted) {
            System.out.println("No pending feedback.");
        }
    }

   

    public void viewProfile(Participant p) {
        System.out.println("\n--- Profile ---");
        System.out.println("ID: " + p.getId());
        System.out.println("Name: " + p.getName());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Course: " + p.getCourse());
    }
}

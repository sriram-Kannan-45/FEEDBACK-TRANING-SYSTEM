package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.trainingfeedback.model.*;

public class ParticipantService {

    Scanner sc = new Scanner(System.in);
    private Connection conn;

    public ParticipantService() {
        this.conn = DBConnection.getConnection();
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

        System.out.print("Enter Session ID: ");
        int sid = sc.nextInt();

        TrainingSession ts = getSessionById(sid);

        if (ts == null) {
            System.out.println("Invalid Session ID!");
            return;
        }

<<<<<<< HEAD
        if (p.isRegisteredFor(ts)) {
            System.out.println("Already registered!");
            return;
        }

        p.registerSession(ts);
        ts.getParticipants().add(p);

        p.setFeedbackReminderPending(true);

        System.out.println("Registered successfully: " + ts.getTitle());
    }

    // Submit Feedback
    public void submitFeedback(Participant p) {

        if (p.getRegisteredSessions().isEmpty()) {
            System.out.println("No sessions registered.");
            return;
        }

        System.out.println("\n--- Your Sessions ---");
        for (TrainingSession ts : p.getRegisteredSessions()) {
            System.out.println(ts.getSessionId() + " | " + ts.getTitle());
=======
        if (isRegisteredForSession(p.getId(), sid)) {
            System.out.println("You are already registered for this session!");
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
            System.out.println("Error registering for session!");
            e.printStackTrace();
        }
    }

    private boolean isRegisteredForSession(int participantId, int sessionId) {
        String query = "SELECT * FROM SessionRegistration WHERE participant_id = ? AND session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<TrainingSession> getAllSessions() {
        List<TrainingSession> sessions = new ArrayList<>();
        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved " +
                       "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id";

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
                    Trainer t = new Trainer(rs.getInt("trainer_id"), rs.getString("trainer_name"), "");
                    t.setApproved(rs.getBoolean("trainer_approved"));
                    ts.assignTrainer(t);
                }
                sessions.add(ts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    private TrainingSession getSessionById(int sessionId) {
        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved " +
                       "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "WHERE ts.session_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
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
                    Trainer t = new Trainer(rs.getInt("trainer_id"), rs.getString("trainer_name"), "");
                    t.setApproved(rs.getBoolean("trainer_approved"));
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
        List<TrainingSession> registeredSessions = getRegisteredSessions(p.getId());

        if (registeredSessions.isEmpty()) {
            System.out.println("You have not registered for any session yet.");
            return;
        }

        System.out.println("\n--- Your Registered Sessions ---");
        for (TrainingSession ts : registeredSessions) {
            String status = hasGivenFeedback(p.getId(), ts.getSessionId()) ? "[Feedback Submitted]" : "[Pending Feedback]";
            System.out.println("  " + ts.getSessionId() + " | " + ts.getTitle() + " " + status);
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
        }

        System.out.print("Enter Session ID: ");
        int sid = sc.nextInt();
        sc.nextLine();

        TrainingSession ts = getSessionById(sid);

<<<<<<< HEAD
        if (ts == null || !p.isRegisteredFor(ts)) {
            System.out.println("Invalid session.");
            return;
        }

        if (ts.hasGivenFeedback(p.getId())) {
            System.out.println("Already submitted.");
            return;
        }

        System.out.print("Rating (1-5): ");
=======
        if (ts == null) {
            System.out.println("Invalid Session ID.");
            return;
        }

        if (!isRegisteredForSession(p.getId(), sid)) {
            System.out.println("You are not registered for this session.");
            return;
        }

        if (hasGivenFeedback(p.getId(), sid)) {
            System.out.println("You have already submitted feedback for this session.");
            return;
        }

        System.out.print("Session Rating (1-5): ");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
        int rating = sc.nextInt();
        sc.nextLine();

        System.out.print("Comment: ");
        String comment = sc.nextLine();

<<<<<<< HEAD
        // SIMPLE constructor (important fix)
        Feedback f = new Feedback(p.getId(), rating, comment);

        ts.addFeedback(f);
        p.addFeedbackHistory(f);

        System.out.println("Feedback submitted!");
    }

    // View Feedback History
    public void viewFeedbackHistory(Participant p) {

        if (p.getFeedbackHistory().isEmpty()) {
            System.out.println("No feedback history.");
            return;
        }

        for (Feedback f : p.getFeedbackHistory()) {
            System.out.println(f);
        }
    }

    // Reminder
    public void checkFeedbackReminders(Participant p) {

        boolean pending = false;

        for (TrainingSession ts : p.getRegisteredSessions()) {
            if (!ts.hasGivenFeedback(p.getId())) {
                System.out.println("Pending: " + ts.getTitle());
                pending = true;
=======
        String query = "INSERT INTO Feedback (participant_id, session_id, rating, comment, instructor_rating, instructor_comment) VALUES (?, ?, ?, ?, 0, '')";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.executeUpdate();

            if (ts.getTrainer() != null) {
                System.out.print("Instructor Rating (1-5): ");
                int instrRating = sc.nextInt();
                sc.nextLine();

                System.out.print("Instructor Comment: ");
                String instrComment = sc.nextLine();

                String updateQuery = "UPDATE Feedback SET instructor_rating = ?, instructor_comment = ? WHERE participant_id = ? AND session_id = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(updateQuery)) {
                    ps2.setInt(1, instrRating);
                    ps2.setString(2, instrComment);
                    ps2.setInt(3, p.getId());
                    ps2.setInt(4, sid);
                    ps2.executeUpdate();
                }
            }

            System.out.println("[Admin Notified] Feedback recorded.");
            System.out.println("Feedback submitted successfully!");
        } catch (SQLException e) {
            System.out.println("Error submitting feedback!");
            e.printStackTrace();
        }
    }

    private boolean hasGivenFeedback(int participantId, int sessionId) {
        String query = "SELECT * FROM Feedback WHERE participant_id = ? AND session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<TrainingSession> getRegisteredSessions(int participantId) {
        List<TrainingSession> sessions = new ArrayList<>();
        String query = "SELECT ts.*, t.name as trainer_name " +
                       "FROM TrainingSession ts " +
                       "JOIN SessionRegistration sr ON ts.session_id = sr.session_id " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "WHERE sr.participant_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
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
                    Trainer t = new Trainer(rs.getInt("trainer_id"), rs.getString("trainer_name"), "");
                    ts.assignTrainer(t);
                }
                sessions.add(ts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public void viewFeedbackHistory(Participant p) {
        System.out.println("\n--- Your Feedback History ---");

        String query = "SELECT f.*, ts.title as session_title " +
                       "FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "WHERE f.participant_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("  Session : " + rs.getString("session_title"));
                System.out.println("  Rating  : " + rs.getInt("rating") + "/5");
                System.out.println("  Comment : " + rs.getString("comment"));
                if (rs.getInt("instructor_rating") > 0) {
                    System.out.println("  Instructor Rating  : " + rs.getInt("instructor_rating") + "/5");
                    System.out.println("  Instructor Comment : " + rs.getString("instructor_comment"));
                }
                System.out.println("  --------------------");
            }

            if (!found) {
                System.out.println("You have not submitted any feedback yet.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing feedback history!");
            e.printStackTrace();
        }
    }

    public void checkFeedbackReminders(Participant p) {
        List<TrainingSession> registeredSessions = getRegisteredSessions(p.getId());
        boolean hasUnsubmitted = false;

        for (TrainingSession ts : registeredSessions) {
            if (!hasGivenFeedback(p.getId(), ts.getSessionId())) {
                System.out.println("[REMINDER] You have not submitted feedback for: " + ts.getTitle());
                hasUnsubmitted = true;
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
            }
        }

        if (!pending) {
            System.out.println("No pending feedback.");
        }
    }

<<<<<<< HEAD
    // Profile
    public void viewProfile(Participant p) {
        p.display();
=======
    public void viewProfile(Participant p) {
        System.out.println("\n--- Your Profile ---");
        System.out.println("ID     : " + p.getId());
        System.out.println("Name   : " + p.getName());
        System.out.println("Email  : " + p.getEmail());
        System.out.println("Course : " + p.getCourse());
        System.out.println("Sessions Registered : " + getRegisteredSessions(p.getId()).size());
        System.out.println("Feedbacks Submitted : " + getFeedbackCount(p.getId()));
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
    }

    private int getFeedbackCount(int participantId) {
        String query = "SELECT COUNT(*) FROM Feedback WHERE participant_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

package com.trainingfeedback.service;

import java.sql.*;
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

    // ========== PROFILE ==========

    public void viewProfile(Participant p) {
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
        if (!email.isEmpty()) p.setEmail(email);

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

    // ========== SESSION REGISTRATION ==========

    public void viewAvailableSessions() {
        String query = "SELECT ts.*, t.name as trainer_name " +
                       "FROM TrainingSession ts " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "WHERE ts.trainer_id IS NOT NULL " +
                       "ORDER BY ts.start_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== AVAILABLE TRAINING SESSIONS ==========");
            System.out.printf("%-10s %-25s %-15s %-15s %-15s %-10s%n", 
                "ID", "Title", "Trainer", "Start Date", "Duration", "Seats");
            System.out.println("----------------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int sessionId = rs.getInt("session_id");
                int maxParticipants = rs.getInt("max_participants");
                int registered = getRegisteredCount(sessionId);
                int available = maxParticipants - registered;

                System.out.printf("%-10d %-25s %-15s %-15s %-15s %-10d%n",
                    sessionId,
                    truncate(rs.getString("title"), 25),
                    rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "TBA",
                    rs.getString("start_date"),
                    rs.getInt("duration") + " hrs",
                    available);
            }

            if (!hasData) {
                System.out.println("No sessions available!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerForSession(Participant p) {
        viewAvailableSessions();
        System.out.print("\nEnter Session ID to register : ");
        int sid = sc.nextInt();

        if (isAlreadyRegistered(p.getId(), sid)) {
            System.out.println("You are already registered for this session!");
            return;
        }

        if (!sessionExists(sid)) {
            System.out.println("Session not found!");
            return;
        }

        int maxParticipants = getMaxParticipants(sid);
        int registered = getRegisteredCount(sid);
        
        if (registered >= maxParticipants) {
            System.out.println("Session is full! No seats available.");
            return;
        }

        String query = "INSERT INTO SessionRegistration (participant_id, session_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.executeUpdate();
            
            String sessionTitle = getSessionTitle(sid);
            System.out.println("Successfully registered for session: " + sessionTitle);
            Notification.NotificationManager.sendRegistrationNotification(p.getId(), p.getName(), sid, sessionTitle);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMySessions(Participant p) {
        String query = "SELECT ts.*, t.name as trainer_name, f.id as feedback_id " +
                       "FROM SessionRegistration sr " +
                       "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "LEFT JOIN Feedback f ON f.session_id = ts.session_id AND f.participant_id = sr.participant_id " +
                       "WHERE sr.participant_id=? " +
                       "ORDER BY ts.start_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== MY REGISTERED SESSIONS ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("\n--- Session ID: " + rs.getInt("session_id") + " ---");
                System.out.println("Title       : " + rs.getString("title"));
                System.out.println("Trainer     : " + (rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "TBA"));
                System.out.println("Date        : " + rs.getString("start_date") + " to " + rs.getString("end_date"));
                System.out.println("Time        : " + rs.getString("time"));
                System.out.println("Duration    : " + rs.getInt("duration") + " hours");
                System.out.println("Location    : " + rs.getString("location"));
                System.out.println("Feedback    : " + (rs.getObject("feedback_id") != null ? "Submitted" : "Pending"));
            }

            if (!hasData) {
                System.out.println("You haven't registered for any sessions!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelRegistration(Participant p) {
        System.out.print("Enter Session ID to cancel registration : ");
        int sid = sc.nextInt();

        if (!isAlreadyRegistered(p.getId(), sid)) {
            System.out.println("You are not registered for this session!");
            return;
        }

        String query = "DELETE FROM SessionRegistration WHERE participant_id=? AND session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.executeUpdate();
            System.out.println("Registration cancelled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== FEEDBACK SUBMISSION ==========

    public void submitFeedback(Participant p) {
        viewMySessionsWithPendingFeedback(p);
        
        System.out.print("\nEnter Session ID to submit feedback : ");
        int sid = sc.nextInt();
        sc.nextLine();

        if (!isRegisteredForSession(p.getId(), sid)) {
            System.out.println("You are not registered for this session!");
            return;
        }

        if (hasAlreadySubmitted(p.getId(), sid)) {
            System.out.println("You have already submitted feedback for this session!");
            return;
        }

        System.out.println("\n========== FEEDBACK FORM ==========");
        System.out.println("Session: " + getSessionTitle(sid));
        System.out.println("===================================\n");

        System.out.print("Is this anonymous feedback? (y/n) : ");
        boolean anonymous = sc.nextLine().toLowerCase().startsWith("y");

        System.out.println("\n--- TRAINING EVALUATION ---");
        System.out.print("Rate the training content (1-5) : ");
        int trainingRating = sc.nextInt();
        sc.nextLine();

        System.out.print("Rate the training materials (1-5) : ");
        int materialsRating = sc.nextInt();
        sc.nextLine();

        System.out.print("Rate the session duration (1-5) : ");
        int durationRating = sc.nextInt();
        sc.nextLine();

        System.out.print("What did you like about the training? : ");
        String trainingComment = sc.nextLine();

        System.out.print("What could be improved? : ");
        String improvementComment = sc.nextLine();

        System.out.println("\n--- INSTRUCTOR EVALUATION ---");
        System.out.print("Rate the instructor's knowledge (1-5) : ");
        int knowledgeRating = sc.nextInt();
        sc.nextLine();

        System.out.print("Rate the instructor's communication (1-5) : ");
        int communicationRating = sc.nextInt();
        sc.nextLine();

        System.out.print("Rate the instructor's teaching style (1-5) : ");
        int teachingRating = sc.nextInt();
        sc.nextLine();

        int instructorRating = (knowledgeRating + communicationRating + teachingRating) / 3;

        System.out.print("Additional comments for instructor : ");
        String instructorComment = sc.nextLine();

        if (trainingRating < 1 || trainingRating > 5 || instructorRating < 1 || instructorRating > 5) {
            System.out.println("Invalid rating! Please enter values between 1 and 5.");
            return;
        }

        String query = "INSERT INTO Feedback (participant_id, session_id, training_rating, instructor_rating, " +
                       "training_comment, instructor_comment, anonymous) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.setInt(3, trainingRating);
            ps.setInt(4, instructorRating);
            ps.setString(5, trainingComment + " | Materials: " + materialsRating + "/5 | Duration: " + durationRating + "/5");
            ps.setString(6, instructorComment);
            ps.setBoolean(7, anonymous);
            ps.executeUpdate();
            
            System.out.println("\nFeedback Submitted Successfully!");
            Notification.NotificationManager.sendFeedbackSubmittedNotification(sid, getSessionTitle(sid));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMySessionsWithPendingFeedback(Participant p) {
        String query = "SELECT ts.session_id, ts.title, ts.start_date, f.id as feedback_id " +
                       "FROM SessionRegistration sr " +
                       "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                       "LEFT JOIN Feedback f ON f.session_id = ts.session_id AND f.participant_id = sr.participant_id " +
                       "WHERE sr.participant_id=? AND f.id IS NULL";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== SESSIONS PENDING FEEDBACK ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("Session ID: " + rs.getInt("session_id") + 
                                   " - " + rs.getString("title") + 
                                   " (Date: " + rs.getString("start_date") + ")");
            }

            if (!hasData) {
                System.out.println("All sessions have feedback submitted!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== FEEDBACK HISTORY ==========

    public void viewFeedbackHistory(Participant p) {
        String query = "SELECT f.*, ts.title as session_title, t.name as trainer_name, f.trainer_response " +
                       "FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "WHERE f.participant_id=? " +
                       "ORDER BY f.submitted_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== MY FEEDBACK HISTORY ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("\n--- Session: " + rs.getString("session_title") + " ---");
                System.out.println("Trainer     : " + (rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "N/A"));
                System.out.println("Date        : " + (rs.getString("submitted_at") != null ? rs.getString("submitted_at") : "N/A"));
                System.out.println("Training    : " + rs.getInt("training_rating") + "/5");
                System.out.println("Instructor  : " + rs.getInt("instructor_rating") + "/5");
                System.out.println("Comment     : " + rs.getString("training_comment"));
                
                String response = rs.getString("trainer_response");
                if (response != null && !response.isEmpty()) {
                    System.out.println("Trainer Response: " + response);
                }
            }

            if (!hasData) {
                System.out.println("No feedback submitted yet!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewFeedbackReports(Participant p) {
        System.out.println("\n========== MY FEEDBACK REPORTS ==========");

        String query = "SELECT COUNT(*) as total, AVG(training_rating) as avg_training, " +
                       "AVG(instructor_rating) as avg_instructor " +
                       "FROM Feedback WHERE participant_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                if (total > 0) {
                    System.out.println("Total Feedbacks Submitted : " + total);
                    System.out.printf("Average Training Rating   : %.2f/5%n", rs.getDouble("avg_training"));
                    System.out.printf("Average Instructor Rating: %.2f/5%n", rs.getDouble("avg_instructor"));
                    System.out.println("\nYour participation rate: " + getParticipationRate(p.getId()) + "%");
                } else {
                    System.out.println("No feedback reports available.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== REMINDERS ==========

    public void checkFeedbackReminders(Participant p) {
        String query = "SELECT ts.session_id, ts.title, ts.start_date " +
                       "FROM SessionRegistration sr " +
                       "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                       "WHERE sr.participant_id=? AND ts.session_id NOT IN " +
                       "(SELECT session_id FROM Feedback WHERE participant_id=?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ps.setInt(2, p.getId());
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== FEEDBACK REMINDERS ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("Session ID: " + rs.getInt("session_id") + 
                                   " - " + rs.getString("title") + 
                                   " (Date: " + rs.getString("start_date") + ")");
            }

            if (hasData) {
                System.out.println("\nPlease submit your feedback for the sessions above.");
                System.out.println("Go to option 3 (Submit Feedback) to provide your feedback.");
            } else {
                System.out.println("All sessions have feedback submitted! Thank you.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMyNotifications(Participant p) {
        List<Notification> notifications = Notification.NotificationManager.getNotificationsForUser("PARTICIPANT", p.getId());

        System.out.println("\n========== MY NOTIFICATIONS ==========");
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (int i = notifications.size() - 1; i >= 0 && i >= notifications.size() - 10; i--) {
            Notification n = notifications.get(i);
            System.out.println("[" + n.getType() + "] " + n.getMessage());
        }
    }

    // ========== HELPER METHODS ==========

    private boolean sessionExists(int sessionId) {
        String query = "SELECT session_id FROM TrainingSession WHERE session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAlreadyRegistered(int participantId, int sessionId) {
        String query = "SELECT participant_id FROM SessionRegistration WHERE participant_id=? AND session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isRegisteredForSession(int participantId, int sessionId) {
        return isAlreadyRegistered(participantId, sessionId);
    }

    private boolean hasAlreadySubmitted(int participantId, int sessionId) {
        String query = "SELECT id FROM Feedback WHERE participant_id=? AND session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getRegisteredCount(int sessionId) {
        String query = "SELECT COUNT(*) as count FROM SessionRegistration WHERE session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getMaxParticipants(int sessionId) {
        String query = "SELECT max_participants FROM TrainingSession WHERE session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("max_participants");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 30;
    }

    private String getSessionTitle(int sessionId) {
        String query = "SELECT title FROM TrainingSession WHERE session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("title");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private double getParticipationRate(int participantId) {
        String query = "SELECT " +
                       "(SELECT COUNT(*) FROM SessionRegistration WHERE participant_id=?) as registered, " +
                       "(SELECT COUNT(*) FROM Feedback WHERE participant_id=?) as feedback";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, participantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int registered = rs.getInt("registered");
                int feedback = rs.getInt("feedback");
                if (registered > 0) {
                    return (feedback * 100.0) / registered;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String truncate(String str, int length) {
        if (str == null) return "";
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
}

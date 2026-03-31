package com.trainingfeedback.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.trainingfeedback.model.*;

package service;

import java.util.*;

public class AdminService {

			System.out.println("Trainer not found ");
		}
	}
}
=======
    Scanner sc = new Scanner(System.in);
    private Connection conn;
    private List<Survey> surveys;

    public AdminService() {
        this.conn = DBConnection.getConnection();
        this.surveys = new ArrayList<>();
        loadSurveys();
    }

    // ========== TRAINER MANAGEMENT ==========

    public void createTrainer() {
        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        if (trainerExists(id)) {
            System.out.println("Trainer already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Name : ");
        String name = sc.nextLine();

        System.out.print("Password : ");
        String pass = sc.next();

        sc.nextLine();
        System.out.print("Course/Expertise : ");
        String course = sc.nextLine();

        System.out.print("Email : ");
        String email = sc.next();

        System.out.print("Department : ");
        String dept = sc.next();

        String query = "INSERT INTO Trainer (id, name, password, approved, course, email, department) VALUES (?, ?, ?, FALSE, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, course);
            ps.setString(5, email);
            ps.setString(6, dept);
            ps.executeUpdate();
            System.out.println("Trainer Created Successfully! Pending approval.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewTrainers() {
        String query = "SELECT * FROM Trainer";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== ALL TRAINERS ==========");
            System.out.printf("%-10s %-20s %-25s %-20s %-10s%n", "ID", "Name", "Email", "Course", "Status");
            System.out.println("-------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-20s %-25s %-20s %-10s%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("course"),
                    rs.getBoolean("approved") ? "Active" : "Pending");
            }

            if (!hasData) {
                System.out.println("No trainers found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveTrainer() {
        viewTrainers();
        System.out.print("\nEnter Trainer ID to approve : ");
        int id = sc.nextInt();

        String checkQuery = "SELECT * FROM Trainer WHERE id=?";
        String updateQuery = "UPDATE Trainer SET approved=TRUE WHERE id=?";

        try (PreparedStatement checkPs = conn.prepareStatement(checkQuery);
             PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {

            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                String trainerName = rs.getString("name");
                updatePs.setInt(1, id);
                updatePs.executeUpdate();
                System.out.println("Trainer '" + trainerName + "' approved successfully!");
                Notification.NotificationManager.sendTrainerApprovalNotification(id, trainerName);
            } else {
                System.out.println("Trainer not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivateTrainer() {
        System.out.print("Enter Trainer ID to deactivate : ");
        int id = sc.nextInt();

        String query = "UPDATE Trainer SET approved=FALSE WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Trainer deactivated successfully!");
            } else {
                System.out.println("Trainer not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== PARTICIPANT MANAGEMENT ==========

    public void viewParticipants() {
        String query = "SELECT p.*, COUNT(DISTINCT sr.session_id) as sessions_attended, " +
                       "COUNT(DISTINCT f.id) as feedbacks_submitted " +
                       "FROM Participant p " +
                       "LEFT JOIN SessionRegistration sr ON p.id = sr.participant_id " +
                       "LEFT JOIN Feedback f ON p.id = f.participant_id " +
                       "GROUP BY p.id";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== ALL PARTICIPANTS ==========");
            System.out.printf("%-10s %-20s %-25s %-15s %-15s %-15s%n", 
                "ID", "Name", "Email", "Dept", "Sessions", "Feedbacks");
            System.out.println("-------------------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-20s %-25s %-15s %-15d %-15d%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("dept"),
                    rs.getInt("sessions_attended"),
                    rs.getInt("feedbacks_submitted"));
            }

            if (!hasData) {
                System.out.println("No participants found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewParticipantDetails() {
        System.out.print("Enter Participant ID : ");
        int id = sc.nextInt();

        String query = "SELECT * FROM Participant WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n========== PARTICIPANT DETAILS ==========");
                System.out.println("ID          : " + rs.getInt("id"));
                System.out.println("Name        : " + rs.getString("name"));
                System.out.println("Email       : " + rs.getString("email"));
                System.out.println("Dept        : " + rs.getString("dept"));
                System.out.println("College     : " + rs.getString("college"));
                System.out.println("Course      : " + rs.getString("course"));
                System.out.println("----------------------------------------");
                viewParticipantTrainingHistory(id);
            } else {
                System.out.println("Participant not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewParticipantTrainingHistory(int participantId) {
        String query = "SELECT ts.*, f.id as feedback_id, f.training_rating, f.instructor_rating, " +
                       "f.training_comment, f.anonymous " +
                       "FROM SessionRegistration sr " +
                       "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                       "LEFT JOIN Feedback f ON ts.session_id = f.session_id AND f.participant_id = sr.participant_id " +
                       "WHERE sr.participant_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Training History ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("\nSession : " + rs.getString("title"));
                System.out.println("Dates   : " + rs.getString("start_date") + " to " + rs.getString("end_date"));
                if (rs.getObject("feedback_id") != null) {
                    System.out.println("Feedback: Submitted (Training: " + rs.getInt("training_rating") + 
                                       "/5, Instructor: " + rs.getInt("instructor_rating") + "/5)");
                } else {
                    System.out.println("Feedback: Not Submitted");
                }
            }

            if (!hasData) {
                System.out.println("No training history found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void trackAttendance() {
        System.out.print("Enter Session ID : ");
        int sessionId = sc.nextInt();

        String query = "SELECT p.id, p.name, p.email, sr.registered_at, " +
                       "CASE WHEN f.id IS NOT NULL THEN 'Submitted' ELSE 'Pending' END as feedback_status " +
                       "FROM SessionRegistration sr " +
                       "JOIN Participant p ON sr.participant_id = p.id " +
                       "LEFT JOIN Feedback f ON f.session_id = sr.session_id AND f.participant_id = sr.participant_id " +
                       "WHERE sr.session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== ATTENDANCE FOR SESSION " + sessionId + " ==========");
            System.out.printf("%-10s %-20s %-25s %-20s%n", "ID", "Name", "Email", "Feedback Status");
            System.out.println("------------------------------------------------------------------------");

            boolean hasData = false;
            int submitted = 0, pending = 0;
            while (rs.next()) {
                hasData = true;
                String status = rs.getString("feedback_status");
                if ("Submitted".equals(status)) submitted++;
                else pending++;
                
                System.out.printf("%-10d %-20s %-25s %-20s%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    status);
            }

            if (hasData) {
                System.out.println("\nSummary: " + submitted + " submitted, " + pending + " pending");
            } else {
                System.out.println("No participants registered for this session!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFeedbackReminders() {
        System.out.print("Enter Session ID (0 for all sessions) : ");
        int sessionId = sc.nextInt();

        String query;
        if (sessionId == 0) {
            query = "SELECT p.id, p.name, ts.session_id, ts.title " +
                    "FROM Participant p " +
                    "JOIN SessionRegistration sr ON p.id = sr.participant_id " +
                    "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                    "WHERE sr.session_id NOT IN (SELECT session_id FROM Feedback WHERE participant_id = p.id)";
        } else {
            query = "SELECT p.id, p.name, ts.session_id, ts.title " +
                    "FROM Participant p " +
                    "JOIN SessionRegistration sr ON p.id = sr.participant_id " +
                    "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                    "WHERE ts.session_id=? AND sr.session_id NOT IN " +
                    "(SELECT session_id FROM Feedback WHERE participant_id = p.id)";
        }

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            if (sessionId != 0) ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== FEEDBACK REMINDERS SENT ==========");
            int count = 0;
            while (rs.next()) {
                Notification.NotificationManager.sendFeedbackReminder(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("session_id"),
                    rs.getString("title")
                );
                count++;
            }
            System.out.println("Total reminders sent: " + count);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== SESSION MANAGEMENT ==========

    public void createSession() {
        System.out.print("Session ID : ");
        int id = sc.nextInt();

        if (sessionExists(id)) {
            System.out.println("Session already exists!");
            return;
        }

        sc.nextLine();
        System.out.print("Title : ");
        String title = sc.nextLine();

        System.out.print("Description : ");
        String desc = sc.nextLine();

        System.out.print("Start Date (YYYY-MM-DD) : ");
        String start = sc.next();

        System.out.print("End Date (YYYY-MM-DD) : ");
        String end = sc.next();

        System.out.print("Time (HH:MM) : ");
        String time = sc.next();

        System.out.print("Duration (hours) : ");
        int duration = sc.nextInt();

        sc.nextLine();
        System.out.print("Location : ");
        String location = sc.nextLine();

        System.out.print("Max Participants : ");
        int max = sc.nextInt();

        String query = "INSERT INTO TrainingSession (session_id, title, description, start_date, end_date, time, duration, location, max_participants) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, desc);
            ps.setString(4, start);
            ps.setString(5, end);
            ps.setString(6, time);
            ps.setInt(7, duration);
            ps.setString(8, location);
            ps.setInt(9, max);
            ps.executeUpdate();
            System.out.println("Session Created Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignTrainer() {
        viewSessions();
        System.out.print("\nEnter Session ID : ");
        int sid = sc.nextInt();

        viewTrainers();
        System.out.print("Enter Trainer ID : ");
        int tid = sc.nextInt();

        if (!trainerExists(tid)) {
            System.out.println("Trainer not found!");
            return;
        }

        String query = "UPDATE TrainingSession SET trainer_id=? WHERE session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, tid);
            ps.setInt(2, sid);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Trainer Assigned Successfully!");
            } else {
                System.out.println("Session not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSessions() {
        String query = "SELECT ts.*, t.name as trainer_name FROM TrainingSession ts " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== ALL TRAINING SESSIONS ==========");
            System.out.printf("%-10s %-25s %-15s %-15s %-15s %-15s%n", 
                "ID", "Title", "Start Date", "End Date", "Trainer", "Participants");
            System.out.println("------------------------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-25s %-15s %-15s %-15s %-15d%n",
                    rs.getInt("session_id"),
                    rs.getString("title"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "Not Assigned",
                    getParticipantCount(rs.getInt("session_id")));
            }

            if (!hasData) {
                System.out.println("No sessions found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== SURVEY MANAGEMENT ==========

    public void createSurvey() {
        System.out.print("Survey ID : ");
        int surveyId = sc.nextInt();
        sc.nextLine();

        System.out.print("Survey Title : ");
        String title = sc.nextLine();

        System.out.print("Description : ");
        String desc = sc.nextLine();

        Survey survey = new Survey(surveyId, title, desc);

        System.out.print("How many questions? ");
        int numQuestions = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numQuestions; i++) {
            System.out.println("\n--- Question " + i + " ---");
            System.out.print("Question ID : ");
            int qId = sc.nextInt();
            sc.nextLine();

            System.out.print("Question Text : ");
            String qText = sc.nextLine();

            System.out.println("Question Types: 1. Rating, 2. Text, 3. Multiple Choice");
            System.out.print("Type (1/2/3) : ");
            int typeChoice = sc.nextInt();
            sc.nextLine();

            String qType = switch (typeChoice) {
                case 1 -> "RATING";
                case 2 -> "TEXT";
                case 3 -> "MULTIPLE_CHOICE";
                default -> "TEXT";
            };

            Survey.Question q = new Survey.Question(qId, qText, qType);

            if (qType.equals("MULTIPLE_CHOICE")) {
                System.out.print("How many options? ");
                int numOptions = sc.nextInt();
                sc.nextLine();
                for (int j = 1; j <= numOptions; j++) {
                    System.out.print("Option " + j + " : ");
                    String option = sc.nextLine();
                    q.addOption(option);
                }
            }

            survey.addQuestion(q);
        }

        surveys.add(survey);
        System.out.println("Survey Created Successfully!");
        survey.displaySurvey();
    }

    public void viewSurveys() {
        if (surveys.isEmpty()) {
            System.out.println("No surveys found!");
            return;
        }

        System.out.println("\n========== ALL SURVEYS ==========");
        for (Survey s : surveys) {
            s.displaySurvey();
            System.out.println();
        }
    }

    public void activateSurvey() {
        viewSurveys();
        System.out.print("\nEnter Survey ID to activate : ");
        int id = sc.nextInt();

        for (Survey s : surveys) {
            if (s.getSurveyId() == id) {
                s.setActive(true);
                System.out.println("Survey activated!");
                return;
            }
        }
        System.out.println("Survey not found!");
    }

    public void deactivateSurvey() {
        viewSurveys();
        System.out.print("\nEnter Survey ID to deactivate : ");
        int id = sc.nextInt();

        for (Survey s : surveys) {
            if (s.getSurveyId() == id) {
                s.setActive(false);
                System.out.println("Survey deactivated!");
                return;
            }
        }
        System.out.println("Survey not found!");
    }

    private void loadSurveys() {
        // Default survey
        Survey defaultSurvey = new Survey(1, "Training Feedback Survey", 
            "Please provide your feedback on the training session");
        
        defaultSurvey.addQuestion(new Survey.Question(1, "How would you rate the training content?", "RATING"));
        defaultSurvey.addQuestion(new Survey.Question(2, "How would you rate the instructor?", "RATING"));
        defaultSurvey.addQuestion(new Survey.Question(3, "How would you rate the training materials?", "RATING"));
        defaultSurvey.addQuestion(new Survey.Question(4, "How would you rate the overall experience?", "RATING"));
        defaultSurvey.addQuestion(new Survey.Question(5, "What did you like most about the training?", "TEXT"));
        defaultSurvey.addQuestion(new Survey.Question(6, "What areas need improvement?", "TEXT"));
        
        surveys.add(defaultSurvey);
    }

    // ========== FEEDBACK ANALYSIS & REPORTS ==========

    public void viewSessionReports() {
        String query = "SELECT ts.session_id, ts.title, ts.start_date, ts.end_date, " +
                       "t.name as trainer_name, COUNT(DISTINCT sr.participant_id) as participants, " +
                       "COUNT(DISTINCT f.id) as feedbacks " +
                       "FROM TrainingSession ts " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "LEFT JOIN SessionRegistration sr ON ts.session_id = sr.session_id " +
                       "LEFT JOIN Feedback f ON ts.session_id = f.session_id " +
                       "GROUP BY ts.session_id, ts.title, ts.start_date, ts.end_date, t.name";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== SESSION REPORTS ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("\n--- " + rs.getString("title") + " ---");
                System.out.println("Session ID   : " + rs.getInt("session_id"));
                System.out.println("Dates        : " + rs.getString("start_date") + " to " + rs.getString("end_date"));
                System.out.println("Trainer      : " + (rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "Not Assigned"));
                System.out.println("Participants : " + rs.getInt("participants"));
                System.out.println("Feedbacks    : " + rs.getInt("feedbacks"));
                
                int participants = rs.getInt("participants");
                int feedbacks = rs.getInt("feedbacks");
                if (participants > 0) {
                    double rate = (feedbacks * 100.0) / participants;
                    System.out.printf("Response Rate: %.1f%%%n", rate);
                }
            }

            if (!hasData) {
                System.out.println("No session data found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewTrainerPerformance() {
        String query = "SELECT t.id, t.name, t.course, t.email, " +
                       "COUNT(DISTINCT ts.session_id) as sessions, " +
                       "COUNT(DISTINCT f.id) as feedbacks, " +
                       "AVG(f.training_rating) as avg_training_rating, " +
                       "AVG(f.instructor_rating) as avg_instructor_rating " +
                       "FROM Trainer t " +
                       "LEFT JOIN TrainingSession ts ON t.id = ts.trainer_id " +
                       "LEFT JOIN Feedback f ON ts.session_id = f.session_id " +
                       "GROUP BY t.id, t.name, t.course, t.email";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== TRAINER PERFORMANCE REPORT ==========");
            System.out.printf("%-10s %-20s %-15s %-10s %-10s %-15s %-15s%n", 
                "ID", "Name", "Course", "Sessions", "Feedbacks", "Train Rating", "Inst Rating");
            System.out.println("-------------------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                double trainRating = rs.getDouble("avg_training_rating");
                double instRating = rs.getDouble("avg_instructor_rating");
                
                System.out.printf("%-10d %-20s %-15s %-10d %-10d %-15.2f %-15.2f%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("course"),
                    rs.getInt("sessions"),
                    rs.getInt("feedbacks"),
                    (trainRating > 0 ? trainRating : 0),
                    (instRating > 0 ? instRating : 0));
            }

            if (!hasData) {
                System.out.println("No trainer data found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSessionFeedbackAnalytics() {
        String query = "SELECT ts.session_id, ts.title, t.name as trainer_name, " +
                       "COUNT(f.id) as feedback_count, " +
                       "AVG(f.training_rating) as avg_training, " +
                       "AVG(f.instructor_rating) as avg_instructor, " +
                       "MIN(f.training_rating) as min_training, MAX(f.training_rating) as max_training " +
                       "FROM TrainingSession ts " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "LEFT JOIN Feedback f ON ts.session_id = f.session_id " +
                       "GROUP BY ts.session_id, ts.title, t.name";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== SESSION FEEDBACK ANALYTICS ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                int count = rs.getInt("feedback_count");
                double avgTraining = rs.getDouble("avg_training");
                double avgInstructor = rs.getDouble("avg_instructor");
                double overall = (avgTraining + avgInstructor) / 2;

                System.out.println("\n--- " + rs.getString("title") + " ---");
                System.out.println("Session ID     : " + rs.getInt("session_id"));
                System.out.println("Trainer        : " + (rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "N/A"));
                System.out.println("Total Feedbacks: " + count);
                System.out.printf("Avg Training   : %.2f/5%n", (avgTraining > 0 ? avgTraining : 0));
                System.out.printf("Avg Instructor : %.2f/5%n", (avgInstructor > 0 ? avgInstructor : 0));
                System.out.printf("Overall Rating : %.2f/5%n", (overall > 0 ? overall : 0));
                System.out.println("Performance    : " + getPerformanceLabel(overall > 0 ? overall : 0));
            }

            if (!hasData) {
                System.out.println("No feedback data found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewFeedbackTrends() {
        System.out.println("\n========== FEEDBACK TRENDS & INSIGHTS ==========");

        String query = "SELECT " +
                       "DATE_FORMAT(f.submitted_at, '%Y-%m') as month, " +
                       "COUNT(*) as total_feedbacks, " +
                       "AVG(f.training_rating) as avg_training, " +
                       "AVG(f.instructor_rating) as avg_instructor " +
                       "FROM Feedback f " +
                       "GROUP BY DATE_FORMAT(f.submitted_at, '%Y-%m') " +
                       "ORDER BY month DESC LIMIT 12";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-15s %-15s %-15s %-15s%n", "Month", "Feedbacks", "Train Avg", "Inst Avg");
            System.out.println("------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-15s %-15d %-15.2f %-15.2f%n",
                    rs.getString("month"),
                    rs.getInt("total_feedbacks"),
                    rs.getDouble("avg_training"),
                    rs.getDouble("avg_instructor"));
            }

            if (!hasData) {
                System.out.println("Not enough data for trends analysis.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        viewInstructorEvaluationSummary();
    }

    private void viewInstructorEvaluationSummary() {
        String query = "SELECT t.id, t.name, t.course, " +
                       "COUNT(f.id) as evaluations, " +
                       "AVG(f.instructor_rating) as avg_rating, " +
                       "f.instructor_comment " +
                       "FROM Trainer t " +
                       "JOIN TrainingSession ts ON t.id = ts.trainer_id " +
                       "JOIN Feedback f ON ts.session_id = f.session_id " +
                       "GROUP BY t.id, t.name, t.course, f.instructor_comment " +
                       "ORDER BY avg_rating DESC";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Instructor Evaluation Summary ---");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("\nTrainer     : " + rs.getString("name"));
                System.out.println("Course      : " + rs.getString("course"));
                System.out.printf("Avg Rating  : %.2f/5%n", rs.getDouble("avg_rating"));
                System.out.println("Evaluations : " + rs.getInt("evaluations"));
            }

            if (!hasData) {
                System.out.println("No instructor evaluation data found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateComprehensiveReport() {
        System.out.println("\n========== COMPREHENSIVE FEEDBACK REPORT ==========");
        System.out.println("==================================================\n");

        System.out.println(">>> OVERVIEW <<<");
        viewOverallStats();

        System.out.println("\n>>> SESSION PERFORMANCE <<<");
        viewSessionFeedbackAnalytics();

        System.out.println("\n>>> TRAINER PERFORMANCE <<<");
        viewTrainerPerformance();

        System.out.println("\n>>> FEEDBACK TRENDS <<<");
        viewFeedbackTrends();
    }

    private void viewOverallStats() {
        String participantQuery = "SELECT COUNT(*) as total FROM Participant";
        String trainerQuery = "SELECT COUNT(*) as total FROM Trainer WHERE approved=TRUE";
        String sessionQuery = "SELECT COUNT(*) as total FROM TrainingSession";
        String feedbackQuery = "SELECT COUNT(*) as total FROM Feedback";

        try {
            int participants = getCount(participantQuery);
            int trainers = getCount(trainerQuery);
            int sessions = getCount(sessionQuery);
            int feedbacks = getCount(feedbackQuery);

            System.out.println("Total Participants  : " + participants);
            System.out.println("Active Trainers     : " + trainers);
            System.out.println("Total Sessions      : " + sessions);
            System.out.println("Total Feedbacks     : " + feedbacks);
            System.out.printf("Avg Feedback/Session: %.1f%n", sessions > 0 ? (double) feedbacks / sessions : 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCount(String query) {
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ========== NOTIFICATIONS ==========

    public void viewAdminNotifications() {
        List<Notification> notifications = Notification.NotificationManager.getAdminNotifications();

        System.out.println("\n========== ADMIN NOTIFICATIONS ==========");
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (int i = notifications.size() - 1; i >= 0 && i >= notifications.size() - 10; i--) {
            Notification n = notifications.get(i);
            System.out.println("[" + n.getType() + "] " + n.getMessage());
        }
    }

    public void clearNotifications() {
        Notification.NotificationManager.clearNotifications("ADMIN", 0);
        System.out.println("Notifications cleared!");
    }

    // ========== HELPER METHODS ==========

    private boolean trainerExists(int id) {
        String query = "SELECT id FROM Trainer WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean sessionExists(int id) {
        String query = "SELECT session_id FROM TrainingSession WHERE session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getParticipantCount(int sessionId) {
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

    private String getPerformanceLabel(double avg) {
        if (avg >= 4.5) return "EXCELLENT";
        if (avg >= 3.5) return "GOOD";
        if (avg >= 2.5) return "AVERAGE";
        if (avg > 0) return "POOR";
        return "N/A";
    }
}

/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
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

/*
Class: ParticipantService
Module: Participant
By: Tamilarasu

Purpose: Handles participant operations (register, login, feedback)
OOPS: Encapsulation - private fields, Abstraction - hides JDBC
*/
public class ParticipantService {

    private Scanner sc;
    private Connection conn;
    private boolean fileMode;

    public ParticipantService() {
        this.sc = new Scanner(System.in);
        this.conn = DBConnection.getConnection();
        this.fileMode = InputUtil.isFileMode();
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

    private boolean isRegisteredForSession(int participantId, int sessionId) {
        String query = "SELECT * FROM SessionRegistration WHERE participant_id = ? AND session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: Database error.");
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
                        rs.getString("end_date") != null ? rs.getString("end_date") : "",
                        rs.getString("time") != null ? rs.getString("time") : "",
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
            System.out.println("Error: Unable to retrieve sessions.");
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
                        rs.getString("end_date") != null ? rs.getString("end_date") : "",
                        rs.getString("time") != null ? rs.getString("time") : "",
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
            System.out.println("Error: Unable to retrieve session.");
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
            boolean hasFeedback = hasGivenFeedback(p.getId(), ts.getSessionId());
            String status = hasFeedback ? "[Feedback Submitted]" : "[Pending Feedback]";
            System.out.println("  " + ts.getSessionId() + " | " + ts.getTitle() + " " + status);
        }

        int sid = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Session ID to submit feedback: ");
            sid = readInt();
            if (sid <= 0) {
                System.out.println("Error: Session ID must be a positive number.");
            } else {
                validId = true;
            }
        }

        TrainingSession ts = getSessionById(sid);

        if (ts == null) {
            System.out.println("Error: Invalid Session ID.");
            return;
        }

        if (!isRegisteredForSession(p.getId(), sid)) {
            System.out.println("Error: You are not registered for this session.");
            return;
        }

        if (hasGivenFeedback(p.getId(), sid)) {
            System.out.println("Error: You have already submitted feedback for this session.");
            return;
        }

        int rating = 0;
        boolean validRating = false;
        while (!validRating) {
            System.out.print("Session Rating (1-5): ");
            rating = readInt();
            if (rating < 1 || rating > 5) {
                System.out.println("Error: Rating must be between 1 and 5.");
            } else {
                validRating = true;
            }
        }

        skipLine();
        String comment = "";
        boolean validComment = false;
        while (!validComment) {
            System.out.print("Comment: ");
            comment = readLine().trim();
            if (comment.isEmpty()) {
                System.out.println("Error: Comment cannot be empty.");
            } else {
                validComment = true;
            }
        }
        
        System.out.print("Submit anonymously? (yes/no): ");
        String anonInput = readLine().trim().toLowerCase();
        boolean isAnonymous = anonInput.equals("yes") || anonInput.equals("y");
        
        if (isAnonymous) {
            System.out.println("[Note] Your feedback will be submitted anonymously.");
        }

        String query = "INSERT INTO Feedback (participant_id, session_id, rating, comment, instructor_rating, instructor_comment, is_anonymous) VALUES (?, ?, ?, ?, 0, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, p.getId());
            ps.setInt(2, sid);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.setString(5, "");
            ps.setBoolean(6, isAnonymous);
            
            int rowsInserted = ps.executeUpdate();
            ps.close();
            
            if (rowsInserted == 0) {
                System.out.println("Error: Failed to insert feedback.");
                return;
            }
            
            String verifyQuery = "SELECT * FROM Feedback WHERE participant_id = ? AND session_id = ?";
            PreparedStatement verifyPs = conn.prepareStatement(verifyQuery);
            verifyPs.setInt(1, p.getId());
            verifyPs.setInt(2, sid);
            ResultSet verifyRs = verifyPs.executeQuery();
            if (!verifyRs.next()) {
                System.out.println("Error: Feedback verification failed. Data may not have been saved.");
                verifyRs.close();
                verifyPs.close();
                return;
            }
            verifyRs.close();
            verifyPs.close();

            if (ts.getTrainer() != null) {
                int instrRating = 0;
                boolean validInstrRating = false;
                while (!validInstrRating) {
                    System.out.print("Instructor Rating (1-5): ");
                    instrRating = readInt();
                    if (instrRating < 1 || instrRating > 5) {
                        System.out.println("Error: Rating must be between 1 and 5.");
                    } else {
                        validInstrRating = true;
                    }
                }

                skipLine();
                String instrComment = "";
                boolean validInstrComment = false;
                while (!validInstrComment) {
                    System.out.print("Instructor Comment: ");
                    instrComment = readLine().trim();
                    if (instrComment.isEmpty()) {
                        System.out.println("Error: Comment cannot be empty.");
                    } else {
                        validInstrComment = true;
                    }
                }

                String updateQuery = "UPDATE Feedback SET instructor_rating = ?, instructor_comment = ? WHERE participant_id = ? AND session_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(updateQuery);
                ps2.setInt(1, instrRating);
                ps2.setString(2, instrComment);
                ps2.setInt(3, p.getId());
                ps2.setInt(4, sid);
                ps2.executeUpdate();
                ps2.close();
            }

            System.out.println("[Admin Notified] Feedback recorded.");
            System.out.println("Feedback submitted successfully!");
        } catch (SQLException e) {
            System.out.println("Error: Unable to submit feedback.");
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
            System.out.println("Error: Database error.");
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
                        rs.getString("end_date") != null ? rs.getString("end_date") : "",
                        rs.getString("time") != null ? rs.getString("time") : "",
                        rs.getInt("duration")
                );

                if (rs.getString("trainer_name") != null) {
                    Trainer t = new Trainer(rs.getInt("trainer_id"), rs.getString("trainer_name"), "");
                    ts.assignTrainer(t);
                }
                sessions.add(ts);
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve registered sessions.");
        }
        return sessions;
    }

    public void viewFeedbackHistory(Participant p) {
        System.out.println("\n--- Your Feedback History ---");

        TableFormatter.printFeedbackTableHeader();
        
        String query = "SELECT f.*, ts.title as session_title " +
                       "FROM Feedback f " +
                       "LEFT JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "WHERE f.participant_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, p.getId());
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                
                String sessionTitle = rs.getString("session_title");
                if (sessionTitle == null) {
                    sessionTitle = "Session ID: " + rs.getInt("session_id");
                }
                
                String participantName = rs.getBoolean("is_anonymous") ? "Anonymous" : "You";
                TableFormatter.printFeedbackRow(
                    sessionTitle,
                    participantName,
                    rs.getInt("rating"),
                    rs.getString("comment")
                );
                if (rs.getInt("instructor_rating") > 0) {
                    System.out.println("    Instructor: " + rs.getInt("instructor_rating") + "/5 | " + 
                                       rs.getString("instructor_comment"));
                }
            }
            
            TableFormatter.printFeedbackTableEnd();

            if (!found) {
                System.out.println("You have not submitted any feedback yet.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve feedback history.");
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
            }
        }

        if (!hasUnsubmitted) {
            System.out.println("No pending feedback reminders.");
        }
    }

    public void viewProfile(Participant p) {
        System.out.println("\n--- Your Profile ---");
        System.out.println("ID     : " + p.getId());
        System.out.println("Name   : " + p.getName());
        System.out.println("Email  : " + p.getEmail());
        System.out.println("Course : " + p.getCourse());
        System.out.println("Sessions Registered : " + getRegisteredSessions(p.getId()).size());
        System.out.println("Feedbacks Submitted : " + getFeedbackCount(p.getId()));
    }

    public void takeSurvey(Participant p) {
        System.out.println("\n--- Available Surveys ---");
        
        String query = "SELECT * FROM Survey WHERE active = TRUE ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            List<Integer> surveyIds = new ArrayList<>();
            int count = 0;
            
            while (rs.next()) {
                count++;
                int surveyId = rs.getInt("id");
                surveyIds.add(surveyId);
                System.out.println(count + ". " + rs.getString("title"));
                System.out.println("   Description: " + rs.getString("description"));
            }
            
            if (surveyIds.isEmpty()) {
                System.out.println("No active surveys available.");
                return;
            }
            
            System.out.print("\nSelect survey number: ");
            int selection = readInt();
            if (selection < 1 || selection > surveyIds.size()) {
                System.out.println("Error: Invalid selection.");
                return;
            }
            
            int selectedSurveyId = surveyIds.get(selection - 1);
            takeSurveyQuestions(p, selectedSurveyId);
            
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve surveys.");
        }
    }

    private void takeSurveyQuestions(Participant p, int surveyId) {
        String questionsQuery = "SELECT * FROM SurveyQuestion WHERE survey_id = ? ORDER BY question_id";
        
        try (PreparedStatement ps = conn.prepareStatement(questionsQuery)) {
            ps.setInt(1, surveyId);
            ResultSet rs = ps.executeQuery();
            
            List<String> answers = new ArrayList<>();
            
            while (rs.next()) {
                int qId = rs.getInt("question_id");
                String qText = rs.getString("question_text");
                String qType = rs.getString("question_type");
                String options = rs.getString("options");
                
                System.out.println("\nQ" + qId + ": " + qText);
                
                String answer = "";
                
                switch (qType) {
                    case "RATING":
                        System.out.print("Enter rating (1-5): ");
                        int rating = 0;
                        boolean validRating = false;
                        while (!validRating) {
                            rating = readInt();
                            if (rating >= 1 && rating <= 5) {
                                validRating = true;
                                answer = String.valueOf(rating);
                            } else {
                                System.out.println("Error: Rating must be between 1 and 5.");
                            }
                        }
                        break;
                        
                    case "YES_NO":
                        System.out.print("Enter yes/no: ");
                        answer = readLine().trim().toLowerCase();
                        while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("y") && !answer.equals("n")) {
                            System.out.println("Error: Please enter yes or no.");
                            answer = readLine().trim().toLowerCase();
                        }
                        break;
                        
                    case "MULTIPLE_CHOICE":
                        if (options != null && !options.isEmpty()) {
                            String[] opts = options.split(",");
                            System.out.println("Options:");
                            for (int i = 0; i < opts.length; i++) {
                                System.out.println("  " + (i + 1) + ". " + opts[i].trim());
                            }
                            System.out.print("Select option number: ");
                            int optSelect = 0;
                            boolean validOpt = false;
                            while (!validOpt) {
                                optSelect = readInt();
                                if (optSelect >= 1 && optSelect <= opts.length) {
                                    validOpt = true;
                                    answer = opts[optSelect - 1].trim();
                                } else {
                                    System.out.println("Error: Invalid option.");
                                }
                            }
                        }
                        break;
                        
                    case "TEXT":
                    default:
                        System.out.print("Enter your answer: ");
                        answer = readLine().trim();
                        while (answer.isEmpty()) {
                            System.out.println("Error: Answer cannot be empty.");
                            answer = readLine().trim();
                        }
                        break;
                }
                
                answers.add(answer);
            }
            
            saveSurveyResponse(p.getId(), surveyId, answers);
            System.out.println("\nSurvey submitted successfully!");
            
        } catch (SQLException e) {
            System.out.println("Error: Unable to load survey questions.");
            e.printStackTrace();
        }
    }

    private void saveSurveyResponse(int participantId, int surveyId, List<String> answers) {
        String insertQuery = "INSERT INTO SurveyResponse (survey_id, participant_id, answer_text, submitted_at) VALUES (?, ?, ?, NOW())";
        
        try (PreparedStatement ps = conn.prepareStatement(insertQuery)) {
            ps.setInt(1, surveyId);
            ps.setInt(2, participantId);
            ps.setString(3, String.join(" | ", answers));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: Unable to save survey response.");
            e.printStackTrace();
        }
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
            System.out.println("Error: Database error.");
        }
        return 0;
    }
}
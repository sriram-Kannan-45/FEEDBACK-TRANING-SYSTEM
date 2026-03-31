package com.trainingfeedback.service;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService {

    Scanner sc = new Scanner(System.in);
    private Connection conn;
<<<<<<< HEAD

    public AdminService() {
        this.conn = DBConnection.getConnection();
    }

<<<<<<< HEAD
   
    public void createTrainer() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        if (DataStorage.trainers.containsKey(id)) {
            System.out.println("Trainer ID already exists!");
=======
    public void createTrainer() {
        System.out.print("Trainer ID   : ");
        int id = sc.nextInt();

        if (trainerExists(id)) {
            System.out.println("Error: Trainer ID already exists!");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
            return;
        }

        System.out.print("Trainer Name : ");
        String name = sc.next();
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

        System.out.print("Password : ");
        String pass = sc.next();

<<<<<<< HEAD
        System.out.print("Course : ");
        String course = sc.next();

        String query = "INSERT INTO Trainer (id, name, password, approved, course) VALUES (?, ?, ?, FALSE, ?)";

<<<<<<< HEAD
        DataStorage.trainers.put(id, t);
        System.out.println("Trainer Created Successfully");
    }

    
    public void viewTrainers() {

        if (DataStorage.trainers.isEmpty()) {
            System.out.println("No Trainers Available");
            return;
        }

        for (Trainer t : DataStorage.trainers.values()) {
            t.display();
            System.out.println();
=======
=======
        sc.nextLine();
        System.out.print("Course/Expertise : ");
        String course = sc.nextLine();

        System.out.print("Email : ");
        String email = sc.next();

        System.out.print("Department : ");
        String dept = sc.next();

        String query = "INSERT INTO Trainer (id, name, password, approved, course, email, department) VALUES (?, ?, ?, FALSE, ?, ?, ?)";

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, course);
<<<<<<< HEAD
            ps.executeUpdate();
            System.out.println("Trainer created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating trainer!");
=======
            ps.setString(5, email);
            ps.setString(6, dept);
            ps.executeUpdate();
            System.out.println("Trainer Created Successfully! Pending approval.");
        } catch (Exception e) {
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    private boolean trainerExists(int id) {
        String query = "SELECT id FROM Trainer WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewTrainers() {
        String query = "SELECT * FROM Trainer";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Trainer ID : " + rs.getInt("id"));
                System.out.println("Trainer Name : " + rs.getString("name"));
                System.out.println("Approved : " + rs.getBoolean("approved"));
                System.out.println("Course : " + rs.getString("course"));
                System.out.println();
            }
            if (!found) {
                System.out.println("No trainers found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing trainers!");
            e.printStackTrace();
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
        }
    }

    public void viewParticipants() {
<<<<<<< HEAD

        if (DataStorage.participants.isEmpty()) {
            System.out.println("No Students Registered");
            return;
        }

        for (Participant p : DataStorage.participants) {
            p.display();
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            System.out.println();
        }
    }

<<<<<<< HEAD
    
=======
        String query = "SELECT * FROM Participant";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID     : " + rs.getInt("id"));
                System.out.println("Name   : " + rs.getString("name"));
                System.out.println("Email  : " + rs.getString("email"));
                System.out.println("Course : " + rs.getString("course"));
                System.out.println();
            }
            if (!found) {
                System.out.println("No participants found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing participants!");
            e.printStackTrace();
        }
    }

>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
    public void approveTrainer() {
        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

<<<<<<< HEAD
        Trainer t = DataStorage.trainers.get(id);

        if (t == null) {
            System.out.println("Trainer not found");
            return;
        }

        if (t.isApproved()) {
            System.out.println("Trainer already approved");
            return;
        }

        t.setApproved(true);
        System.out.println("Trainer Approved Successfully");
    }

   
    public void createSession() {

        System.out.print("Session ID: ");
        int id = sc.nextInt();

        if (DataStorage.sessions.containsKey(id)) {
            System.out.println("Session already exists!");
=======
        if (!trainerExists(id)) {
            System.out.println("Trainer not found.");
            return;
        }

        String checkQuery = "SELECT approved FROM Trainer WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkQuery)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getBoolean("approved")) {
                System.out.println("Trainer is already approved.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        String updateQuery = "UPDATE Trainer SET approved = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Trainer approved successfully.");
        } catch (SQLException e) {
            System.out.println("Error approving trainer!");
            e.printStackTrace();
        }
    }

    public void createSession() {
        System.out.print("Session ID : ");
        int sid = sc.nextInt();

        if (sessionExists(sid)) {
            System.out.println("Error: Session ID already exists!");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
            return;
        }

        System.out.print("Title: ");
        String title = sc.next();

        System.out.print("Start Date: ");
        String start = sc.next();

        System.out.print("End Date: ");
        String end = sc.next();

        System.out.print("Time: ");
        String time = sc.next();

        System.out.print("Duration (hrs): ");
        int duration = sc.nextInt();

<<<<<<< HEAD
        TrainingSession ts = new TrainingSession(id, title, start, end, time, duration);

        System.out.print("Enter Trainer ID to assign (0 to skip): ");
        int tid = sc.nextInt();

        if (tid != 0) {
            Trainer t = DataStorage.trainers.get(tid);

            if (t == null) {
                System.out.println("Invalid Trainer ID");
            } else if (!t.isApproved()) {
                System.out.println("Trainer not approved");
            } else {
                ts.assignTrainer(t);
                System.out.println("Trainer Assigned");
=======
        System.out.print("Assign Trainer ID (0 to skip): ");
        int tid = sc.nextInt();

        Integer trainerId = (tid != 0) ? tid : null;

        String query = "INSERT INTO TrainingSession (session_id, title, start_date, end_date, time, duration, trainer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sid);
            ps.setString(2, title);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setString(5, time);
            ps.setInt(6, duration);
            if (trainerId != null) {
                ps.setInt(7, trainerId);
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
            }
            ps.executeUpdate();

<<<<<<< HEAD
        DataStorage.sessions.put(id, ts);
        System.out.println("Session Created Successfully");
    }

    public void assignTrainer() {

        System.out.print("Session ID: ");
        int sid = sc.nextInt();

        System.out.print("Trainer ID: ");
        int tid = sc.nextInt();

        TrainingSession ts = DataStorage.sessions.get(sid);
        Trainer t = DataStorage.trainers.get(tid);

        if (ts == null || t == null) {
            System.out.println("Invalid IDs");
            return;
        }

        if (!t.isApproved()) {
            System.out.println("Trainer not approved");
            return;
        }

        ts.assignTrainer(t);
        System.out.println("Trainer Assigned Successfully");
    }
    public void viewSessions() {

        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No Sessions Available");
            return;
=======
            if (tid != 0) {
                Trainer trainer = getTrainerById(tid);
                if (trainer == null) {
                    System.out.println("Warning: Invalid Trainer ID. Session saved without trainer.");
                } else if (!trainer.isApproved()) {
                    System.out.println("Warning: Trainer not approved. Session saved without trainer.");
                } else {
                    System.out.println("Trainer '" + trainer.getName() + "' assigned.");
                }
            }

            System.out.println("Session created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating session!");
            e.printStackTrace();
        }
    }

    private boolean sessionExists(int id) {
        String query = "SELECT session_id FROM TrainingSession WHERE session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Trainer getTrainerById(int id) {
        String query = "SELECT * FROM Trainer WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Trainer t = new Trainer(rs.getInt("id"), rs.getString("name"), rs.getString("password"));
                t.setApproved(rs.getBoolean("approved"));
                String course = rs.getString("course");
                if (course != null) {
                    t.addCourse(course);
                }
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void viewSessionReports() {
        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved " +
                       "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\n============================");
                System.out.println("Session ID : " + rs.getInt("session_id"));
                System.out.println("Title      : " + rs.getString("title"));
                System.out.println("Duration   : " + rs.getInt("duration") + " hrs");
                System.out.println("Trainer    : " + (rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "Not Assigned"));

                int sessionId = rs.getInt("session_id");
                viewSessionFeedback(sessionId);
            }

            if (!found) {
                System.out.println("No sessions available.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing session reports!");
            e.printStackTrace();
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
        }
    }

<<<<<<< HEAD
        for (TrainingSession ts : DataStorage.sessions.values()) {
            ts.displaySession();
        }
    }

    public void viewSessionReports() {

        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No Sessions Available");
            return;
        }

        for (TrainingSession ts : DataStorage.sessions.values()) {
            System.out.println("\nSession: " + ts.getTitle());
            ts.viewSessionFeedback();
=======
    private void viewSessionFeedback(int sessionId) {
        String query = "SELECT f.*, p.name as participant_name " +
                       "FROM Feedback f JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE f.session_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            System.out.println("Feedback:");
            boolean hasFeedback = false;
            while (rs.next()) {
                hasFeedback = true;
                System.out.println("  Rating: " + rs.getInt("rating") + "/5 | By: " + 
                                   rs.getString("participant_name") + " | " + rs.getString("comment"));
                if (rs.getInt("instructor_rating") > 0) {
                    System.out.println("  Instructor Rating: " + rs.getInt("instructor_rating") + "/5 | " + 
                                       rs.getString("instructor_comment"));
                }
            }
            if (!hasFeedback) {
                System.out.println("  No feedback submitted yet.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
        }
    }

    public void viewSessionFeedbackAnalytics() {
        String query = "SELECT ts.*, t.name as trainer_name " +
                       "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id";

<<<<<<< HEAD
        if (DataStorage.sessions.isEmpty()) {
            System.out.println("No Sessions Available");
            return;
        }

        for (TrainingSession ts : DataStorage.sessions.values()) {
            System.out.println("\nSession: " + ts.getTitle());
            ts.printFeedbackAnalytics();
        }
    }

=======
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            System.out.println("\n===== Session Feedback Analytics =====");

            while (rs.next()) {
                System.out.println("\nSession : " + rs.getString("title") +
                        " [" + rs.getString("start_date") + " - " + rs.getString("end_date") + "]");
                System.out.println("Trainer : " + (rs.getString("trainer_name") != null ? rs.getString("trainer_name") : "Not Assigned"));

                int sessionId = rs.getInt("session_id");
                printFeedbackAnalytics(sessionId);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing analytics!");
            e.printStackTrace();
        }
    }

    private void printFeedbackAnalytics(int sessionId) {
        String query = "SELECT rating FROM Feedback WHERE session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            double total = 0;
            int count = 0;
            int[] dist = new int[6];

            while (rs.next()) {
                int r = rs.getInt("rating");
                total += r;
                count++;
                if (r >= 1 && r <= 5) dist[r]++;
            }

            if (count == 0) {
                System.out.println("  No feedback submitted yet.");
            } else {
                double avg = total / count;
                System.out.printf("  Total Feedbacks : %d%n", count);
                System.out.printf("  Average Rating  : %.2f / 5.00%n", avg);
                System.out.println("  Rating Distribution:");
                for (int i = 5; i >= 1; i--) {
                    String bar = "=".repeat(dist[i]);
                    System.out.printf("    %d star : %s (%d)%n", i, bar, dist[i]);
                }
            }
        } catch (SQLException e) {
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            e.printStackTrace();
        }
    }

    public void viewTrainerPerformance() {
<<<<<<< HEAD
        System.out.print("Enter Trainer ID: ");
        int tid = sc.nextInt();

        Trainer trainer = getTrainerById(tid);
        if (trainer == null) {
            System.out.println("Invalid Trainer ID.");
            return;
        }

        System.out.println("\n--- Trainer Performance Report ---");
        System.out.println("Trainer : " + trainer.getName());

        String query = "SELECT f.*, ts.title as session_title, p.name as participant_name " +
                       "FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE ts.trainer_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, tid);
            ResultSet rs = ps.executeQuery();

            double totalRating = 0;
            double totalInstrRating = 0;
            int count = 0;
            int instrCount = 0;

            while (rs.next()) {
                System.out.println("\n  Session: " + rs.getString("session_title"));

                int rating = rs.getInt("rating");
                totalRating += rating;
                count++;

                System.out.println("    " + rs.getString("participant_name") +
                        " | Session: " + rating + "/5" +
                        (rs.getInt("instructor_rating") > 0 ? " | Instructor: " + rs.getInt("instructor_rating") + "/5" : "") +
                        " | " + rs.getString("comment"));

                if (rs.getInt("instructor_rating") > 0) {
                    totalInstrRating += rs.getInt("instructor_rating");
                    instrCount++;
                }
            }

            System.out.println("\n  Summary");
            System.out.println("  -------");
            if (count == 0) {
                System.out.println("  No feedback received yet.");
            } else {
                System.out.printf("  Total Feedbacks     : %d%n", count);
                System.out.printf("  Avg Session Rating  : %.2f / 5.00%n", totalRating / count);
                if (instrCount > 0) {
                    System.out.printf("  Avg Instructor Rating: %.2f / 5.00%n", totalInstrRating / instrCount);
                }
            }
        } catch (SQLException e) {
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
    public void viewAdminNotifications() {
        System.out.println("\n===== Admin Notifications =====");
        System.out.println("No new notifications.");
    }
<<<<<<< HEAD


	public void viewTrainerPerformance() {
		// TODO Auto-generated method stub
		
	}
}
=======
}
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

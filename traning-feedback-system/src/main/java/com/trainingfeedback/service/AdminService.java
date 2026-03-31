package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.util.ValidationUtil;
import com.trainingfeedback.util.TableFormatter;

public class AdminService {

    private Scanner sc;
    private Connection conn;

    public AdminService() {
        this.sc = new Scanner(System.in);
        this.conn = DBConnection.getConnection();
    }

    public void createTrainer() {
        int id = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Trainer ID   : ");
            try {
                id = sc.nextInt();
                if (id <= 0) {
                    System.out.println("Error: ID must be a positive number.");
                } else if (trainerExists(id)) {
                    System.out.println("Error: Trainer ID already exists!");
                    return;
                } else {
                    validId = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        sc.nextLine();
        String name = "";
        boolean validName = false;
        while (!validName) {
            System.out.print("Trainer Name : ");
            name = sc.nextLine().trim();
            try {
                ValidationUtil.validateName(name);
                validName = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String pass = "";
        boolean validPass = false;
        while (!validPass) {
            System.out.print("Password     : ");
            pass = sc.nextLine().trim();
            if (pass.length() < 6) {
                System.out.println("Error: Password must be at least 6 characters.");
            } else {
                validPass = true;
            }
        }

        String course = "";
        boolean validCourse = false;
        while (!validCourse) {
            System.out.print("Course       : ");
            course = sc.nextLine().trim();
            if (course.isEmpty()) {
                System.out.println("Error: Course cannot be empty.");
            } else {
                validCourse = true;
            }
        }

        String query = "INSERT INTO Trainer (id, name, password, approved, course) VALUES (?, ?, ?, FALSE, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, course);
            ps.executeUpdate();
            System.out.println("Trainer created successfully.");
        } catch (SQLException e) {
            System.out.println("Error: Unable to create trainer. Please check database connection.");
        }
    }

    private boolean trainerExists(int id) {
        String query = "SELECT id FROM Trainer WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: Database error checking trainer.");
            return false;
        }
    }

    public void viewTrainers() {
        String query = "SELECT * FROM Trainer";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            TableFormatter.printTrainerTableHeader();
            while (rs.next()) {
                found = true;
                TableFormatter.printTrainerRow(
                    rs.getInt("id"),
                    rs.getString("name"),
                    "",  // Email not stored in Trainer table
                    rs.getString("course"),
                    rs.getBoolean("approved")
                );
            }
            TableFormatter.printTrainerTableEnd();
            if (!found) {
                System.out.println("No trainers found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve trainers.");
        }
    }

    public void viewParticipants() {
        String query = "SELECT * FROM Participant";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            TableFormatter.printParticipantTableHeader();
            while (rs.next()) {
                found = true;
                TableFormatter.printParticipantRow(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("course")
                );
            }
            TableFormatter.printParticipantTableEnd();
            if (!found) {
                System.out.println("No participants found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve participants.");
        }
    }

    public void approveTrainer() {
        int id = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Trainer ID : ");
            try {
                id = sc.nextInt();
                if (id <= 0) {
                    System.out.println("Error: ID must be a positive number.");
                } else {
                    validId = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        if (!trainerExists(id)) {
            System.out.println("Error: Trainer not found.");
            return;
        }

        if (isTrainerApproved(id)) {
            System.out.println("Trainer is already approved.");
            return;
        }

        String updateQuery = "UPDATE Trainer SET approved = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateQuery)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Trainer approved successfully.");
        } catch (SQLException e) {
            System.out.println("Error: Unable to approve trainer.");
        }
    }

    private boolean isTrainerApproved(int id) {
        String query = "SELECT approved FROM Trainer WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("approved");
            }
        } catch (SQLException e) {
            System.out.println("Error: Database error.");
        }
        return false;
    }

    public void createSession() {
        int sid = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Session ID : ");
            try {
                sid = sc.nextInt();
                if (sid <= 0) {
                    System.out.println("Error: Session ID must be a positive number.");
                } else if (sessionExists(sid)) {
                    System.out.println("Error: Session ID already exists!");
                    return;
                } else {
                    validId = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        sc.nextLine();
        String title = "";
        boolean validTitle = false;
        while (!validTitle) {
            System.out.print("Title      : ");
            title = sc.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Error: Title cannot be empty.");
            } else {
                validTitle = true;
            }
        }

        String start = "";
        boolean validStart = false;
        while (!validStart) {
            System.out.print("Start Date (dd/mm/yyyy): ");
            start = sc.nextLine().trim();
            try {
                ValidationUtil.validateDate(start);
                validStart = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String end = "";
        boolean validEnd = false;
        while (!validEnd) {
            System.out.print("End Date (dd/mm/yyyy): ");
            end = sc.nextLine().trim();
            try {
                ValidationUtil.validateDate(end);
                validEnd = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        int duration = 0;
        boolean validDur = false;
        while (!validDur) {
            System.out.print("Duration (hrs) : ");
            try {
                duration = sc.nextInt();
                if (duration <= 0) {
                    System.out.println("Error: Duration must be a positive number.");
                } else {
                    validDur = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        int tid = 0;
        boolean validTrainer = false;
        while (!validTrainer) {
            System.out.print("Assign Trainer ID (0 to skip): ");
            try {
                tid = sc.nextInt();
                if (tid < 0) {
                    System.out.println("Error: Trainer ID must be a positive number or 0.");
                } else {
                    validTrainer = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        Integer trainerId = (tid != 0) ? tid : null;

        if (trainerId != null && !trainerExists(trainerId)) {
            System.out.println("Error: Trainer not found. Session will be saved without trainer.");
            trainerId = null;
        } else if (trainerId != null && !isTrainerApproved(trainerId)) {
            System.out.println("Warning: Trainer not approved. Session will be saved without trainer.");
            trainerId = null;
        }

        String query = "INSERT INTO TrainingSession (session_id, title, start_date, end_date, duration, trainer_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sid);
            ps.setString(2, title);
            ps.setString(3, start);
            ps.setString(4, end);
            ps.setInt(5, duration);
            if (trainerId != null) {
                ps.setInt(6, trainerId);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
            System.out.println("Session created successfully.");
        } catch (SQLException e) {
            System.out.println("Error: Unable to create session.");
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
            System.out.println("Error: Database error.");
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
            System.out.println("Error: Database error.");
        }
        return null;
    }

    public void viewSessionReports() {
        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved " +
                       "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean found = false;
            TableFormatter.printSessionTableHeader();
            while (rs.next()) {
                found = true;
                TableFormatter.printSessionRow(
                    rs.getInt("session_id"),
                    rs.getString("title"),
                    rs.getString("start_date"),
                    rs.getString("end_date"),
                    rs.getInt("duration"),
                    rs.getString("trainer_name")
                );
            }
            TableFormatter.printSessionTableEnd();

            if (!found) {
                System.out.println("No sessions available.");
                return;
            }
            
            System.out.println("\n--- Feedback Details ---");
            rs = ps.executeQuery();
            while (rs.next()) {
                int sessionId = rs.getInt("session_id");
                viewSessionFeedback(sessionId);
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve session reports.");
            e.printStackTrace();
        }
    }

    private void viewSessionFeedback(int sessionId) {
        String query = "SELECT f.*, p.name as participant_name " +
                       "FROM Feedback f JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE f.session_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            TableFormatter.printFeedbackTableHeader();
            boolean hasFeedback = false;
            while (rs.next()) {
                hasFeedback = true;
                String participantName = rs.getBoolean("is_anonymous") ? "Anonymous" : rs.getString("participant_name");
                TableFormatter.printFeedbackRow(
                    String.valueOf(sessionId),
                    participantName,
                    rs.getInt("rating"),
                    rs.getString("comment")
                );
            }
            TableFormatter.printFeedbackTableEnd();
            if (!hasFeedback) {
                System.out.println("  No feedback submitted yet.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve feedback.");
        }
    }

    public void viewSessionFeedbackAnalytics() {
        String query = "SELECT ts.*, t.name as trainer_name " +
                       "FROM TrainingSession ts LEFT JOIN Trainer t ON ts.trainer_id = t.id";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            boolean hasAnySession = false;

            while (rs.next()) {
                hasAnySession = true;
                String sessionTitle = rs.getString("title") + " [" + rs.getString("start_date") + " - " + rs.getString("end_date") + "]";
                String trainerName = rs.getString("trainer_name");
                int sessionId = rs.getInt("session_id");
                printFeedbackAnalytics(sessionId, sessionTitle, trainerName);
            }

            if (!hasAnySession) {
                System.out.println("No sessions available.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve analytics.");
        }
    }

    private void printFeedbackAnalytics(int sessionId, String sessionTitle, String trainerName) {
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
                TableFormatter.printSessionAnalyticsReport(sessionTitle, trainerName, 0, 0.0, dist);
                System.out.println("No feedback submitted yet.");
            } else {
                double avg = total / count;
                TableFormatter.printSessionAnalyticsReport(sessionTitle, trainerName, count, avg, dist);
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to calculate analytics.");
        }
    }

    public void viewTrainerPerformance() {
        int tid = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Enter Trainer ID: ");
            try {
                tid = sc.nextInt();
                if (tid <= 0) {
                    System.out.println("Error: ID must be a positive number.");
                } else {
                    validId = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }

        Trainer trainer = getTrainerById(tid);
        if (trainer == null) {
            System.out.println("Error: Invalid Trainer ID.");
            return;
        }

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
            int[] dist = new int[6];

            while (rs.next()) {
                int rating = rs.getInt("rating");
                totalRating += rating;
                count++;
                if (rating >= 1 && rating <= 5) {
                    dist[rating]++;
                }

                if (rs.getInt("instructor_rating") > 0) {
                    totalInstrRating += rs.getInt("instructor_rating");
                    instrCount++;
                }
            }

            if (count == 0) {
                TableFormatter.printReportHeader("Trainer Performance", trainer.getName());
                System.out.println("No feedback received yet.");
                TableFormatter.printReportEnd();
            } else {
                TableFormatter.printTrainerPerformanceReport(trainer.getName(), count, totalRating / count, dist);
                if (instrCount > 0) {
                    System.out.printf("%-20s: %.2f / 5.00%n", "Avg Instructor Rating", totalInstrRating / instrCount);
                }
                System.out.println("========================================");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve performance data.");
        }
    }

    public void viewAdminNotifications() {
        System.out.println("\n===== Admin Notifications =====");
        System.out.println("No new notifications.");
    }
}
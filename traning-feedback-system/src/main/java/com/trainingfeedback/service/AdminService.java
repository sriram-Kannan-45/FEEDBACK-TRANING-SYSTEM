package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.trainingfeedback.model.*;

public class AdminService {

    Scanner sc = new Scanner(System.in);
    private Connection conn;

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

        System.out.print("Password : ");
        String pass = sc.next();

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
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, course);
            ps.executeUpdate();
            System.out.println("Trainer created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating trainer!");
            e.printStackTrace();
        }
    }

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
            System.out.println();
        }
    }

    
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
            e.printStackTrace();
        }
    }

    public void viewTrainerPerformance() {
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
            e.printStackTrace();
        }
    }

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

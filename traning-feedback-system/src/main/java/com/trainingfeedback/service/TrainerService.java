package com.trainingfeedback.service;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
=======
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
import com.trainingfeedback.model.*;

public class TrainerService {

<<<<<<< HEAD
=======
    Scanner sc = new Scanner(System.in);
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    private Connection conn;

    public TrainerService() {
        this.conn = DBConnection.getConnection();
    }

<<<<<<< HEAD
    public void viewCourses(Trainer t) {
        System.out.println("\n--- Your Courses ---");
        List<String> courses = getTrainerCourses(t.getId());

        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }

        for (String c : courses) {
            System.out.println("  " + c);
        }
    }

    private List<String> getTrainerCourses(int trainerId) {
        List<String> courses = new ArrayList<>();
        String query = "SELECT course FROM Trainer WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, trainerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String course = rs.getString("course");
                if (course != null && !course.isEmpty()) {
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void viewParticipants() {
        System.out.println("\n--- All Participants ---");
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
=======
    // ========== PROFILE & COURSES ==========

    public void viewProfile(Trainer t) {
        System.out.println("\n========== MY PROFILE ==========");
        System.out.println("ID       : " + t.getId());
        System.out.println("Name     : " + t.getName());
        System.out.println("Email    : " + t.getEmail());
        System.out.println("Course   : " + t.getCourse());
        System.out.println("Status   : " + (t.isApproved() ? "Approved" : "Pending Approval"));
        System.out.println("Courses  : " + t.getCourses());
    }

    public void viewCourses(Trainer t) {
        System.out.println("\n========== MY COURSES ==========");
        
        String query = "SELECT DISTINCT course FROM Trainer WHERE id=?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Primary Course: " + rs.getString("course"));
            }
            
            System.out.println("\nAdditional Courses:");
            for (String course : t.getCourses()) {
                System.out.println("  - " + course);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(Trainer t) {
        System.out.println("\n========== UPDATE PROFILE ==========");
        sc.nextLine();
        
        System.out.print("New Name (current: " + t.getName() + ") : ");
        String name = sc.nextLine();
        if (!name.isEmpty()) t.setName(name);

        System.out.print("Add new course : ");
        String course = sc.nextLine();
        if (!course.isEmpty()) t.addCourse(course);

        String query = "UPDATE Trainer SET name=? WHERE id=?";
        
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, t.getName());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            System.out.println("Profile updated successfully!");
        } catch (Exception e) {
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void viewMySessions(Trainer t) {
        System.out.println("\n--- My Sessions ---");

        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved " +
                       "FROM TrainingSession ts " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "WHERE ts.trainer_id = ?";
=======
    // ========== SESSION MANAGEMENT ==========

    public void viewMySessions(Trainer t) {
        String query = "SELECT * FROM TrainingSession WHERE trainer_id=? ORDER BY start_date DESC";
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

<<<<<<< HEAD
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Session ID : " + rs.getInt("session_id"));
                System.out.println("Title      : " + rs.getString("title"));
                System.out.println("Duration   : " + rs.getInt("duration") + " hrs");
                System.out.println("  Participants : " + getParticipantCountForSession(rs.getInt("session_id")));
                System.out.println("  Feedback:");
                viewSessionFeedback(rs.getInt("session_id"));
                System.out.println();
            }

            if (!found) {
                System.out.println("No sessions assigned to you.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing sessions!");
=======
            System.out.println("\n========== MY TRAINING SESSIONS ==========");
            boolean hasData = false;
            
            List<Integer> sessionIds = new ArrayList<>();
            while (rs.next()) {
                hasData = true;
                sessionIds.add(rs.getInt("session_id"));
                
                System.out.println("\n--- Session ID: " + rs.getInt("session_id") + " ---");
                System.out.println("Title       : " + rs.getString("title"));
                System.out.println("Description : " + rs.getString("description"));
                System.out.println("Date        : " + rs.getString("start_date") + " to " + rs.getString("end_date"));
                System.out.println("Time        : " + rs.getString("time"));
                System.out.println("Duration    : " + rs.getInt("duration") + " hours");
                System.out.println("Location    : " + rs.getString("location"));
                System.out.println("Participants: " + getParticipantCount(rs.getInt("session_id")));
            }

            if (!hasData) {
                System.out.println("No sessions assigned to you yet!");
            }

        } catch (Exception e) {
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    private int getParticipantCountForSession(int sessionId) {
        String query = "SELECT COUNT(*) FROM SessionRegistration WHERE session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
=======
    public void viewSessionDetails(Trainer t) {
        System.out.print("Enter Session ID : ");
        int sessionId = sc.nextInt();

        if (!isMySession(t.getId(), sessionId)) {
            System.out.println("This session is not assigned to you!");
            return;
        }

        String query = "SELECT * FROM TrainingSession WHERE session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n========== SESSION DETAILS ==========");
                System.out.println("Session ID    : " + rs.getInt("session_id"));
                System.out.println("Title         : " + rs.getString("title"));
                System.out.println("Description   : " + rs.getString("description"));
                System.out.println("Start Date    : " + rs.getString("start_date"));
                System.out.println("End Date      : " + rs.getString("end_date"));
                System.out.println("Time          : " + rs.getString("time"));
                System.out.println("Duration      : " + rs.getInt("duration") + " hours");
                System.out.println("Location      : " + rs.getString("location"));
                System.out.println("Max Participants: " + rs.getInt("max_participants"));
                
                viewSessionParticipants(sessionId);
                viewSessionFeedbackSummary(sessionId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== PARTICIPANT MANAGEMENT ==========

    public void viewParticipants() {
        String query = "SELECT DISTINCT p.id, p.name, p.email, p.dept, p.college " +
                       "FROM Participant p " +
                       "JOIN SessionRegistration sr ON p.id = sr.participant_id " +
                       "JOIN TrainingSession ts ON sr.session_id = ts.session_id " +
                       "WHERE ts.trainer_id IS NOT NULL";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== ALL PARTICIPANTS ==========");
            System.out.printf("%-10s %-20s %-25s %-15s %-15s%n", "ID", "Name", "Email", "Dept", "College");
            System.out.println("-------------------------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-20s %-25s %-15s %-15s%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("dept"),
                    rs.getString("college"));
            }

            if (!hasData) {
                System.out.println("No participants found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSessionParticipants(int sessionId) {
        String query = "SELECT p.id, p.name, p.email, p.dept, sr.registered_at, " +
                       "CASE WHEN f.id IS NOT NULL THEN 'Submitted' ELSE 'Pending' END as feedback_status " +
                       "FROM Participant p " +
                       "JOIN SessionRegistration sr ON p.id = sr.participant_id " +
                       "LEFT JOIN Feedback f ON f.session_id = sr.session_id AND f.participant_id = p.id " +
                       "WHERE sr.session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Registered Participants ---");
            System.out.printf("%-10s %-20s %-25s %-15s%n", "ID", "Name", "Email", "Feedback");
            System.out.println("------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-20s %-25s %-15s%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("feedback_status"));
            }

            if (!hasData) {
                System.out.println("No participants registered yet!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAttendance(Trainer t) {
        System.out.println("\n========== SESSION ATTENDANCE ==========");

        String query = "SELECT ts.session_id, ts.title, ts.start_date, ts.end_date, " +
                       "COUNT(DISTINCT sr.participant_id) as registered, " +
                       "COUNT(DISTINCT f.id) as feedback_submitted " +
                       "FROM TrainingSession ts " +
                       "LEFT JOIN SessionRegistration sr ON ts.session_id = sr.session_id " +
                       "LEFT JOIN Feedback f ON ts.session_id = f.session_id " +
                       "WHERE ts.trainer_id=? " +
                       "GROUP BY ts.session_id, ts.title, ts.start_date, ts.end_date";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-10s %-25s %-15s %-12s %-12s%n", "ID", "Title", "Date", "Registered", "Feedback");
            System.out.println("----------------------------------------------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%-10d %-25s %-15s %-12d %-12d%n",
                    rs.getInt("session_id"),
                    rs.getString("title"),
                    rs.getString("start_date"),
                    rs.getInt("registered"),
                    rs.getInt("feedback_submitted"));
            }

            if (!hasData) {
                System.out.println("No sessions found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== FEEDBACK MANAGEMENT ==========

    public void viewFeedbackReport(Trainer t) {
        System.out.println("\n========== FEEDBACK REPORT ==========");

        String query = "SELECT f.*, ts.title as session_title, p.name as participant_name " +
                       "FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "LEFT JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE ts.trainer_id=? " +
                       "ORDER BY f.submitted_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

            double totalTraining = 0, totalInstructor = 0;
            int count = 0;

            while (rs.next()) {
                count++;
                System.out.println("\n--- Feedback #" + count + " ---");
                System.out.println("Session : " + rs.getString("session_title"));
                System.out.println("From    : " + (rs.getBoolean("anonymous") ? "Anonymous" : rs.getString("participant_name")));
                System.out.println("Training Rating   : " + rs.getInt("training_rating") + "/5");
                System.out.println("Instructor Rating : " + rs.getInt("instructor_rating") + "/5");
                System.out.println("Training Comment  : " + (rs.getString("training_comment") != null ? rs.getString("training_comment") : "N/A"));
                System.out.println("Instructor Comment: " + (rs.getString("instructor_comment") != null ? rs.getString("instructor_comment") : "N/A"));
                
                if (rs.getString("trainer_response") != null) {
                    System.out.println("My Response: " + rs.getString("trainer_response"));
                } else {
                    System.out.println("Response   : [Not Responded]");
                }

                totalTraining += rs.getInt("training_rating");
                totalInstructor += rs.getInt("instructor_rating");
            }

            if (count > 0) {
                System.out.println("\n========== SUMMARY ==========");
                System.out.printf("Total Feedbacks      : %d%n", count);
                System.out.printf("Avg Training Rating  : %.2f/5%n", totalTraining / count);
                System.out.printf("Avg Instructor Rating: %.2f/5%n", totalInstructor / count);
            } else {
                System.out.println("No feedback received yet!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewSessionFeedbackSummary(int sessionId) {
        String query = "SELECT COUNT(*) as total, AVG(training_rating) as avg_training, " +
                       "AVG(instructor_rating) as avg_instructor " +
                       "FROM Feedback WHERE session_id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                if (total > 0) {
                    System.out.println("\n--- Feedback Summary ---");
                    System.out.println("Total Feedbacks     : " + total);
                    System.out.printf("Avg Training Rating : %.2f/5%n", rs.getDouble("avg_training"));
                    System.out.printf("Avg Instructor Rating: %.2f/5%n", rs.getDouble("avg_instructor"));
                } else {
                    System.out.println("\n--- No feedback submitted yet ---");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void respondToFeedback(int feedbackId, String response) {
        String query = "UPDATE Feedback SET trainer_response=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, response);
            ps.setInt(2, feedbackId);
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Response submitted successfully!");
            } else {
                System.out.println("Feedback not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void respondToFeedback(Trainer t) {
        System.out.print("Enter Feedback ID to respond : ");
        int feedbackId = sc.nextInt();
        sc.nextLine();

        if (!isFeedbackForMySession(t.getId(), feedbackId)) {
            System.out.println("This feedback is not for your session!");
            return;
        }

        System.out.println("\n--- Feedback Details ---");
        viewFeedbackDetail(feedbackId);

        System.out.print("\nEnter your response : ");
        String response = sc.nextLine();

        respondToFeedback(feedbackId, response);
    }

    private void viewFeedbackDetail(int feedbackId) {
        String query = "SELECT f.*, ts.title, p.name as participant_name " +
                       "FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "LEFT JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE f.id=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, feedbackId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Session           : " + rs.getString("title"));
                System.out.println("Participant      : " + (rs.getBoolean("anonymous") ? "Anonymous" : rs.getString("participant_name")));
                System.out.println("Training Rating  : " + rs.getInt("training_rating") + "/5");
                System.out.println("Instructor Rating: " + rs.getInt("instructor_rating") + "/5");
                System.out.println("Training Comment : " + rs.getString("training_comment"));
                System.out.println("Instructor Comment: " + rs.getString("instructor_comment"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== INSTRUCTOR EVALUATION ==========

    public void viewInstructorEvaluation(Trainer t) {
        System.out.println("\n========== INSTRUCTOR EVALUATION ==========");

        String query = "SELECT f.instructor_rating, f.instructor_comment, f.anonymous, " +
                       "ts.title as session_title, p.name as participant_name " +
                       "FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "LEFT JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE ts.trainer_id=? AND f.instructor_rating > 0 " +
                       "ORDER BY f.submitted_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

            int[] ratingCounts = {0, 0, 0, 0, 0};
            int totalRatings = 0;
            double sumRatings = 0;

            while (rs.next()) {
                int rating = rs.getInt("instructor_rating");
                totalRatings++;
                sumRatings += rating;
                if (rating >= 1 && rating <= 5) ratingCounts[rating - 1]++;

                System.out.println("\nSession : " + rs.getString("session_title"));
                System.out.println("Rating  : " + rating + "/5");
                System.out.println("From    : " + (rs.getBoolean("anonymous") ? "Anonymous" : rs.getString("participant_name")));
                System.out.println("Comment : " + (rs.getString("instructor_comment") != null ? rs.getString("instructor_comment") : "No comment"));
            }

            if (totalRatings > 0) {
                System.out.println("\n========== EVALUATION SUMMARY ==========");
                System.out.printf("Total Evaluations : %d%n", totalRatings);
                System.out.printf("Average Rating     : %.2f/5%n", sumRatings / totalRatings);
                System.out.println("\nRating Distribution:");
                System.out.println("5 Stars: " + ratingCounts[4] + " (" + (ratingCounts[4] * 100 / totalRatings) + "%)");
                System.out.println("4 Stars: " + ratingCounts[3] + " (" + (ratingCounts[3] * 100 / totalRatings) + "%)");
                System.out.println("3 Stars: " + ratingCounts[2] + " (" + (ratingCounts[2] * 100 / totalRatings) + "%)");
                System.out.println("2 Stars: " + ratingCounts[1] + " (" + (ratingCounts[1] * 100 / totalRatings) + "%)");
                System.out.println("1 Star : " + ratingCounts[0] + " (" + (ratingCounts[0] * 100 / totalRatings) + "%)");
            } else {
                System.out.println("No instructor evaluations found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== SEARCH ==========

    public void searchParticipant(String name) {
        String query = "SELECT id, name, email, dept, college, course FROM Participant WHERE name LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            System.out.println("\n========== SEARCH RESULTS ==========");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("\nID      : " + rs.getInt("id"));
                System.out.println("Name    : " + rs.getString("name"));
                System.out.println("Email   : " + rs.getString("email"));
                System.out.println("Dept    : " + rs.getString("dept"));
                System.out.println("College : " + rs.getString("college"));
                System.out.println("Course  : " + rs.getString("course"));
            }

            if (!hasData) {
                System.out.println("No participants found with name: " + name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ========== HELPER METHODS ==========

    private int getParticipantCount(int sessionId) {
        String query = "SELECT COUNT(*) as count FROM SessionRegistration WHERE session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("count");
        } catch (Exception e) {
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            e.printStackTrace();
        }
        return 0;
    }

<<<<<<< HEAD
    private void viewSessionFeedback(int sessionId) {
        String query = "SELECT f.*, p.name as participant_name " +
                       "FROM Feedback f " +
                       "JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE f.session_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

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
        }
=======
    private boolean isMySession(int trainerId, int sessionId) {
        String query = "SELECT session_id FROM TrainingSession WHERE trainer_id=? AND session_id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, trainerId);
            ps.setInt(2, sessionId);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isFeedbackForMySession(int trainerId, int feedbackId) {
        String query = "SELECT f.id FROM Feedback f " +
                       "JOIN TrainingSession ts ON f.session_id = ts.session_id " +
                       "WHERE ts.trainer_id=? AND f.id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, trainerId);
            ps.setInt(2, feedbackId);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ========== NOTIFICATIONS ==========

    public void viewMyNotifications(Trainer t) {
        List<Notification> notifications = Notification.NotificationManager.getNotificationsForUser("TRAINER", t.getId());

        System.out.println("\n========== MY NOTIFICATIONS ==========");
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (int i = notifications.size() - 1; i >= 0 && i >= notifications.size() - 10; i--) {
            Notification n = notifications.get(i);
            System.out.println("[" + n.getType() + "] " + n.getMessage());
        }
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    }
}

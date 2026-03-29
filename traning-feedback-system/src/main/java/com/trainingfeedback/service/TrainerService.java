package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.trainingfeedback.model.*;

public class TrainerService {

    private Connection conn;

    public TrainerService() {
        this.conn = DBConnection.getConnection();
    }

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
            e.printStackTrace();
        }
    }

    public void viewMySessions(Trainer t) {
        System.out.println("\n--- My Sessions ---");

        String query = "SELECT ts.*, t.name as trainer_name, t.approved as trainer_approved " +
                       "FROM TrainingSession ts " +
                       "LEFT JOIN Trainer t ON ts.trainer_id = t.id " +
                       "WHERE ts.trainer_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

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
            e.printStackTrace();
        }
    }

    private int getParticipantCountForSession(int sessionId) {
        String query = "SELECT COUNT(*) FROM SessionRegistration WHERE session_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

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
    }
}

package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.trainingfeedback.model.*;
import com.trainingfeedback.util.TableFormatter;

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
                System.out.println("No sessions assigned to you.");
            } else {
                System.out.println("\n--- Feedback Details ---");
                ps.setInt(1, t.getId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    viewSessionFeedback(rs.getInt("session_id"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve sessions.");
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
                       "LEFT JOIN Participant p ON f.participant_id = p.id " +
                       "WHERE f.session_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, sessionId);
            ResultSet rs = ps.executeQuery();

            TableFormatter.printFeedbackTableHeader();
            boolean hasFeedback = false;
            while (rs.next()) {
                hasFeedback = true;
                String participantName = "Unknown";
                try {
                    participantName = rs.getBoolean("is_anonymous") ? "Anonymous" : rs.getString("participant_name");
                    if (participantName == null || participantName.isEmpty()) {
                        participantName = "Participant " + rs.getInt("participant_id");
                    }
                } catch (SQLException e) {
                    participantName = "Participant " + rs.getInt("participant_id");
                }
                TableFormatter.printFeedbackRow(
                    String.valueOf(sessionId),
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
            if (!hasFeedback) {
                System.out.println("  No feedback submitted yet.");
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to retrieve feedback.");
            e.printStackTrace();
        }
    }
}

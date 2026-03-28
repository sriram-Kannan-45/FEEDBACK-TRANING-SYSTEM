package com.trainingfeedback.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.trainingfeedback.model.TrainingSession;
import com.trainingfeedback.model.Trainer;

public class SessionDAO {

    private Connection con;

    public SessionDAO() {
        this.con = DBConnection.getConnection();
        if (this.con == null) {
            System.out.println("ERROR: SessionDAO could not get DB connection!");
        }
    }

    // ─────────────────────────────────────────
    // CREATE SESSION
    // replaces → DataStorage.sessions.put(id, ts)
    // ─────────────────────────────────────────
    public boolean createSession(TrainingSession ts) {

        String sql = "INSERT INTO sessions (id, title, trainer_id) VALUES (?, ?, NULL)";

        try {
            // check session ID already exists
            if (sessionExists(ts.getSessionId())) {
                System.out.println("Session already exists!");
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ts.getSessionId());
            ps.setString(2, ts.getTitle());

            ps.executeUpdate();

            System.out.println("Session Created!");
            return true;

        } catch (Exception e) {
            System.out.println("Create session error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────
    // ASSIGN TRAINER TO SESSION
    // replaces → ts.assignTrainer(t) in AdminService
    // ─────────────────────────────────────────
    public boolean assignTrainer(int sessionId, int trainerId) {

        String sql = "UPDATE sessions SET trainer_id = ? WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, trainerId);
            ps.setInt(2, sessionId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Trainer Assigned!");
                return true;
            } else {
                System.out.println("Session not found!");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Assign trainer error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────
    // GET SESSION BY ID
    // replaces → DataStorage.sessions.get(sid)
    // ─────────────────────────────────────────
    public TrainingSession findById(int id) {

        String sql = "SELECT s.*, t.name AS trainer_name, t.password AS trainer_pass, "
                   + "t.approved FROM sessions s "
                   + "LEFT JOIN trainers t ON s.trainer_id = t.id "
                   + "WHERE s.id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TrainingSession ts = new TrainingSession(
                    rs.getInt("id"),
                    rs.getString("title")
                );

                // attach trainer if assigned
                int trainerId = rs.getInt("trainer_id");
                if (!rs.wasNull()) {
                    Trainer t = new Trainer(
                        trainerId,
                        rs.getString("trainer_name"),
                        rs.getString("trainer_pass")
                    );
                    t.setApproved(rs.getBoolean("approved"));
                    ts.assignTrainer(t);
                }

                // load feedback for this session
                loadFeedback(ts);

                return ts;
            }

        } catch (Exception e) {
            System.out.println("Find session error: " + e.getMessage());
        }

        return null; // not found
    }

    // ─────────────────────────────────────────
    // GET ALL SESSIONS
    // replaces → DataStorage.sessions.values()
    // ─────────────────────────────────────────
    public ArrayList<TrainingSession> getAllSessions() {

        ArrayList<TrainingSession> list = new ArrayList<>();

        String sql = "SELECT s.*, t.id AS tid, t.name AS trainer_name, "
                   + "t.password AS trainer_pass, t.approved "
                   + "FROM sessions s "
                   + "LEFT JOIN trainers t ON s.trainer_id = t.id";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                TrainingSession ts = new TrainingSession(
                    rs.getInt("id"),
                    rs.getString("title")
                );

                // attach trainer if assigned
                int trainerId = rs.getInt("tid");
                if (!rs.wasNull()) {
                    Trainer t = new Trainer(
                        trainerId,
                        rs.getString("trainer_name"),
                        rs.getString("trainer_pass")
                    );
                    t.setApproved(rs.getBoolean("approved"));
                    ts.assignTrainer(t);
                }

                // load feedback for each session
                loadFeedback(ts);

                list.add(ts);
            }

        } catch (Exception e) {
            System.out.println("Get all sessions error: " + e.getMessage());
        }

        return list;
    }

    // ─────────────────────────────────────────
    // SUBMIT FEEDBACK
    // replaces → ts.addFeedback() in ParticipantService
    // ─────────────────────────────────────────
    public boolean submitFeedback(int participantId, int sessionId,
                                  int rating, String comment) {

        // check already submitted
        if (hasGivenFeedback(participantId, sessionId)) {
            System.out.println("Already Submitted!");
            return false;
        }

        String feedbackText = "Rating: " + rating + " | " + comment;

        String sql = "INSERT INTO feedback (participant_id, session_id, feedback_text) "
                   + "VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);
            ps.setString(3, feedbackText);

            ps.executeUpdate();

            System.out.println("Feedback Submitted!");
            return true;

        } catch (Exception e) {
            System.out.println("Submit feedback error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────
    // CHECK IF FEEDBACK ALREADY GIVEN
    // replaces → ts.hasGivenFeedback(p.getId())
    // ─────────────────────────────────────────
    public boolean hasGivenFeedback(int participantId, int sessionId) {

        String sql = "SELECT id FROM feedback WHERE participant_id = ? AND session_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, participantId);
            ps.setInt(2, sessionId);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true = already submitted

        } catch (Exception e) {
            System.out.println("Check feedback error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────
    // LOAD FEEDBACK INTO SESSION OBJECT
    // replaces → ts.feedbackList loading
    // ─────────────────────────────────────────
    private void loadFeedback(TrainingSession ts) {

        String sql = "SELECT f.feedback_text, f.participant_id "
                   + "FROM feedback f WHERE f.session_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ts.getSessionId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ts.addFeedback(
                    rs.getInt("participant_id"),
                    rs.getString("feedback_text")
                );
            }

        } catch (Exception e) {
            System.out.println("Load feedback error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // HELPER - check if session ID already exists
    // ─────────────────────────────────────────
    private boolean sessionExists(int id) {

        String sql = "SELECT id FROM sessions WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Session exists check error: " + e.getMessage());
            return false;
        }
    }
}

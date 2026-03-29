package com.trainingfeedback.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.trainingfeedback.model.Participant;
import com.trainingfeedback.model.TrainingSession;
import com.trainingfeedback.model.Trainer;

public class TrainerDAO {

    private Connection con;

    public TrainerDAO() {
        this.con = DBConnection.getConnection();
        if (this.con == null) {
            System.out.println("ERROR: TrainerDAO could not get DB connection!");
        }
    }

    // ─────────────────────────────────────────
    // VIEW TRAINER'S OWN SESSIONS
    // replaces → DataStorage.sessions loop in TrainerService
    // ─────────────────────────────────────────
    public ArrayList<TrainingSession> getMySessions(int trainerId) {   // ✅ typo fixed (was getMySessionss)

        ArrayList<TrainingSession> list = new ArrayList<>();

        String sql = "SELECT * FROM sessions WHERE trainer_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, trainerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TrainingSession ts = new TrainingSession(
                    rs.getInt("id"),
                    rs.getString("title")
                );
                list.add(ts);
            }

        } catch (Exception e) {
            System.out.println("Get my sessions error: " + e.getMessage());
        }

        return list;
    }

    // ─────────────────────────────────────────
    // VIEW ALL PARTICIPANTS
    // replaces → DataStorage.participants in TrainerService
    // ─────────────────────────────────────────
    public ArrayList<Participant> getAllParticipants() {

        ArrayList<Participant> list = new ArrayList<>();
        String sql = "SELECT * FROM participants";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                list.add(new Participant(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("dept"),
                    rs.getString("college"),
                    rs.getString("course")
                ));
            }

        } catch (Exception e) {
            System.out.println("Get participants error: " + e.getMessage());
        }

        return list;
    }

    // ─────────────────────────────────────────
    // VIEW COURSES OF A TRAINER
    // replaces → t.getCourses() in TrainerService
    // ─────────────────────────────────────────
    public ArrayList<String> getCourses(int trainerId) {

        ArrayList<String> courses = new ArrayList<>();
        String sql = "SELECT course FROM trainer_courses WHERE trainer_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, trainerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                courses.add(rs.getString("course"));
            }

        } catch (Exception e) {
            System.out.println("Get courses error: " + e.getMessage());
        }

        return courses;
    }

    // ─────────────────────────────────────────
    // GET TRAINER BY ID
    // replaces → DataStorage.trainers.get(id)
    // ─────────────────────────────────────────
    public Trainer findById(int id) {

        String sql = "SELECT * FROM trainers WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Trainer t = new Trainer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password")
                );
                t.setApproved(rs.getBoolean("approved"));

                // load courses
                ArrayList<String> courses = getCourses(t.getId());
                for (String c : courses) {
                    t.addCourse(c);
                }

                return t;
            }

        } catch (Exception e) {
            System.out.println("Find trainer error: " + e.getMessage());
        }

        return null; // not found
    }
}
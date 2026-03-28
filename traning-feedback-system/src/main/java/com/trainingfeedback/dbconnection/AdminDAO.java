package com.trainingfeedback.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.trainingfeedback.model.Participant;
import com.trainingfeedback.model.Trainer;

public class AdminDAO {

    private Connection con;

    public AdminDAO() {
        this.con = DBConnection.getConnection();
        if (this.con == null) {
            System.out.println("ERROR: AdminDAO could not get DB connection!");
        }
    }

    // ─────────────────────────────────────────
    // CREATE TRAINER
    // replaces → DataStorage.trainers.put(id, t)
    // ─────────────────────────────────────────
    public boolean createTrainer(Trainer t, String course) {

        String sql = "INSERT INTO trainers (id, name, password, approved) VALUES (?, ?, ?, ?)";

        try {
            // check if trainer ID already exists
            if (trainerExists(t.getId())) {
                System.out.println("Error: Trainer ID already exists!");
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, t.getId());
            ps.setString(2, t.getName());
            ps.setString(3, t.getPassword());
            ps.setBoolean(4, false); // not approved by default

            ps.executeUpdate();

            // save the course in trainer_courses table
            saveCourse(t.getId(), course);

            System.out.println("Trainer Created Successfully!");
            return true;

        } catch (Exception e) {
            System.out.println("Create trainer error: " + e.getMessage());
            return false;
        }
    }

    // save course for a trainer
    private void saveCourse(int trainerId, String course) {

        String sql = "INSERT INTO trainer_courses (trainer_id, course) VALUES (?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, trainerId);
            ps.setString(2, course);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Save course error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // APPROVE TRAINER
    // replaces → t.setApproved(true) in AdminService
    // ─────────────────────────────────────────
    public boolean approveTrainer(int trainerId) {

        String sql = "UPDATE trainers SET approved = TRUE WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, trainerId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Trainer Approved!");
                return true;
            } else {
                System.out.println("Trainer not found!");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Approve trainer error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────
    // VIEW ALL TRAINERS
    // replaces → DataStorage.trainers.values()
    // ─────────────────────────────────────────
    public ArrayList<Trainer> getAllTrainers() {

        ArrayList<Trainer> list = new ArrayList<>();
        String sql = "SELECT * FROM trainers";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Trainer t = new Trainer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password")
                );
                t.setApproved(rs.getBoolean("approved"));

                // load this trainer's courses too
                loadCourses(t);

                list.add(t);
            }

        } catch (Exception e) {
            System.out.println("Get trainers error: " + e.getMessage());
        }

        return list;
    }

    // loads courses from trainer_courses table into Trainer object
    private void loadCourses(Trainer t) {

        String sql = "SELECT course FROM trainer_courses WHERE trainer_id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, t.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                t.addCourse(rs.getString("course"));
            }

        } catch (Exception e) {
            System.out.println("Load courses error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────
    // VIEW ALL PARTICIPANTS
    // replaces → DataStorage.participants list
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
    // HELPER - check if trainer ID already exists
    // ─────────────────────────────────────────
    private boolean trainerExists(int id) {

        String sql = "SELECT id FROM trainers WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if found

        } catch (Exception e) {
            System.out.println("Trainer exists check error: " + e.getMessage());
            return false;
        }
    }
}

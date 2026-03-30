package com.trainingfeedback.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.trainingfeedback.model.Participant;

public class ParticipantDAO {

    private Connection con;

    public ParticipantDAO() {
        this.con = DBConnection.getConnection();
        if (this.con == null) {
            System.out.println("ERROR: ParticipantDAO could not get DB connection!");
        }
    }

    // ─────────────────────────────────────────
    // REGISTER PARTICIPANT
    // replaces → DataStorage.participants.add(p)
    // ─────────────────────────────────────────
    public boolean register(Participant p) {

        String sql = "INSERT INTO participants (id, name, password, email, dept, college, course) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            // check ID already exists
            if (participantExists(p.getId())) {
                System.out.println("Error: Participant ID already exists!");
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getDept());
            ps.setString(6, p.getCollege());
            ps.setString(7, p.getCourse());

            ps.executeUpdate();

            System.out.println("Registration Successful!");
            return true;

        } catch (Exception e) {
            System.out.println("Register participant error: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────
    // FIND PARTICIPANT BY ID
    // replaces → loop search in UserService
    // ─────────────────────────────────────────
    public Participant findById(int id) {

        String sql = "SELECT * FROM participants WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Participant(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("dept"),
                    rs.getString("college"),
                    rs.getString("course")
                );
            }

        } catch (Exception e) {
            System.out.println("Find participant error: " + e.getMessage());
        }

        return null; // not found
    }

    // ─────────────────────────────────────────
    // HELPER - check if participant ID already exists
    // ─────────────────────────────────────────
    private boolean participantExists(int id) {

        String sql = "SELECT id FROM participants WHERE id = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if record found

        } catch (Exception e) {
            System.out.println("Participant exists check error: " + e.getMessage());
            return false;
        }
    }
}

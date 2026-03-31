package com.trainingfeedback.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.trainingfeedback.model.Admin;
import com.trainingfeedback.model.Participant;
import com.trainingfeedback.model.Trainer;

public class UserDAO {

    private Connection con;

    public UserDAO() {
        this.con = DBConnection.getConnection();
        if (this.con == null) {
            System.out.println("ERROR: UserDAO could not get DB connection!");
        }
    }

    // ─────────────────────────────────────────
    // ADMIN LOGIN
    // replaces → DataStorage.admin check in UserService
    // ─────────────────────────────────────────
    public Admin adminLogin(int id, String password) {

        String sql = "SELECT * FROM admin WHERE id = ? AND password = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // login success - return Admin object
                return new Admin(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("Admin login error: " + e.getMessage());
        }

        return null; // login failed
    }

    // ─────────────────────────────────────────
    // TRAINER LOGIN
    // replaces → DataStorage.trainers.get(id) in UserService
    // ─────────────────────────────────────────
    public Trainer trainerLogin(int id, String password) {

        String sql = "SELECT * FROM trainers WHERE id = ? AND password = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Trainer t = new Trainer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password")
                );
                t.setApproved(rs.getBoolean("approved"));
                return t;
            }

        } catch (Exception e) {
            System.out.println("Trainer login error: " + e.getMessage());
        }

        return null; // login failed
    }

    // ─────────────────────────────────────────
    // PARTICIPANT LOGIN
    // replaces → loop in UserService.loginParticipant()
    // ─────────────────────────────────────────
    public Participant participantLogin(int id, String password) {

        String sql = "SELECT * FROM participants WHERE id = ? AND password = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, password);

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
            System.out.println("Participant login error: " + e.getMessage());
        }

        return null; // login failed
    }
}

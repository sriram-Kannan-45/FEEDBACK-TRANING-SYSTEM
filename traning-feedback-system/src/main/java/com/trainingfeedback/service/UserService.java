package com.trainingfeedback.service;

import java.sql.*;
import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.*;

public class UserService {

    Scanner sc = new Scanner(System.in);
    private Connection conn;

    public UserService() {
        this.conn = DBConnection.getConnection();
    }

    // ✅ ADMIN LOGIN
    public void adminLogin() {

        System.out.print("Admin ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        if (id == 1 && pass.equals("admin123")) {
            System.out.println("Admin login successful.");
            new AdminDashboard().menu();
        } else {
            System.out.println("Unauthorized access.");
        }
    }

    // ✅ TRAINER LOGIN
    public void trainerLogin() {

        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        Trainer t = authenticateTrainer(id, pass);

        if (t != null) {
            if (t.isApproved()) {
                System.out.println("Trainer login successful.");
                new TrainerDashboard().menu(t);
            } else {
                System.out.println("Trainer not approved.");
            }
        } else {
            System.out.println("Invalid login.");
        }
    }

    private Trainer authenticateTrainer(int id, String password) {

        String query = "SELECT * FROM Trainer WHERE id=? AND password=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

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
            e.printStackTrace();
        }

        return null;
    }

    // ✅ REGISTER PARTICIPANT
    public void registerParticipant() {

        System.out.print("ID : ");
        int id = sc.nextInt();

        if (participantExists(id)) {
            System.out.println("ID already exists!");
            return;
        }

        System.out.print("Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Email : ");
        String email = sc.next();

        System.out.print("Dept : ");
        String dept = sc.next();

        System.out.print("College : ");
        String college = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        String query = "INSERT INTO Participant VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, email);
            ps.setString(5, dept);
            ps.setString(6, college);
            ps.setString(7, course);

            ps.executeUpdate();
            System.out.println("Registration successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean participantExists(int id) {
        String query = "SELECT id FROM Participant WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ LOGIN PARTICIPANT
    public void loginParticipant() {

        System.out.print("ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        Participant p = authenticateParticipant(id, pass);

        if (p != null) {
            System.out.println("Login successful. Welcome " + p.getName());
            new ParticipantDashboard().menu(p);
        } else {
            System.out.println("Invalid login.");
        }
    }

    private Participant authenticateParticipant(int id, String password) {

        String query = "SELECT * FROM Participant WHERE id=? AND password=?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

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
            e.printStackTrace();
        }

        return null;
    }
}
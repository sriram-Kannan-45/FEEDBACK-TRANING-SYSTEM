package com.trainingfeedback.service;

<<<<<<< HEAD
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
import java.sql.*;
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
import java.util.Scanner;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.*;

public class UserService {

    Scanner sc = new Scanner(System.in);
    private Connection conn;

    public UserService() {
        this.conn = DBConnection.getConnection();
    }

<<<<<<< HEAD
    public void adminLogin() {
<<<<<<< HEAD

        System.out.print("Admin ID : ");
=======
        System.out.print("Admin ID   : ");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
=======
    // ✅ ADMIN LOGIN
    public void adminLogin() {

        System.out.print("Admin ID : ");
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

<<<<<<< HEAD
<<<<<<< HEAD
        if (DataStorage.admin.getId() == id &&
            DataStorage.admin.getPassword().equals(pass)) {

=======
        if (id == 1 && pass.equals("admin123")) {
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
=======
        if (id == 1 && pass.equals("admin123")) {
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            System.out.println("Admin login successful.");
            new AdminDashboard().menu();
        } else {
            System.out.println("Unauthorized access.");
        }
    }

<<<<<<< HEAD
    public void trainerLogin() {
=======
    // ✅ TRAINER LOGIN
    public void trainerLogin() {

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        System.out.print("Trainer ID : ");
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

        Trainer t = authenticateTrainer(id, pass);

<<<<<<< HEAD
<<<<<<< HEAD
        if (t != null && t.getPassword().equals(pass)) {

=======
        if (t != null) {
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
=======
        if (t != null) {
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            if (t.isApproved()) {
                System.out.println("Trainer login successful.");
                new TrainerDashboard().menu(t);
            } else {
                System.out.println("Trainer not approved.");
            }
<<<<<<< HEAD

=======
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        } else {
            System.out.println("Invalid login.");
        }
    }

    private Trainer authenticateTrainer(int id, String password) {
<<<<<<< HEAD
        String query = "SELECT * FROM Trainer WHERE id = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

<<<<<<< HEAD
        System.out.print("ID : ");
        int id = sc.nextInt();

        // check duplicate ID
        boolean exists = DataStorage.participants.stream()
                .anyMatch(p -> p.getId() == id);

        if (exists) {
            System.out.println("ID already exists!");
=======
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

    public void registerParticipant() {
        System.out.print("ID       : ");
        int id = sc.nextInt();

        if (participantExists(id)) {
            System.out.println("Error: Participant ID already exists!");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
=======

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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            return;
        }

        System.out.print("Name : ");
        String name = sc.next();

        System.out.print("Password : ");
        String pass = sc.next();

        System.out.print("Email : ");
        String email = sc.next();

<<<<<<< HEAD
        System.out.print("Course : ");
        String course = sc.next();

        String query = "INSERT INTO Participant (id, name, password, email, course) VALUES (?, ?, ?, ?, ?)";

<<<<<<< HEAD
        System.out.println("Registration successful!");
=======
        try (PreparedStatement ps = conn.prepareStatement(query)) {
=======
        System.out.print("Dept : ");
        String dept = sc.next();

        System.out.print("College : ");
        String college = sc.next();

        System.out.print("Course : ");
        String course = sc.next();

        String query = "INSERT INTO Participant VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, email);
<<<<<<< HEAD
            ps.setString(5, course);
            ps.executeUpdate();
            System.out.println("Registration successful! You can now login.");
        } catch (SQLException e) {
            System.out.println("Error registering participant!");
            e.printStackTrace();
        }
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
    }

    private boolean participantExists(int id) {
        String query = "SELECT id FROM Participant WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

<<<<<<< HEAD
        System.out.print("ID : ");
=======
    public void loginParticipant() {
        System.out.print("ID       : ");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
        int id = sc.nextInt();

        System.out.print("Password : ");
        String pass = sc.next();

<<<<<<< HEAD
<<<<<<< HEAD
        for (Participant p : DataStorage.participants) {

            if (p.getId() == id && p.getPassword().equals(pass)) {

                System.out.println("Login successful. Welcome " + p.getName());
=======
        Participant p = authenticateParticipant(id, pass);

        if (p != null) {
            System.out.println("Login successful. Welcome, " + p.getName() + "!");
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3

            if (hasPendingFeedback(p.getId())) {
                System.out.println("\n[REMINDER] You have sessions pending feedback!");
            }

<<<<<<< HEAD
        System.out.println("Invalid login.");
=======
            new ParticipantDashboard().menu(p);
        } else {
            System.out.println("Unauthorized access.");
        }
>>>>>>> 79badbca5daa2639c5893a033aff602f090d41f3
    }

    private Participant authenticateParticipant(int id, String password) {
        String query = "SELECT * FROM Participant WHERE id = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, password);
=======
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

>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Participant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
<<<<<<< HEAD
                        rs.getString("course")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasPendingFeedback(int participantId) {
        String query = "SELECT COUNT(*) FROM SessionRegistration sr " +
                      "WHERE sr.participant_id = ? " +
                      "AND NOT EXISTS (SELECT 1 FROM Feedback f WHERE f.participant_id = sr.participant_id AND f.session_id = sr.session_id)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, participantId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
=======
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
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

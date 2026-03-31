package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import com.trainingfeedback.model.*;
import com.trainingfeedback.controller.*;
import com.trainingfeedback.util.InputUtil;

public class UserService {

    private Scanner sc;
    private Connection conn;
    private boolean fileMode;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UserService() {
        this.sc = new Scanner(System.in);
        this.conn = DBConnection.getConnection();
        this.fileMode = InputUtil.isFileMode();
    }

    public void adminLogin() {
        int id = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Admin ID   : ");
            if (fileMode) {
                id = InputUtil.nextInt();
                if (id > 0) {
                    validId = true;
                } else {
                    System.out.println("Error: ID must be a positive number.");
                }
            } else {
                try {
                    id = sc.nextInt();
                    if (id <= 0) {
                        System.out.println("Error: ID must be a positive number.");
                    } else {
                        validId = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }
        }

        if (!fileMode) sc.nextLine();
        String pass = "";
        boolean validPass = false;
        while (!validPass) {
            System.out.print("Password   : ");
            if (fileMode) {
                pass = InputUtil.nextLine();
            } else {
                pass = sc.nextLine();
            }
            if (pass.isEmpty()) {
                System.out.println("Error: Password cannot be empty.");
            } else {
                validPass = true;
            }
        }

        if (id == 1 && pass.equals("admin123")) {
            System.out.println("Admin login successful.");
            new AdminDashboard().menu();
        } else {
            System.out.println("Error: Invalid credentials.");
        }
    }

    public void trainerLogin() {
        int id = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("Trainer ID : ");
            if (fileMode) {
                id = InputUtil.nextInt();
                if (id > 0) {
                    validId = true;
                } else {
                    System.out.println("Error: ID must be a positive number.");
                }
            } else {
                try {
                    id = sc.nextInt();
                    if (id <= 0) {
                        System.out.println("Error: ID must be a positive number.");
                    } else {
                        validId = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }
        }

        if (!fileMode) sc.nextLine();
        String pass = "";
        boolean validPass = false;
        while (!validPass) {
            System.out.print("Password   : ");
            if (fileMode) {
                pass = InputUtil.nextLine();
            } else {
                pass = sc.nextLine();
            }
            if (pass.isEmpty()) {
                System.out.println("Error: Password cannot be empty.");
            } else {
                validPass = true;
            }
        }

        Trainer t = authenticateTrainer(id, pass);

        if (t != null) {
            if (t.isApproved()) {
                System.out.println("Trainer login successful.");
                new TrainerDashboard().menu(t);
            } else {
                System.out.println("Error: Your account is not yet approved by the admin.");
            }
        } else {
            System.out.println("Error: Invalid credentials.");
        }
    }

    private Trainer authenticateTrainer(int id, String password) {
        String query = "SELECT * FROM Trainer WHERE id = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

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
            System.out.println("Error: Database error during authentication.");
        }
        return null;
    }

    public void registerParticipant() {
        int id = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("ID       : ");
            if (fileMode) {
                id = InputUtil.nextInt();
                if (id <= 0) {
                    System.out.println("Error: ID must be a positive number.");
                } else if (participantExists(id)) {
                    System.out.println("Error: Participant ID already exists!");
                    return;
                } else {
                    validId = true;
                }
            } else {
                try {
                    id = sc.nextInt();
                    if (id <= 0) {
                        System.out.println("Error: ID must be a positive number.");
                    } else if (participantExists(id)) {
                        System.out.println("Error: Participant ID already exists!");
                        return;
                    } else {
                        validId = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }
        }

        if (!fileMode) sc.nextLine();
        String name = "";
        boolean validName = false;
        while (!validName) {
            System.out.print("Name     : ");
            if (fileMode) {
                name = InputUtil.nextLine().trim();
            } else {
                name = sc.nextLine().trim();
            }
            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty.");
            } else {
                validName = true;
            }
        }

        String pass = "";
        boolean validPass = false;
        while (!validPass) {
            System.out.print("Password : ");
            if (fileMode) {
                pass = InputUtil.nextLine();
            } else {
                pass = sc.nextLine();
            }
            if (pass.length() < 6) {
                System.out.println("Error: Password must be at least 6 characters.");
            } else {
                validPass = true;
            }
        }

        String email = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.print("Email    : ");
            if (fileMode) {
                email = InputUtil.nextLine().trim();
            } else {
                email = sc.nextLine().trim();
            }
            if (email.isEmpty()) {
                System.out.println("Error: Email cannot be empty.");
            } else if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Error: Invalid email format.");
            } else {
                validEmail = true;
            }
        }

        String course = "";
        boolean validCourse = false;
        while (!validCourse) {
            System.out.print("Course   : ");
            if (fileMode) {
                course = InputUtil.nextLine().trim();
            } else {
                course = sc.nextLine().trim();
            }
            if (course.isEmpty()) {
                System.out.println("Error: Course cannot be empty.");
            } else {
                validCourse = true;
            }
        }

        String query = "INSERT INTO Participant (id, name, password, email, course) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, pass);
            ps.setString(4, email);
            ps.setString(5, course);
            ps.executeUpdate();
            System.out.println("Registration successful! You can now login.");
        } catch (SQLException e) {
            System.out.println("Error: Unable to register participant.");
        }
    }

    private boolean participantExists(int id) {
        String query = "SELECT id FROM Participant WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: Database error.");
            return false;
        }
    }

    public void loginParticipant() {
        int id = 0;
        boolean validId = false;
        
        while (!validId) {
            System.out.print("ID       : ");
            if (fileMode) {
                id = InputUtil.nextInt();
                if (id > 0) {
                    validId = true;
                } else {
                    System.out.println("Error: ID must be a positive number.");
                }
            } else {
                try {
                    id = sc.nextInt();
                    if (id <= 0) {
                        System.out.println("Error: ID must be a positive number.");
                    } else {
                        validId = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }
        }

        if (!fileMode) sc.nextLine();
        String pass = "";
        boolean validPass = false;
        while (!validPass) {
            System.out.print("Password : ");
            if (fileMode) {
                pass = InputUtil.nextLine();
            } else {
                pass = sc.nextLine();
            }
            if (pass.isEmpty()) {
                System.out.println("Error: Password cannot be empty.");
            } else {
                validPass = true;
            }
        }

        Participant p = authenticateParticipant(id, pass);

        if (p != null) {
            System.out.println("Login successful. Welcome, " + p.getName() + "!");

            if (hasPendingFeedback(p.getId())) {
                System.out.println("\n[REMINDER] You have sessions pending feedback!");
            }

            new ParticipantDashboard().menu(p);
        } else {
            System.out.println("Error: Invalid credentials.");
        }
    }

    private Participant authenticateParticipant(int id, String password) {
        String query = "SELECT * FROM Participant WHERE id = ? AND password = ?";
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
                        rs.getString("course")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: Database error during authentication.");
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
            System.out.println("Error: Database error.");
        }
        return false;
    }
}
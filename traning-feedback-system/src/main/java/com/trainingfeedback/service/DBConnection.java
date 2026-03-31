package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/feedback";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    
    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connection.setAutoCommit(true);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
        return connection;
    }
    
    public static void initializeDatabase() {
        System.out.println("Initializing database...");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = connection.createStatement();
            
            // Create Trainer table
            String createTrainerTable = 
                "CREATE TABLE IF NOT EXISTS Trainer (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "password VARCHAR(100), " +
                "approved BOOLEAN DEFAULT FALSE, " +
                "course VARCHAR(100)" +
                ")";
            stmt.execute(createTrainerTable);
            
            // Create Participant table
            String createParticipantTable = 
                "CREATE TABLE IF NOT EXISTS Participant (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "password VARCHAR(100), " +
                "email VARCHAR(100), " +
                "course VARCHAR(100)" +
                ")";
            stmt.execute(createParticipantTable);
            
            // Create TrainingSession table
            String createSessionTable = 
                "CREATE TABLE IF NOT EXISTS TrainingSession (" +
                "session_id INT PRIMARY KEY, " +
                "title VARCHAR(100), " +
                "start_date VARCHAR(50), " +
                "end_date VARCHAR(50), " +
                "time VARCHAR(50), " +
                "duration INT, " +
                "trainer_id INT, " +
                "FOREIGN KEY (trainer_id) REFERENCES Trainer(id)" +
                ")";
            stmt.execute(createSessionTable);
            
            // Check if end_date column exists, add if not
            try {
                stmt.execute("ALTER TABLE TrainingSession ADD COLUMN end_date VARCHAR(50)");
            } catch (SQLException e) {}
            try {
                stmt.execute("ALTER TABLE TrainingSession ADD COLUMN time VARCHAR(50)");
            } catch (SQLException e) {}
            
            // Create Feedback table
            String createFeedbackTable = 
                "CREATE TABLE IF NOT EXISTS Feedback (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "participant_id INT, " +
                "session_id INT, " +
                "rating INT, " +
                "comment TEXT, " +
                "instructor_rating INT, " +
                "instructor_comment TEXT, " +
                "is_anonymous BOOLEAN DEFAULT FALSE, " +
                "UNIQUE KEY unique_feedback (participant_id, session_id), " +
                "FOREIGN KEY (participant_id) REFERENCES Participant(id), " +
                "FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id)" +
                ")";
            stmt.execute(createFeedbackTable);
            
            // Add is_anonymous column if it doesn't exist (for existing databases)
            try {
                stmt.execute("ALTER TABLE Feedback ADD COLUMN is_anonymous BOOLEAN DEFAULT FALSE");
            } catch (SQLException e) {}
            
            // Create SessionRegistration table
            String createRegistrationTable = 
                "CREATE TABLE IF NOT EXISTS SessionRegistration (" +
                "participant_id INT, " +
                "session_id INT, " +
                "PRIMARY KEY (participant_id, session_id), " +
                "FOREIGN KEY (participant_id) REFERENCES Participant(id), " +
                "FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id)" +
                ")";
            stmt.execute(createRegistrationTable);
            
            stmt.close();
            System.out.println("Database initialized successfully!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error initializing database!");
            e.printStackTrace();
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

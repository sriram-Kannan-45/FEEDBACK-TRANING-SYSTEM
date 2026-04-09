/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
Class: DBConnection
Module: JDBC/Database
By: Sriram K

Purpose: Manages MySQL database connection
JDBC: Connection, DriverManager, Statement
OOPS: Encapsulation - private static connection, Singleton pattern
*/
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
            
            // Create Survey table
            String createSurveyTable = 
                "CREATE TABLE IF NOT EXISTS Survey (" +
                "id INT PRIMARY KEY, " +
                "title VARCHAR(200), " +
                "description TEXT, " +
                "active BOOLEAN DEFAULT TRUE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.execute(createSurveyTable);
            
            // Create SurveyQuestion table
            String createSurveyQuestionTable = 
                "CREATE TABLE IF NOT EXISTS SurveyQuestion (" +
                "survey_id INT, " +
                "question_id INT, " +
                "question_text TEXT, " +
                "question_type VARCHAR(50), " +
                "options TEXT, " +
                "required BOOLEAN DEFAULT TRUE, " +
                "PRIMARY KEY (survey_id, question_id), " +
                "FOREIGN KEY (survey_id) REFERENCES Survey(id) ON DELETE CASCADE" +
                ")";
            stmt.execute(createSurveyQuestionTable);
            
            // Create SurveyResponse table
            String createSurveyResponseTable = 
                "CREATE TABLE IF NOT EXISTS SurveyResponse (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "survey_id INT, " +
                "participant_id INT, " +
                "answer_text TEXT, " +
                "submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (survey_id) REFERENCES Survey(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (participant_id) REFERENCES Participant(id)" +
                ")";
            stmt.execute(createSurveyResponseTable);
            
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

    public static void clearAllData() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("TRUNCATE TABLE SessionRegistration");
            stmt.execute("TRUNCATE TABLE Feedback");
            stmt.execute("TRUNCATE TABLE TrainingSession");
            stmt.execute("TRUNCATE TABLE Participant");
            stmt.execute("TRUNCATE TABLE Trainer");
            stmt.execute("TRUNCATE TABLE Survey");
            stmt.execute("TRUNCATE TABLE SurveyQuestion");
            stmt.execute("TRUNCATE TABLE SurveyResponse");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            stmt.close();
            System.out.println("All data cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Error clearing data.");
            e.printStackTrace();
        }
    }
}

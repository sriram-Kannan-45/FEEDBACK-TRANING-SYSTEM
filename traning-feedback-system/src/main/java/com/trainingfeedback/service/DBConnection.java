package com.trainingfeedback.service;
<<<<<<< HEAD

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
                "UNIQUE KEY unique_feedback (participant_id, session_id), " +
                "FOREIGN KEY (participant_id) REFERENCES Participant(id), " +
                "FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id)" +
                ")";
            stmt.execute(createFeedbackTable);
            
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
=======
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class DBConnection {

	    private static Connection conn = null;

	    private static final String URL      = "jdbc:mysql://localhost:3306/trainingfeedback";
	    private static final String USERNAME = "root";
	    private static final String PASSWORD = "1234"; 

	    public static Connection getConnection() {
	        if (conn == null) {
	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	                System.out.println("DB Connected Successfully!");
	            } catch (ClassNotFoundException e) {
	                System.out.println("MySQL Driver not found! Check pom.xml dependency.");
	                e.printStackTrace();
	            } catch (SQLException e) {
	                System.out.println("DB Connection Failed! Check URL, username, password.");
	                e.printStackTrace();
	            }
	        }
	        return conn;
	    }
	}


>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

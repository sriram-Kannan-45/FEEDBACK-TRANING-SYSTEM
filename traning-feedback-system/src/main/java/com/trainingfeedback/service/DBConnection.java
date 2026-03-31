package com.trainingfeedback.service;
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



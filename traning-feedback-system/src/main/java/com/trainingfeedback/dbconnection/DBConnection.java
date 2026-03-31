package com.trainingfeedback.dbconnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    // single shared connection (Singleton pattern)
    private static Connection connection = null;

    public static Connection getConnection() {

        if (connection != null) return connection;

        try {
            // Step 1: manually load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: load db.properties from src/main/resources/
            Properties props = new Properties();
            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                System.out.println("ERROR: db.properties file not found in src/main/resources/");
                return null;
            }

            props.load(input);

            String url      = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            // Step 3: make the connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("DB Connected Successfully!");

        } catch (ClassNotFoundException e) {
            // this means mysql-connector-java is missing from pom.xml
            System.out.println("ERROR: MySQL Driver not found! Add mysql-connector-java to pom.xml");
        } catch (Exception e) {
            System.out.println("DB Connection Failed: " + e.getMessage());
        }

        return connection;
    }

    // call this when app exits
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("DB Connection Closed.");
            }
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}

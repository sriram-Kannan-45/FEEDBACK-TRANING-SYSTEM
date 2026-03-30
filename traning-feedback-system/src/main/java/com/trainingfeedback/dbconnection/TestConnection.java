package com.trainingfeedback.dbconnection;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        System.out.println("Testing DB Connection...");

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("✅ Connection SUCCESS! JDBC is working.");
        } else {
            System.out.println("❌ Connection FAILED! Check the errors above.");
        }

        DBConnection.closeConnection();
    }
}

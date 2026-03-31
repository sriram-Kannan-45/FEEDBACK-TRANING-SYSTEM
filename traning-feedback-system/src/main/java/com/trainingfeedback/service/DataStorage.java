package com.trainingfeedback.service;

import com.trainingfeedback.model.*;

public class DataStorage {

    public static Admin admin = new Admin(1, "admin", "admin123");

    public static void notifyAdmin(String message) {
        System.out.println("[Admin Notification] " + message);
    }
}

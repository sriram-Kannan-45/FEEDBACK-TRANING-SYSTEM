package com.trainingfeedback.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.trainingfeedback.model.*;

public class DataStorage {

    // admin account
    public static Admin admin = new Admin(1, "admin", "admin123");

    // Trainers keyed by ID
    public static Map<Integer, Trainer> trainers = new HashMap<>();

    // Sessions keyed by session ID
    public static Map<Integer, TrainingSession> sessions = new HashMap<>();

    // All registered participants
    public static ArrayList<Participant> participants = new ArrayList<>();

    // Admin notification log for new feedback submissions
    public static ArrayList<String> adminNotifications = new ArrayList<>();

    //Admin notification
    public static void notifyAdmin(String message) {
        adminNotifications.add(message);
    }
}
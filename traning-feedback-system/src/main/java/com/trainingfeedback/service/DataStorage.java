package com.trainingfeedback.service;

import com.trainingfeedback.model.*;

public class DataStorage {

<<<<<<< HEAD
    public static Admin admin = new Admin(1, "admin", "admin123");

    public static void notifyAdmin(String message) {
        System.out.println("[Admin Notification] " + message);
    }
}
=======
    
    public static Admin admin = new Admin(1, "admin", "admin123");

    public static void notifyAdmin(String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        System.out.println("[Admin Notification] " + message);
    }
}
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

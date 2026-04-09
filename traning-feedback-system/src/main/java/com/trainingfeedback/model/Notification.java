/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.model;

import java.util.ArrayList;
import java.util.List;

/*
Class: Notification
Module: Model

Purpose: Stores notification data for reminders
OOPS: Encapsulation - private fields + getters/setters
*/
public class Notification {

    private int notificationId;
    private String recipientRole;
    private int recipientId;
    private String message;
    private String type;
    private boolean read;
    private String createdAt;

    public Notification() {
        this.read = false;
    }

    public Notification(int notificationId, String recipientRole, int recipientId, 
                        String message, String type) {
        this.notificationId = notificationId;
        this.recipientRole = recipientRole;
        this.recipientId = recipientId;
        this.message = message;
        this.type = type;
        this.read = false;
    }

    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public String getRecipientRole() { return recipientRole; }
    public void setRecipientRole(String recipientRole) { this.recipientRole = recipientRole; }

    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "[" + type + "] " + message;
    }

    public static class NotificationManager {
        private static List<Notification> notifications = new ArrayList<>();
        private static int nextId = 1;

        public static void addNotification(String recipientRole, int recipientId, 
                                          String message, String type) {
            notifications.add(new Notification(nextId++, recipientRole, recipientId, message, type));
            System.out.println("[NOTIFICATION] " + type + ": " + message);
        }

        public static void addNotification(String recipientRole, String message, String type) {
            notifications.add(new Notification(nextId++, recipientRole, 0, message, type));
            System.out.println("[NOTIFICATION] " + type + ": " + message);
        }

        public static List<Notification> getNotificationsForUser(String role, int userId) {
            List<Notification> userNotifications = new ArrayList<>();
            for (Notification n : notifications) {
                if (n.getRecipientRole().equalsIgnoreCase(role) && 
                    (n.getRecipientId() == userId || n.getRecipientId() == 0)) {
                    userNotifications.add(n);
                }
            }
            return userNotifications;
        }

        public static List<Notification> getNotificationsForRole(String role) {
            List<Notification> roleNotifications = new ArrayList<>();
            for (Notification n : notifications) {
                if (n.getRecipientRole().equalsIgnoreCase(role)) {
                    roleNotifications.add(n);
                }
            }
            return roleNotifications;
        }

        public static List<Notification> getAdminNotifications() {
            return getNotificationsForRole("ADMIN");
        }

        public static int getUnreadCount(String role, int userId) {
            int count = 0;
            for (Notification n : getNotificationsForUser(role, userId)) {
                if (!n.isRead()) count++;
            }
            return count;
        }

        public static void markAsRead(int notificationId) {
            for (Notification n : notifications) {
                if (n.getNotificationId() == notificationId) {
                    n.setRead(true);
                    break;
                }
            }
        }

        public static void markAllAsRead(String role, int userId) {
            for (Notification n : getNotificationsForUser(role, userId)) {
                n.setRead(true);
            }
        }

        public static void clearNotifications(String role, int userId) {
            notifications.removeIf(n -> n.getRecipientRole().equalsIgnoreCase(role) && 
                                          n.getRecipientId() == userId);
        }

        public static List<Notification> getAllNotifications() {
            return new ArrayList<>(notifications);
        }

        public static void sendFeedbackReminder(int participantId, String participantName, 
                                               int sessionId, String sessionTitle) {
            addNotification("PARTICIPANT", participantId,
                "Reminder: Please submit your feedback for session '" + sessionTitle + "' (ID: " + sessionId + ")",
                "REMINDER");
        }

        public static void sendFeedbackSubmittedNotification(int sessionId, String sessionTitle) {
            addNotification("ADMIN",
                "New feedback submitted for session '" + sessionTitle + "' (ID: " + sessionId + ")",
                "FEEDBACK");
        }

        public static void sendTrainerApprovalNotification(int trainerId, String trainerName) {
            addNotification("TRAINER", trainerId,
                "Congratulations " + trainerName + "! Your account has been approved.",
                "APPROVAL");
        }

        public static void sendRegistrationNotification(int participantId, String participantName, 
                                                        int sessionId, String sessionTitle) {
            addNotification("PARTICIPANT", participantId,
                "You have been registered for session '" + sessionTitle + "' (ID: " + sessionId + ")",
                "REGISTRATION");
            addNotification("ADMIN",
                "Participant " + participantName + " registered for session '" + sessionTitle + "'",
                "REGISTRATION");
        }
    }
}

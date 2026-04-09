package com.trainingfeedback.util;

public class TableFormatter {

    public static void printTrainerTableHeader() {
        System.out.println("+-----+---------------+-------------------------+---------------+-----------+");
        System.out.printf("| %-3s | %-13s | %-23s | %-13s | %-9s |%n", 
            "ID", "Name", "Email", "Course", "Approved");
        System.out.println("+-----+---------------+-------------------------+---------------+-----------+");
    }

    public static void printTrainerRow(int id, String name, String email, String course, boolean approved) {
        String approvedStr = approved ? "Yes" : "No";
        System.out.printf("| %-3d | %-13s | %-23s | %-13s | %-9s |%n", 
            id, truncate(name, 13), truncate(email, 23), truncate(course, 13), approvedStr);
    }

    public static void printTrainerTableEnd() {
        System.out.println("+-----+---------------+-------------------------+---------------+-----------+");
    }

    public static void printParticipantTableHeader() {
        System.out.println("+-----+---------------+-------------------------+---------------+");
        System.out.printf("| %-3s | %-13s | %-23s | %-13s |%n", 
            "ID", "Name", "Email", "Course");
        System.out.println("+-----+---------------+-------------------------+---------------+");
    }

    public static void printParticipantRow(int id, String name, String email, String course) {
        System.out.printf("| %-3d | %-13s | %-23s | %-13s |%n", 
            id, truncate(name, 13), truncate(email, 23), truncate(course, 13));
    }

    public static void printParticipantTableEnd() {
        System.out.println("+-----+---------------+-------------------------+---------------+");
    }

    public static void printSessionTableHeader() {
        System.out.println("+------------+--------------+------------+------------+----------+-------------+");
        System.out.printf("| %-10s | %-12s | %-10s | %-10s | %-8s | %-11s |%n", 
            "Session ID", "Title", "Start Date", "End Date", "Duration", "Trainer");
        System.out.println("+------------+--------------+------------+------------+----------+-------------+");
    }

    public static void printSessionRow(int sessionId, String title, String startDate, String endDate, 
                                        int duration, String trainer) {
        String trainerStr = (trainer != null && !trainer.isEmpty()) ? trainer : "Not Assigned";
        System.out.printf("| %-10d | %-12s | %-10s | %-10s | %-8d | %-11s |%n", 
            sessionId, truncate(title, 12), startDate, endDate, duration, truncate(trainerStr, 11));
    }

    public static void printSessionTableEnd() {
        System.out.println("+------------+--------------+------------+------------+----------+-------------+");
    }

    public static void printFeedbackTableHeader() {
        System.out.println("+---------+-------------+--------+----------------------+");
        System.out.printf("| %-7s | %-11s | %-6s | %-20s |%n", 
            "Session", "Participant", "Rating", "Comment");
        System.out.println("+---------+-------------+--------+----------------------+");
    }

    public static void printFeedbackRow(String session, String participant, int rating, String comment) {
        System.out.printf("| %-7s | %-11s | %-6d | %-20s |%n", 
            truncate(session, 7), truncate(participant, 11), rating, truncate(comment, 20));
    }

    public static void printFeedbackTableEnd() {
        System.out.println("+---------+-------------+--------+----------------------+");
    }

    public static void printAnalyticsHeader(String title) {
        System.out.println("\n============================================================");
        System.out.println(title);
        System.out.println("============================================================");
    }

    public static void printSectionHeader(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void printReportHeader(String sessionName, String trainerName) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        TRAINING FEEDBACK REPORT");
        System.out.println("========================================");
        System.out.println();
        System.out.printf("%-20s: %s%n", "Session Name", sessionName);
        System.out.printf("%-20s: %s%n", "Trainer Name", trainerName != null ? trainerName : "Not Assigned");
        System.out.println();
    }

    public static void printFeedbackSummary(int totalFeedbacks, double averageRating) {
        printSeparatorLine(40);
        System.out.println("Feedback Summary");
        printSeparatorLine(40);
        System.out.printf("%-20s: %d%n", "Total Feedbacks", totalFeedbacks);
        System.out.printf("%-20s: %.2f / 5.00%n", "Average Rating", averageRating);
        System.out.println();
    }

    public static void printRatingDistribution(int[] dist) {
        printSeparatorLine(40);
        System.out.println("Rating Distribution");
        printSeparatorLine(40);
        System.out.printf("%-20s: %d%n", "5 Star", dist[5]);
        System.out.printf("%-20s: %d%n", "4 Star", dist[4]);
        System.out.printf("%-20s: %d%n", "3 Star", dist[3]);
        System.out.printf("%-20s: %d%n", "2 Star", dist[2]);
        System.out.printf("%-20s: %d%n", "1 Star", dist[1]);
        System.out.println();
    }

    public static void printReportEnd() {
        System.out.println("========================================");
    }

    public static void printSeparatorLine(int length) {
        System.out.println("-".repeat(length));
    }

    public static void printTrainerPerformanceReport(String trainerName, int totalFeedbacks, 
                                                       double avgRating, int[] dist) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       TRAINER PERFORMANCE REPORT");
        System.out.println("========================================");
        System.out.println();
        System.out.printf("%-20s: %s%n", "Trainer Name", trainerName);
        System.out.println();
        printSeparatorLine(40);
        System.out.println("Performance Summary");
        printSeparatorLine(40);
        System.out.printf("%-20s: %d%n", "Total Feedbacks", totalFeedbacks);
        System.out.printf("%-20s: %.2f / 5.00%n", "Average Rating", avgRating);
        System.out.println();
        printSeparatorLine(40);
        System.out.println("Rating Distribution");
        printSeparatorLine(40);
        System.out.printf("%-20s: %d%n", "5 Star", dist[5]);
        System.out.printf("%-20s: %d%n", "4 Star", dist[4]);
        System.out.printf("%-20s: %d%n", "3 Star", dist[3]);
        System.out.printf("%-20s: %d%n", "2 Star", dist[2]);
        System.out.printf("%-20s: %d%n", "1 Star", dist[1]);
        System.out.println("========================================");
    }

    public static void printSessionAnalyticsReport(String sessionTitle, String trainerName,
                                                     int totalFeedbacks, double avgRating, int[] dist) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       SESSION FEEDBACK ANALYTICS");
        System.out.println("========================================");
        System.out.println();
        System.out.printf("%-20s: %s%n", "Session Name", sessionTitle);
        System.out.printf("%-20s: %s%n", "Trainer Name", trainerName != null ? trainerName : "Not Assigned");
        System.out.println();
        printSeparatorLine(40);
        System.out.println("Feedback Summary");
        printSeparatorLine(40);
        System.out.printf("%-20s: %d%n", "Total Feedbacks", totalFeedbacks);
        System.out.printf("%-20s: %.2f / 5.00%n", "Average Rating", avgRating);
        System.out.println();
        printSeparatorLine(40);
        System.out.println("Rating Distribution");
        printSeparatorLine(40);
        System.out.printf("%-20s: %d%n", "5 Star", dist[5]);
        System.out.printf("%-20s: %d%n", "4 Star", dist[4]);
        System.out.printf("%-20s: %d%n", "3 Star", dist[3]);
        System.out.printf("%-20s: %d%n", "2 Star", dist[2]);
        System.out.printf("%-20s: %d%n", "1 Star", dist[1]);
        System.out.println("========================================");
    }

    private static String truncate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
package com.trainingfeedback.util;

import java.util.regex.Pattern;
import com.trainingfeedback.exception.InvalidInputException;

public class ValidationUtil {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{1,2}/\\d{1,2}/\\d{4}$");
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]?\\d|2[0-3]):[0-5]\\d$");

    private ValidationUtil() {
    }

    public static void validateId(int id) {
        if (id <= 0) {
            throw new InvalidInputException("ID must be greater than 0");
        }
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            throw new InvalidInputException("Name must contain only alphabets and spaces");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidInputException("Invalid email format");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidInputException("Password cannot be empty");
        }
        if (password.length() < 6) {
            throw new InvalidInputException("Password must be at least 6 characters");
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        if (!hasLetter || !hasDigit) {
            throw new InvalidInputException("Password must contain both letters and numbers");
        }
    }

    public static void validateCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            throw new InvalidInputException("Course cannot be empty");
        }
    }

    public static void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new InvalidInputException("Rating must be between 1 and 5");
        }
    }

    public static void validateDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidInputException("Date cannot be empty");
        }
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new InvalidInputException("Date must be in format dd/mm/yyyy");
        }
        String[] parts = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        if (month < 1 || month > 12) {
            throw new InvalidInputException("Invalid month. Must be between 01 and 12");
        }
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            daysInMonth[1] = 29;
        }
        if (day < 1 || day > daysInMonth[month - 1]) {
            throw new InvalidInputException("Invalid day " + day + " for month " + month);
        }
    }

    public static void validateTime(String time) {
        if (time == null || time.trim().isEmpty()) {
            throw new InvalidInputException("Time cannot be empty");
        }
        if (!TIME_PATTERN.matcher(time).matches()) {
            throw new InvalidInputException("Time must be in format HH:MM");
        }
    }

    public static void validateDuration(int duration) {
        if (duration <= 0) {
            throw new InvalidInputException("Duration must be a positive number");
        }
    }

    public static void validateComment(String comment) {
        if (comment == null) {
            throw new InvalidInputException("Comment cannot be null");
        }
    }
}
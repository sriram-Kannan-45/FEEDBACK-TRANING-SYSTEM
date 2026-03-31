package com.trainingfeedback.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputUtil {

    private static List<String> inputs = new ArrayList<>();
    private static int currentIndex = 0;
    private static boolean initialized = false;
    private static boolean fileNotFound = false;

    public static void init(String filePath) {
        inputs.clear();
        currentIndex = 0;
        initialized = true;
        fileNotFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    inputs.add(line);
                }
            }
            System.out.println("Input file loaded: " + inputs.size() + " commands");
        } catch (IOException e) {
            fileNotFound = true;
            System.out.println("Note: Input file not found - running in interactive mode");
        }
    }

    public static boolean hasNext() {
        if (fileNotFound) {
            return false;
        }
        return currentIndex < inputs.size();
    }

    public static String next() {
        if (fileNotFound) {
            return null;
        }
        if (currentIndex >= inputs.size()) {
            System.out.println("Warning: No more inputs available");
            return null;
        }
        String input = inputs.get(currentIndex);
        currentIndex++;
        return input;
    }

    public static int nextInt() {
        String s = next();
        if (s == null) {
            return -1;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid integer input: " + s);
            return -1;
        }
    }

    public static String nextLine() {
        String s = next();
        return s != null ? s : "";
    }

    public static double nextDouble() {
        String s = next();
        if (s == null) {
            return -1;
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid double input: " + s);
            return -1;
        }
    }

    public static boolean isFileMode() {
        return !fileNotFound && initialized;
    }

    public static void reset() {
        currentIndex = 0;
    }

    public static int remaining() {
        return Math.max(0, inputs.size() - currentIndex);
    }

    public static void close() {
        inputs.clear();
        currentIndex = 0;
        initialized = false;
        fileNotFound = false;
    }
}
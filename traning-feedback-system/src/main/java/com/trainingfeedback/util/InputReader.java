package com.trainingfeedback.util;

import java.util.InputMismatchException;

public class InputReader {

    private boolean fileMode;

    public InputReader(String inputFile) {
        InputUtil.init(inputFile);
        this.fileMode = InputUtil.isFileMode();
    }

    public int nextInt() {
        if (fileMode) {
            return InputUtil.nextInt();
        }
        return scannerNextInt();
    }

    public String next() {
        if (fileMode) {
            return InputUtil.next();
        }
        return scannerNext();
    }

    public String nextLine() {
        if (fileMode) {
            String line = InputUtil.nextLine();
            return line != null ? line : "";
        }
        return scannerNextLine();
    }

    public double nextDouble() {
        if (fileMode) {
            return InputUtil.nextDouble();
        }
        return scannerNextDouble();
    }

    public boolean hasNext() {
        if (fileMode) {
            return InputUtil.hasNext();
        }
        return scannerHasNext();
    }

    public void close() {
        if (fileMode) {
            InputUtil.close();
        }
    }

    private java.util.Scanner getScanner() {
        return new java.util.Scanner(System.in);
    }

    private int scannerNextInt() {
        java.util.Scanner sc = getScanner();
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a number.");
            sc.nextLine();
            return -1;
        } finally {
            sc.close();
        }
    }

    private String scannerNext() {
        java.util.Scanner sc = getScanner();
        String result = sc.next();
        sc.close();
        return result;
    }

    private String scannerNextLine() {
        java.util.Scanner sc = getScanner();
        String result = sc.nextLine();
        sc.close();
        return result;
    }

    private double scannerNextDouble() {
        java.util.Scanner sc = getScanner();
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a number.");
            sc.nextLine();
            return -1;
        } finally {
            sc.close();
        }
    }

    private boolean scannerHasNext() {
        java.util.Scanner sc = getScanner();
        boolean has = sc.hasNext();
        sc.close();
        return has;
    }
}
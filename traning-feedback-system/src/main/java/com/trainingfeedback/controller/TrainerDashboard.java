package com.trainingfeedback.controller;

import java.util.Scanner;
import com.trainingfeedback.model.Trainer;
import com.trainingfeedback.service.TrainerService;

public class TrainerDashboard {

    Scanner sc = new Scanner(System.in);
    TrainerService service = new TrainerService();

    public void menu(Trainer t) {

        while (true) {

            System.out.println("\n===== Trainer Dashboard =====");
            System.out.println("1. Add Course");
            System.out.println("2. View Courses");
            System.out.println("3. Logout");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("Enter Course Name:");
                    String course = sc.next();

                    service.addCourse(t, course);

                    System.out.println("Course Added Successfully");
                    break;

                case 2:

                    service.viewCourses(t);
                    break;

                case 3:

                    System.out.println("Logout Successful");
                    return;

                default:

                    System.out.println("Invalid Choice");
            }
        }
    }
}
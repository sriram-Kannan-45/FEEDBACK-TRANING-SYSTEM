package com.trainingfeedback.service;

import com.trainingfeedback.model.Trainer;

public class TrainerService {

    public void addCourse(Trainer t, String course) {

        t.addCourse(course);
        System.out.println("Course added successfully");

    }

    public void viewCourses(Trainer t) {

        if (t.getCourses().isEmpty()) {

            System.out.println("No courses assigned");

        } else {

            System.out.println("Courses handled by Trainer:");

            for (String c : t.getCourses()) {
                System.out.println(c);
            }
        }
    }
}
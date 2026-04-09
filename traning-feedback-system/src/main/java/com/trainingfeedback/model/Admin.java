/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.model;

/*
Class: Admin
Module: Admin
By: Mylambikai

Purpose: Admin model extending User
OOPS: Inheritance - extends User, Polymorphism - overrides display()
*/
public class Admin extends User {

    public Admin(int id, String name, String password) {
        super(id, name, password);
    }

    public void display() {
        System.out.println("Admin ID: " + id);
        System.out.println("Admin Name: " + name);
    }
}
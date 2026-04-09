/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.model;

/*
Class: User (Parent Class)
Module: Base Model

Purpose: Base class for Admin, Trainer, Participant
OOPS: Inheritance - Admin/Trainer/Participant extend User, Encapsulation - private fields + getters
*/
public class User {

    protected int id;
    protected String name;
    protected String password;

    public User(int id, String name, String password) {

        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
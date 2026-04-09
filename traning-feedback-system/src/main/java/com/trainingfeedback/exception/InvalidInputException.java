/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.exception;

/*
Class: InvalidInputException
Module: Exception

Purpose: Thrown when user input validation fails
OOPS: Inheritance - extends RuntimeException
*/
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
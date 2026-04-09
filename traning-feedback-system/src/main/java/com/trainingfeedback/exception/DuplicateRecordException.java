/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.exception;

/*
Class: DuplicateRecordException
Module: Exception

Purpose: Thrown when trying to create duplicate record
OOPS: Inheritance - extends RuntimeException
*/
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException(String message) {
        super(message);
    }
}
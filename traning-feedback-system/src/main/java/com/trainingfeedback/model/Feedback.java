/*
Project: Training Feedback System
Type: Console-Based Java Application
CMD: java Main
Team: Admin→Mylambikai, Trainer→Shamiha, Participant→Tamilarasu, JDBC→Sriram K
Flow: Main → Service → DAO → DB
*/
package com.trainingfeedback.model;

/*
Class: Feedback
Module: Model

Purpose: Stores participant feedback for sessions
OOPS: Encapsulation - private fields + getters/setters
*/
public class Feedback {

    private int participantId;
    private int rating;
    private String comment;

    // Constructor
    public Feedback(int participantId, int rating, String comment) {
        this.participantId = participantId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getter methods
    public int getParticipantId1() {
        return participantId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    // Display
    @Override
    public String toString() {
        return "Participant ID: " + participantId +
               ", Rating: " + rating +
               ", Comment: " + comment;
    }

	public Integer getParticipantId() {
		return participantId;
	}
}
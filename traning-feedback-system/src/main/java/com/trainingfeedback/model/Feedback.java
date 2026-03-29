package com.trainingfeedback.model;

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
		// TODO Auto-generated method stub
		return null;
	}
}
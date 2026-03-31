package com.trainingfeedback.model;

public class Feedback {

    private int id;
    private int participantId;
    private int sessionId;
    private int trainingRating;
    private int instructorRating;
    private String trainingComment;
    private String instructorComment;
    private String trainerResponse;
    private boolean anonymous;
    private String submittedAt;

    public Feedback() {}

    public Feedback(int id, int participantId, int sessionId, int trainingRating, 
                    int instructorRating, String trainingComment, String instructorComment, 
                    boolean anonymous) {
        this.id = id;
        this.participantId = participantId;
        this.sessionId = sessionId;
        this.trainingRating = trainingRating;
        this.instructorRating = instructorRating;
        this.trainingComment = trainingComment;
        this.instructorComment = instructorComment;
        this.anonymous = anonymous;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getParticipantId() { return participantId; }
    public void setParticipantId(int participantId) { this.participantId = participantId; }

    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getTrainingRating() { return trainingRating; }
    public void setTrainingRating(int trainingRating) { this.trainingRating = trainingRating; }

    public int getInstructorRating() { return instructorRating; }
    public void setInstructorRating(int instructorRating) { this.instructorRating = instructorRating; }

    public String getTrainingComment() { return trainingComment; }
    public void setTrainingComment(String trainingComment) { this.trainingComment = trainingComment; }

    public String getInstructorComment() { return instructorComment; }
    public void setInstructorComment(String instructorComment) { this.instructorComment = instructorComment; }

    public String getTrainerResponse() { return trainerResponse; }
    public void setTrainerResponse(String trainerResponse) { this.trainerResponse = trainerResponse; }

    public boolean isAnonymous() { return anonymous; }
    public void setAnonymous(boolean anonymous) { this.anonymous = anonymous; }

    public String getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(String submittedAt) { this.submittedAt = submittedAt; }

    public double getOverallRating() {
        return (trainingRating + instructorRating) / 2.0;
    }

    @Override
    public String toString() {
        return "Feedback ID         : " + id + 
               "\nParticipant ID    : " + (anonymous ? "Anonymous" : participantId) +
               "\nSession ID        : " + sessionId +
               "\nTraining Rating   : " + trainingRating + "/5" +
               "\nInstructor Rating : " + instructorRating + "/5" +
               "\nTraining Comment  : " + (trainingComment != null ? trainingComment : "N/A") +
               "\nInstructor Comment: " + (instructorComment != null ? instructorComment : "N/A") +
               "\nTrainer Response  : " + (trainerResponse != null ? trainerResponse : "Pending") +
               "\nAnonymous         : " + (anonymous ? "Yes" : "No");
    }

	public double getRating() {
		// TODO Auto-generated method stub
		return 0;
	}
}

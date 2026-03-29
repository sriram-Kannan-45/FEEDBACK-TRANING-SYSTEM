package com.trainingfeedback.model;

public class Feedback {

    private int participantId;
    private String participantName;
    private int sessionId;
    private String sessionTitle;

    private int rating;           //Submit Training Feedback
    private String comment;

    // Instructor Evaluation
    private int instructorRating;
    private String instructorComment;

    // Constructor for session feedback
    public Feedback(int participantId, String participantName, int sessionId,
                    String sessionTitle, int rating, String comment) {
        this.participantId    = participantId;
        this.participantName  = participantName;
        this.sessionId        = sessionId;
        this.sessionTitle     = sessionTitle;
        this.rating           = rating;
        this.comment          = comment;
        this.instructorRating = 0;
        this.instructorComment = "";
    }

    //set instructor evaluation separately
    public void setInstructorEvaluation(int rating, String comment) {
        this.instructorRating  = rating;
        this.instructorComment = comment;
    }

    public int getParticipantId()      { return participantId; }
    public String getParticipantName() { return participantName; }
    public int getSessionId()          { return sessionId; }
    public String getSessionTitle()    { return sessionTitle; }
    public int getRating()             { return rating; }
    public String getComment()         { return comment; }
    public int getInstructorRating()   { return instructorRating; }
    public String getInstructorComment() { return instructorComment; }
    public boolean hasInstructorEval() { return instructorRating > 0; }

    @Override
    public String toString() {
        return "Rating: " + rating + " | By: " + participantName + " | " + comment;
    }
}
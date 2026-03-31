package com.trainingfeedback.model;

import java.util.ArrayList;
import java.util.List;

public class Survey {

    private int surveyId;
    private String title;
    private String description;
    private String createdAt;
    private boolean active;
    private List<Question> questions;

    public Survey() {
        this.questions = new ArrayList<>();
        this.active = true;
    }

    public Survey(int surveyId, String title, String description) {
        this.surveyId = surveyId;
        this.title = title;
        this.description = description;
        this.questions = new ArrayList<>();
        this.active = true;
    }

    public int getSurveyId() { return surveyId; }
    public void setSurveyId(int surveyId) { this.surveyId = surveyId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public List<Question> getQuestions() { return questions; }

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public void removeQuestion(int questionId) {
        questions.removeIf(q -> q.getQuestionId() == questionId);
    }

    public void displaySurvey() {
        System.out.println("\n========== " + title + " ==========");
        System.out.println("Description: " + description);
        System.out.println("Status: " + (active ? "Active" : "Inactive"));
        System.out.println("Questions: " + questions.size());
        
        if (!questions.isEmpty()) {
            System.out.println("\n--- Survey Questions ---");
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                System.out.println((i + 1) + ". [" + q.getQuestionType() + "] " + q.getQuestionText());
                if (q.getOptions() != null && !q.getOptions().isEmpty()) {
                    System.out.println("   Options: " + String.join(", ", q.getOptions()));
                }
            }
        }
    }

    public static class Question {
        private int questionId;
        private String questionText;
        private String questionType;
        private List<String> options;
        private boolean required;

        public Question() {
            this.options = new ArrayList<>();
            this.required = true;
        }

        public Question(int questionId, String questionText, String questionType) {
            this.questionId = questionId;
            this.questionText = questionText;
            this.questionType = questionType;
            this.options = new ArrayList<>();
            this.required = true;
        }

        public int getQuestionId() { return questionId; }
        public void setQuestionId(int questionId) { this.questionId = questionId; }

        public String getQuestionText() { return questionText; }
        public void setQuestionText(String questionText) { this.questionText = questionText; }

        public String getQuestionType() { return questionType; }
        public void setQuestionType(String questionType) { this.questionType = questionType; }

        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }

        public boolean isRequired() { return required; }
        public void setRequired(boolean required) { this.required = required; }

        public void addOption(String option) {
            this.options.add(option);
        }
    }
}

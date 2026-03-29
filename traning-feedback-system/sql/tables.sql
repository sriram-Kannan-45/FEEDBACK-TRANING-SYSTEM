-- Training Feedback System Database Setup

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS feedback;
USE feedback;

-- Trainer table
CREATE TABLE IF NOT EXISTS Trainer (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    approved BOOLEAN DEFAULT FALSE,
    course VARCHAR(100)
);

-- Participant table
CREATE TABLE IF NOT EXISTS Participant (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    course VARCHAR(100)
);

-- TrainingSession table
CREATE TABLE IF NOT EXISTS TrainingSession (
    session_id INT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    start_date VARCHAR(20),
    end_date VARCHAR(20),
    time VARCHAR(20),
    duration INT,
    trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES Trainer(id)
);

-- Feedback table
CREATE TABLE IF NOT EXISTS Feedback (
    participant_id INT,
    session_id INT,
    rating INT,
    comment TEXT,
    instructor_rating INT DEFAULT 0,
    instructor_comment TEXT,
    PRIMARY KEY (participant_id, session_id),
    FOREIGN KEY (participant_id) REFERENCES Participant(id),
    FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id)
);

-- Session Registration table (for participant-session relationship)
CREATE TABLE IF NOT EXISTS SessionRegistration (
    participant_id INT,
    session_id INT,
    PRIMARY KEY (participant_id, session_id),
    FOREIGN KEY (participant_id) REFERENCES Participant(id),
    FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id)
);

-- Insert default admin
INSERT INTO Trainer (id, name, password, approved, course) 
VALUES (1, 'admin', 'admin123', TRUE, 'Administration')
ON DUPLICATE KEY UPDATE name = name;

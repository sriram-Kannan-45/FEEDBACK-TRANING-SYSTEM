-- Training Feedback System Database Setup

<<<<<<< HEAD
-- Create database if not exists
CREATE DATABASE IF NOT EXISTS feedback;
USE feedback;
=======
CREATE DATABASE IF NOT EXISTS trainingfeedback;
USE trainingfeedback;
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

-- Trainer table
CREATE TABLE IF NOT EXISTS Trainer (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    approved BOOLEAN DEFAULT FALSE,
<<<<<<< HEAD
    course VARCHAR(100)
=======
    course VARCHAR(100),
    email VARCHAR(100),
    department VARCHAR(100)
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
);

-- Participant table
CREATE TABLE IF NOT EXISTS Participant (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
<<<<<<< HEAD
=======
    dept VARCHAR(100),
    college VARCHAR(100),
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    course VARCHAR(100)
);

-- TrainingSession table
CREATE TABLE IF NOT EXISTS TrainingSession (
    session_id INT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
<<<<<<< HEAD
=======
    description TEXT,
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    start_date VARCHAR(20),
    end_date VARCHAR(20),
    time VARCHAR(20),
    duration INT,
<<<<<<< HEAD
=======
    location VARCHAR(200),
    max_participants INT DEFAULT 30,
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES Trainer(id)
);

-- Feedback table
CREATE TABLE IF NOT EXISTS Feedback (
<<<<<<< HEAD
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
=======
    id INT AUTO_INCREMENT PRIMARY KEY,
    participant_id INT,
    session_id INT,
    training_rating INT,
    instructor_rating INT,
    training_comment TEXT,
    instructor_comment TEXT,
    trainer_response TEXT,
    anonymous BOOLEAN DEFAULT FALSE,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (participant_id) REFERENCES Participant(id),
    FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id),
    UNIQUE KEY unique_feedback (participant_id, session_id)
);

-- Session Registration table
CREATE TABLE IF NOT EXISTS SessionRegistration (
    participant_id INT,
    session_id INT,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59
    PRIMARY KEY (participant_id, session_id),
    FOREIGN KEY (participant_id) REFERENCES Participant(id),
    FOREIGN KEY (session_id) REFERENCES TrainingSession(session_id)
);

<<<<<<< HEAD
-- Insert default admin
INSERT INTO Trainer (id, name, password, approved, course) 
VALUES (1, 'admin', 'admin123', TRUE, 'Administration')
ON DUPLICATE KEY UPDATE name = name;
=======
-- Survey table (for feedback surveys)
CREATE TABLE IF NOT EXISTS Survey (
    survey_id INT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Survey Question table
CREATE TABLE IF NOT EXISTS SurveyQuestion (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    survey_id INT,
    question_text TEXT NOT NULL,
    question_type VARCHAR(50),
    required BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (survey_id) REFERENCES Survey(survey_id)
);

-- Survey Question Options table
CREATE TABLE IF NOT EXISTS SurveyQuestionOption (
    option_id INT AUTO_INCREMENT PRIMARY KEY,
    question_id INT,
    option_text VARCHAR(200),
    FOREIGN KEY (question_id) REFERENCES SurveyQuestion(question_id)
);

-- Insert default admin trainer
INSERT INTO Trainer (id, name, password, approved, course, email, department) 
VALUES (1, 'admin', 'admin123', TRUE, 'Administration', 'admin@system.com', 'Admin')
ON DUPLICATE KEY UPDATE name = name;

-- Insert sample data for testing
INSERT INTO Trainer (id, name, password, approved, course, email, department) 
VALUES 
(2, 'John Smith', 'trainer123', TRUE, 'Java Programming', 'john.smith@training.com', 'IT'),
(3, 'Sarah Johnson', 'trainer123', TRUE, 'Web Development', 'sarah.j@training.com', 'IT'),
(4, 'Mike Brown', 'trainer123', FALSE, 'Database Design', 'mike.b@training.com', 'IT')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO Participant (id, name, password, email, dept, college, course)
VALUES 
(101, 'Alice Williams', 'student123', 'alice@college.edu', 'Computer Science', 'State University', 'Java'),
(102, 'Bob Davis', 'student123', 'bob@college.edu', 'Information Technology', 'Tech Institute', 'Web Dev'),
(103, 'Carol Martinez', 'student123', 'carol@college.edu', 'Computer Science', 'State University', 'Database')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO TrainingSession (session_id, title, description, start_date, end_date, time, duration, location, max_participants, trainer_id)
VALUES 
(1, 'Java Fundamentals', 'Introduction to Java programming', '2026-04-01', '2026-04-05', '09:00', 20, 'Room A101', 30, 2),
(2, 'Advanced Java', 'Advanced Java concepts and patterns', '2026-04-10', '2026-04-15', '10:00', 25, 'Room B202', 25, 2),
(3, 'Web Development Basics', 'HTML, CSS, JavaScript fundamentals', '2026-04-05', '2026-04-08', '14:00', 15, 'Lab C301', 20, 3)
ON DUPLICATE KEY UPDATE title = title;

INSERT INTO SessionRegistration (participant_id, session_id)
VALUES 
(101, 1),
(101, 2),
(102, 1),
(102, 3),
(103, 1),
(103, 2),
(103, 3)
ON DUPLICATE KEY UPDATE participant_id = participant_id;

INSERT INTO Feedback (participant_id, session_id, training_rating, instructor_rating, training_comment, instructor_comment, anonymous)
VALUES 
(101, 1, 4, 5, 'Great content coverage', 'Excellent teaching style', FALSE),
(102, 1, 5, 4, 'Very informative session', 'Good communication skills', FALSE),
(103, 2, 3, 4, 'Could use more practical examples', 'Clear explanations', FALSE)
ON DUPLICATE KEY UPDATE training_rating = training_rating;

-- Create survey
INSERT INTO Survey (survey_id, title, description, active)
VALUES (1, 'Training Feedback Survey', 'Standard feedback survey for all training sessions', TRUE)
ON DUPLICATE KEY UPDATE title = title;

INSERT INTO SurveyQuestion (question_id, survey_id, question_text, question_type, required)
VALUES 
(1, 1, 'How would you rate the training content?', 'RATING', TRUE),
(2, 1, 'How would you rate the instructor?', 'RATING', TRUE),
(3, 1, 'How would you rate the training materials?', 'RATING', TRUE),
(4, 1, 'How would you rate the overall experience?', 'RATING', TRUE),
(5, 1, 'What did you like most about the training?', 'TEXT', FALSE),
(6, 1, 'What areas need improvement?', 'TEXT', FALSE)
ON DUPLICATE KEY UPDATE question_text = question_text;
>>>>>>> eb73dc00f4e646735320b75e51ffdb6097bbcf59

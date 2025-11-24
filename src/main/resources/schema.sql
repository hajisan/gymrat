-- =========================================
--  Database: gymrat_db
--  Schema Creation Script
-- =========================================

-- Opret database hvis den ikke eksisterer
CREATE DATABASE IF NOT EXISTS gymrat_db;
USE gymrat_db;

-- =========================================
-- DROP TABLES (hvis de eksisterer)
-- =========================================
DROP TABLE IF EXISTS performed_set;
DROP TABLE IF EXISTS performed_exercise;
DROP TABLE IF EXISTS training_session;
DROP TABLE IF EXISTS exercise;

-- =========================================
-- TABLE: exercise
-- =========================================
CREATE TABLE IF NOT EXISTS exercise (
    exercise_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    target_muscle_group VARCHAR(255),
    equipment VARCHAR(255),
    exercise_type VARCHAR(20) NOT NULL DEFAULT 'REP_BASED',
    CONSTRAINT uq_exercise_name UNIQUE (name),
    INDEX idx_exercise_type (exercise_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================
-- TABLE: training_session
-- =========================================
CREATE TABLE IF NOT EXISTS training_session (
    training_session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at DATE NOT NULL,
    started_at DATETIME,
    completed_at DATETIME,
    note VARCHAR(255),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================
-- TABLE: performed_exercise
-- =========================================
CREATE TABLE IF NOT EXISTS performed_exercise (
    performed_exercise_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    training_session_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    order_number INT NOT NULL,
    CONSTRAINT fk_performed_exercise_session 
        FOREIGN KEY (training_session_id) 
        REFERENCES training_session(training_session_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_performed_exercise_exercise 
        FOREIGN KEY (exercise_id) 
        REFERENCES exercise(exercise_id)
        ON DELETE CASCADE,
    CONSTRAINT uq_session_order 
        UNIQUE (training_session_id, order_number),
    CONSTRAINT chk_order_number CHECK (order_number > 0),
    INDEX idx_se_session (training_session_id),
    INDEX idx_se_exercise (exercise_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================
-- TABLE: performed_set
-- =========================================
CREATE TABLE IF NOT EXISTS performed_set (
    performed_set_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    performed_exercise_id BIGINT NOT NULL,
    side_of_body VARCHAR(5) NOT NULL DEFAULT 'BOTH',
    set_number INT NOT NULL,
    weight DECIMAL(10,2),
    reps INT,
    duration_seconds INT,
    note VARCHAR(255),
    CONSTRAINT fk_performed_set_performed_exercise 
        FOREIGN KEY (performed_exercise_id) 
        REFERENCES performed_exercise(performed_exercise_id)
        ON DELETE CASCADE,
    CONSTRAINT uq_performed_set_exercise_setnumber 
        UNIQUE (performed_exercise_id, set_number),
    CONSTRAINT chk_reps_or_duration 
        CHECK (reps IS NOT NULL OR duration_seconds IS NOT NULL),
    INDEX idx_performed_set__exercise (performed_exercise_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =========================================
--  Database: gymrat_db
--  Seed data matching JPA entities
-- =========================================

-- -----------------------------------------
-- Table: exercise
-- -----------------------------------------
INSERT IGNORE INTO exercise (
    name,
    target_muscle_group,
    equipment
) VALUES
      ('Single leg launch med calf raise', 'Læg, lår og ankel', 'Squat rack'),
      ('Single leg calf raise', 'Læg og ankel', 'Smith machine'),
      ('Leg Curl', 'Lår', 'Leg curl machine'),
      ('Calf raise', 'Læg', 'Leg press'),
      ('Side step launch', 'Lår, læg, ankel, mobilitet', 'Blå skum til ankel + barbell'),
      ('Hip raises', 'Hofte, core, mobilitet', 'Yogamåtte'),
      ('Overkrops twist med bånd', 'Core, mavemuskler, arme, håndled', 'Lilla elastikbånd');

-- -----------------------------------------
-- Table: training_session
-- -----------------------------------------
INSERT IGNORE INTO training_session (
    created_at,
    note
) VALUES
    ('2025-10-25', 'god session, effektiv træning, nået mål');

-- -----------------------------------------
-- Table: performed_exercise
--  Kræver: training_session_id, exercise_id, order_number
-- -----------------------------------------
INSERT IGNORE INTO performed_exercise (
    training_session_id,
    exercise_id,
    order_number
) VALUES
      (1, 1, 1),
      (1, 2, 2);

-- Antag ovenstående performed_exercise får id = 1 og 2

-- -----------------------------------------
-- Table: exercise_set
--  Kræver: performed_exercise_id, side_of_body, set_number, weight
--  Mindst ét af 'reps' eller 'duration_seconds' skal være sat
--  Kolonnenavnene følger Spring/Hibernate snake_case:
--   durationSeconds-feltet i entity -> duration_seconds i DB
-- -----------------------------------------
INSERT IGNORE INTO exercise_set (
    performed_exercise_id,
    side_of_body,
    set_number,
    weight,
    reps,
    note
) VALUES
      (1, 'BOTH', 1, 40, 12, 'opvarmning'),
      (1, 'BOTH', 2, 50, 10, NULL),
      (2, 'BOTH', 1, 20, 15, NULL);

-- =========================================
--  End of dataset
-- =========================================
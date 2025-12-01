-- =========================================
--  Database: gymrat_db
--  Seed data matching JPA entities
--  Migration af 17 workouts fra iPhone noter
-- =========================================

USE gymrat_db;

-- -----------------------------------------
-- Table: exercise
-- -----------------------------------------
INSERT IGNORE INTO exercise (
    name,
    target_muscle_group,
    equipment,
    exercise_type
) VALUES
      -- Eksisterende exercises
      ('Single leg launch med calf raise', 'Læg, lår og ankel', 'Squat rack', 'REP_BASED'),
      ('Single leg calf raise', 'Læg og ankel', 'Smith machine', 'REP_BASED'),
      ('Leg Curl', 'Lår', 'Leg curl machine', 'REP_BASED'),
      ('Calf raise', 'Læg', 'Leg press', 'REP_BASED'),
      ('Side step launch', 'Lår, læg, ankel, mobilitet', 'Blå skum til ankel + barbell', 'REP_BASED'),
      ('Hip raises', 'Hofte, core, mobilitet', 'Yogamåtte', 'REP_BASED'),
      ('Overkrops twist med bånd', 'Core, mavemuskler, arme, håndled', 'Lilla elastikbånd', 'REP_BASED'),
      ('Planke', 'Core', 'Yogamåtte', 'DURATION_BASED'),
      ('Ankel twist med kabel', 'Læg, ankel', 'Kabel', 'REP_BASED'),
      ('Calf raise begge ben', 'Læg', 'Smith machine', 'REP_BASED'),
      ('Launches på Bosu', 'Lår, balance', 'Bosu bold', 'REP_BASED'),
      ('Launches med twist', 'Lår, core', 'Barbell/Elastik', 'REP_BASED'),
      ('Pogo jumps', 'Læg, eksplosivitet', 'Elastikbånd', 'DURATION_BASED'),
      ('Håndled extension', 'Håndled, underarm', 'Dumbbell', 'DURATION_BASED'),
      ('Heel lifts', 'Læg', 'Leg press', 'REP_BASED'),
      ('Ben press', 'Lår, læg', 'Leg press', 'REP_BASED'),
      ('Power sled', 'Lår, eksplosivitet', 'Sled', 'REP_BASED'),
      ('Hop ned fra stepbænk', 'Delacceleration, stabilitet', 'Stepbænk', 'REP_BASED'),
      ('One legged around the globe med kettlebell', 'Balance, core, ben, ankel stabilitet', 'Kettlebell', 'REP_BASED'),
      ('Hop på stepbænk med 2 ben / land på 1', 'Læg, balance, eksplosivitet', 'Stepbænk', 'REP_BASED'),
      ('Lateral Ball throw', 'Core, eksplosivitet, koordination', 'Medicinbold', 'REP_BASED');

-- =========================================
-- WORKOUT MIGRATIONS (17 total)
-- =========================================

-- -----------------------------------------
-- WORKOUT #1: 31. Maj 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-05-31', '2025-05-31 10:00:00', '2025-05-31 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise (var: Leg press): 245kg, 12 reps
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 4, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 245, 12, '12 RM');

-- Calf raise begge ben (var: Smith machine): 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL),
       (@pe_id, 'BOTH', 2, 80, 10, NULL);

-- Leg curl: 70kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 70, 12, NULL),
       (@pe_id, 'BOTH', 2, 70, 12, NULL);

-- Ankel twist med kabel: 20kg - Set 1
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 20, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 20, 10, 'inwards'),
       (@pe_id, 'LEFT', 3, 20, 10, 'outwards'),
       (@pe_id, 'LEFT', 4, 20, 10, 'inwards');

-- Ankel twist med kabel: 25kg - Set 2
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 25, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 25, 10, 'inwards'),
       (@pe_id, 'LEFT', 3, 25, 10, 'outwards'),
       (@pe_id, 'LEFT', 4, 25, 10, 'inwards');

-- -----------------------------------------
-- WORKOUT #2: 7. Juni 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-06-07', '2025-06-07 10:00:00', '2025-06-07 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Leg curl
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 70, 14, NULL),
       (@pe_id, 'BOTH', 2, 75, 12, NULL);

-- Calf raise begge ben (var: Smith machine)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 8, NULL),
       (@pe_id, 'BOTH', 2, 75, 10, NULL),
       (@pe_id, 'BOTH', 3, 70, 8, NULL);

-- Ankel twist med kabel: 20kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 20, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 20, 10, 'inwards'),
       (@pe_id, 'LEFT', 3, 20, 10, 'outwards'),
       (@pe_id, 'LEFT', 4, 20, 10, 'inwards');

-- -----------------------------------------
-- WORKOUT #3: 11. Juni 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-06-11', '2025-06-11 10:00:00', '2025-06-11 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine): 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL),
       (@pe_id, 'BOTH', 2, 80, 12, NULL);

-- Leg curl: 75kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 75, 12, NULL),
       (@pe_id, 'BOTH', 2, 75, 12, NULL);

-- Ankel twist med kabel: 15kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 15, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 15, 10, 'inwards'),
       (@pe_id, 'LEFT', 3, 15, 10, 'outwards'),
       (@pe_id, 'LEFT', 4, 15, 10, 'inwards');

-- -----------------------------------------
-- WORKOUT #4: 16. Juni 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-06-16', '2025-06-16 10:00:00', '2025-06-16 11:30:00', 'alt hoppe, sjippe og power sled sprunget over');
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine): 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL),
       (@pe_id, 'BOTH', 2, 80, 10, NULL);

-- Leg curl: 75kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 75, 12, NULL);

-- -----------------------------------------
-- WORKOUT #5: 19. Juni 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-06-19', '2025-06-19 10:00:00', '2025-06-19 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine): 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL);

-- Leg curl: 75kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 75, 12, NULL);

-- Power sled: 100kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 17, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 100, 5, '5x20m');

-- -----------------------------------------
-- WORKOUT #6: 22. Juni 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-06-22', '2025-06-22 10:00:00', '2025-06-22 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine): 90kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 10, NULL),
       (@pe_id, 'BOTH', 2, 90, 10, NULL);

-- Leg curl
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 85, 10, NULL),
       (@pe_id, 'BOTH', 2, 80, 10, NULL);

-- Ankel twist med kabel: 30kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 30, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 30, 10, 'inwards'),
       (@pe_id, 'LEFT', 3, 30, 10, 'outwards'),
       (@pe_id, 'LEFT', 4, 30, 10, 'inwards');

-- Hip raises (var: Side plank med knæ)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 6, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 10, NULL),
       (@pe_id, 'LEFT', 3, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 4, NULL, 10, NULL);

-- Pogo jumps (var: Jumps med elastik): 10 sets × 30 sek
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 13, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, duration_seconds, note)
VALUES (@pe_id, 'BOTH', 1, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 2, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 3, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 4, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 5, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 6, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 7, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 8, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 9, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 10, NULL, NULL, 30, NULL);

-- Power sled
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 17, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 3, NULL),
       (@pe_id, 'BOTH', 2, 80, 2, NULL),
       (@pe_id, 'BOTH', 3, 70, 3, NULL),
       (@pe_id, 'BOTH', 4, 60, 2, NULL);

-- -----------------------------------------
-- WORKOUT #7: 4. Juli 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-07-04', '2025-07-04 10:00:00', '2025-07-04 11:30:00', 'Ingen smerter efter session');
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine): 100kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 100, 10, NULL),
       (@pe_id, 'BOTH', 2, 100, 8, NULL),
       (@pe_id, 'BOTH', 3, 100, 5, NULL);

-- Leg curl: 85kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 85, 8, NULL),
       (@pe_id, 'BOTH', 2, 85, 5, NULL);

-- Single leg calf raise - højre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 12, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 8, NULL);

-- Single leg calf raise - venstre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 4, NULL),
       (@pe_id, 'LEFT', 2, NULL, 3, NULL),
       (@pe_id, 'LEFT', 3, NULL, 4, 'Smerte i akillen, lige under knysten ved fuld extend');

-- Launches på Bosu
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 11, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 10, NULL),
       (@pe_id, 'LEFT', 2, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 3, NULL, 10, NULL),
       (@pe_id, 'LEFT', 4, NULL, 10, NULL);

-- Launches med twist
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 12, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, NULL, 20, '20m'),
       (@pe_id, 'BOTH', 2, NULL, 20, '20m');

-- Hip raises (var: Side plank med knæ)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 6, 7);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 15, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 15, NULL),
       (@pe_id, 'LEFT', 3, NULL, 15, NULL),
       (@pe_id, 'LEFT', 4, NULL, 15, NULL);

-- -----------------------------------------
-- WORKOUT #8: 7. Juli 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-07-07', '2025-07-07 10:00:00', '2025-07-07 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 110, 8, NULL),
       (@pe_id, 'BOTH', 2, 115, 6, NULL);

-- Leg curl: 90kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 6, NULL),
       (@pe_id, 'BOTH', 2, 90, 6, NULL);

-- Single leg calf raise - højre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 14, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 13, NULL);

-- Single leg calf raise - venstre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 14, NULL),
       (@pe_id, 'LEFT', 2, NULL, 13, NULL);

-- Launches på Bosu
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 11, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 15, NULL),
       (@pe_id, 'LEFT', 2, NULL, 15, NULL),
       (@pe_id, 'RIGHT', 3, NULL, 15, NULL),
       (@pe_id, 'LEFT', 4, NULL, 15, NULL);

-- Launches med twist
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 12, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, NULL, 20, 'varm op med lilla elastik twists - 20m'),
       (@pe_id, 'BOTH', 2, NULL, 20, '20m');

-- Hip raises (var: Side plank med knæ)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 6, 7);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 18, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 13, NULL),
       (@pe_id, 'LEFT', 3, NULL, 18, NULL),
       (@pe_id, 'LEFT', 4, NULL, 13, NULL);

-- -----------------------------------------
-- WORKOUT #9: 10. Juli 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-07-10', '2025-07-10 10:00:00', '2025-07-10 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben (var: Smith machine): 115kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 115, 6, NULL),
       (@pe_id, 'BOTH', 2, 115, 6, NULL);

-- Leg curl: 90kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 6, NULL),
       (@pe_id, 'BOTH', 2, 90, 6, NULL);

-- Single leg calf raise - højre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 6, NULL);

-- Single leg calf raise - venstre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 6, NULL),
       (@pe_id, 'LEFT', 2, NULL, 6, NULL);

-- Launches på Bosu
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 11, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 15, NULL),
       (@pe_id, 'LEFT', 2, NULL, 15, NULL);

-- Launches med twist: 10kg dumbbell
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 12, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 10, 20, '2x10kg dumbbell - 20m'),
       (@pe_id, 'BOTH', 2, 10, 20, '2x10kg dumbbell - 20m');

-- Pogo jumps: 10 sets × 30 sek
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 13, 7);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, duration_seconds, note)
VALUES (@pe_id, 'BOTH', 1, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 2, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 3, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 4, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 5, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 6, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 7, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 8, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 9, NULL, NULL, 30, NULL),
       (@pe_id, 'BOTH', 10, NULL, NULL, 30, NULL);

-- -----------------------------------------
-- WORKOUT #10: 29. Juli 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-07-29', '2025-07-29 10:00:00', '2025-07-29 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Calf raise (var: Leg press): 245kg 16 reps (max rep)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 4, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 245, 16, 'max rep');

-- Calf raise (var: Leg press) - Single leg højre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 4, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 117, 9, NULL),
       (@pe_id, 'RIGHT', 2, 109, 11, NULL);

-- Calf raise (var: Leg press) - Single leg venstre
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 4, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 117, 10, NULL),
       (@pe_id, 'LEFT', 2, 109, 12, NULL);

-- Heel lifts
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 15, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 149, 10, 'test'),
       (@pe_id, 'BOTH', 2, 157, 12, NULL);

-- Squats: 205kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 16, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 205, 12, NULL),
       (@pe_id, 'BOTH', 2, 205, 17, NULL);

-- Leg curl: 90kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 7, NULL),
       (@pe_id, 'BOTH', 2, 90, 6, NULL);

-- -----------------------------------------
-- WORKOUT #11: 29. September 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-09-29', '2025-09-29 10:00:00', '2025-09-29 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Squats: 245kg 8 reps (max ud)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 16, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 245, 8, 'max ud'),
       (@pe_id, 'BOTH', 2, 239, 10, NULL),
       (@pe_id, 'BOTH', 3, 239, 10, NULL);

-- Leg curl: 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL),
       (@pe_id, 'BOTH', 2, 80, 10, NULL);

-- Håndled extension: 8kg barbell
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 14, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, duration_seconds, note)
VALUES (@pe_id, 'BOTH', 1, 8, NULL, 30, NULL),
       (@pe_id, 'BOTH', 2, 8, NULL, 45, NULL);

-- Overkrops twist med bånd
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 7, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 10, 'Sort elastik - extend arm'),
       (@pe_id, 'RIGHT', 2, NULL, 10, 'Sort elastik - extend arm'),
       (@pe_id, 'LEFT', 3, NULL, 10, 'Sort elastik - extend arm'),
       (@pe_id, 'LEFT', 4, NULL, 10, 'Sort elastik - extend arm');

-- -----------------------------------------
-- WORKOUT #12: 9. Oktober 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-10-09', '2025-10-09 10:00:00', '2025-10-09 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Single leg launch med calf raise: 25kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 25, 11, NULL),
       (@pe_id, 'RIGHT', 2, 25, 11, NULL),
       (@pe_id, 'LEFT', 3, 25, 10, NULL),
       (@pe_id, 'RIGHT', 4, 25, 10, NULL);

-- Single leg calf raise
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 10, NULL),
       (@pe_id, 'LEFT', 3, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 4, NULL, 8, NULL);

-- Leg curl
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 8, 'hårdt fra 5. rep'),
       (@pe_id, 'BOTH', 2, 85, 8, NULL);

-- Overkrops twist med bånd
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 7, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 10, 'lilla bånd'),
       (@pe_id, 'LEFT', 2, NULL, 10, 'lilla bånd');

-- Håndled extension: 8kg dumbbell, 6 sets × 30 sek (3 per side)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 14, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, duration_seconds, note)
VALUES (@pe_id, 'RIGHT', 1, 8, NULL, 30, NULL),
       (@pe_id, 'LEFT', 2, 8, NULL, 30, NULL),
       (@pe_id, 'RIGHT', 3, 8, NULL, 30, NULL),
       (@pe_id, 'LEFT', 4, 8, NULL, 30, NULL),
       (@pe_id, 'RIGHT', 5, 8, NULL, 30, NULL),
       (@pe_id, 'LEFT', 6, 8, NULL, 30, NULL);

-- -----------------------------------------
-- WORKOUT #13: 12. Oktober 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-10-12', '2025-10-12 10:00:00', '2025-10-12 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Single leg launch med calf raise: 25kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 25, 10, NULL),
       (@pe_id, 'RIGHT', 2, 25, 10, NULL),
       (@pe_id, 'LEFT', 3, 25, 10, NULL),
       (@pe_id, 'RIGHT', 4, 25, 10, NULL);

-- Single leg calf raise
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 10, NULL),
       (@pe_id, 'LEFT', 3, NULL, 10, NULL),
       (@pe_id, 'RIGHT', 4, NULL, 10, NULL);

-- Squats (var: Leg press): 229kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 16, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 229, 10, NULL),
       (@pe_id, 'BOTH', 2, 229, 10, NULL);

-- Leg curl: 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 10, NULL),
       (@pe_id, 'BOTH', 2, 80, 10, NULL);

-- -----------------------------------------
-- WORKOUT #14: 23. Oktober 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-10-23', '2025-10-23 10:00:00', '2025-10-23 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Single leg launch med calf raise: 25kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 25, 12, NULL),
       (@pe_id, 'RIGHT', 2, 25, 12, NULL);

-- Single leg launch med calf raise: 27.5kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 27.5, 10, NULL),
       (@pe_id, 'RIGHT', 2, 27.5, 10, NULL);

-- Single leg calf raise
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, NULL, 9, NULL),
       (@pe_id, 'RIGHT', 2, NULL, 9, NULL);

-- Single leg calf raise: 10kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 10, 8, NULL),
       (@pe_id, 'RIGHT', 2, 10, 8, NULL);

-- Leg curl: 85kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 85, 8, NULL),
       (@pe_id, 'BOTH', 2, 85, 8, NULL);

-- -----------------------------------------
-- WORKOUT #15: 29. Oktober 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-10-29', '2025-10-29 10:00:00', '2025-10-29 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Single leg launch med calf raise: 30kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 30, 8, NULL),
       (@pe_id, 'RIGHT', 2, 30, 8, NULL),
       (@pe_id, 'LEFT', 3, 30, 8, NULL),
       (@pe_id, 'RIGHT', 4, 30, 8, NULL);

-- Single leg calf raise: 10kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 2, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 10, 9, NULL),
       (@pe_id, 'RIGHT', 2, 10, 8, NULL),
       (@pe_id, 'LEFT', 3, 10, 8, NULL),
       (@pe_id, 'RIGHT', 4, 10, 8, NULL);

-- -----------------------------------------
-- WORKOUT #16: 16. November 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-11-16', '2025-11-16 10:00:00', '2025-11-16 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Ankel twist med kabel: 25kg - Set 1
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 25, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 25, 15, 'inwards'),
       (@pe_id, 'LEFT', 3, 25, 18, 'outwards'),
       (@pe_id, 'LEFT', 4, 25, 10, 'inwards');

-- Ankel twist med kabel: 25kg - Set 2
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 25, 12, 'outwards'),
       (@pe_id, 'RIGHT', 2, 25, 12, 'inwards'),
       (@pe_id, 'LEFT', 3, 25, 12, 'outwards'),
       (@pe_id, 'LEFT', 4, 25, 12, 'inwards');

-- Calf raise begge ben (var: Smith machine): 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL),
       (@pe_id, 'BOTH', 2, 80, 12, NULL);

-- Single leg launch med calf raise: 35kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 35, 7, NULL),
       (@pe_id, 'RIGHT', 2, 35, 7, NULL);

-- Single leg launch med calf raise: 40kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 40, 7, NULL),
       (@pe_id, 'RIGHT', 2, 40, 7, NULL);

-- Leg curl: 95kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 95, 6, NULL),
       (@pe_id, 'BOTH', 2, 95, 6, NULL);

-- Side step launch (var: Side lunges)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 5, 7);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, NULL, 10, 'med stang'),
       (@pe_id, 'BOTH', 2, 8, 10, NULL),
       (@pe_id, 'BOTH', 3, 10, 10, NULL);

-- -----------------------------------------
-- WORKOUT #17: 20. November 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-11-20', '2025-11-20 10:00:00', '2025-11-20 11:30:00', NULL);
SET @session_id = LAST_INSERT_ID();

-- Ankel twist med kabel: 30kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 9, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 30, 10, 'outwards'),
       (@pe_id, 'RIGHT', 2, 30, 10, 'inwards'),
       (@pe_id, 'LEFT', 3, 30, 10, 'outwards'),
       (@pe_id, 'LEFT', 4, 30, 10, 'inwards');

-- Calf raise begge ben (var: Smith machine): 80kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 80, 12, NULL),
       (@pe_id, 'BOTH', 2, 80, 12, NULL);

-- Single leg launch med calf raise: 42.5kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 42.5, 6, NULL),
       (@pe_id, 'RIGHT', 2, 42.5, 6, NULL),
       (@pe_id, 'LEFT', 3, 42.5, 6, NULL),
       (@pe_id, 'RIGHT', 4, 42.5, 6, NULL);

-- Leg curl: 95kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 95, 6, NULL),
       (@pe_id, 'BOTH', 2, 95, 6, NULL);

-- -----------------------------------------
-- WORKOUT #18: 25. November 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-11-25', '2025-11-25 18:30:00', '2025-11-25 19:40:00', 'Super træning, ingen smerter under, føler mig stærk!');
SET @session_id = LAST_INSERT_ID();

-- Calf raise begge ben: 90kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 90, 10, NULL),
       (@pe_id, 'BOTH', 2, 90, 7, NULL);

-- Single leg launch med calf raise: 45kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 45, 6, NULL),
       (@pe_id, 'RIGHT', 2, 45, 6, NULL),
       (@pe_id, 'LEFT', 3, 45, 11, NULL),
       (@pe_id, 'RIGHT', 4, 45, 11, NULL);

-- Leg curl: 95kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 3, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 95, 6, NULL),
       (@pe_id, 'BOTH', 2, 95, 6, NULL);

-- Side step launch
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 5, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, NULL, 10, NULL),
       (@pe_id, 'BOTH', 2, 10, 10, NULL),
       (@pe_id, 'BOTH', 3, 10, 10, 'uden 10kg plate');

-- Hop ned fra step
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 18, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, NULL, 10, 'hop ned'),
       (@pe_id, 'BOTH', 2, NULL, 10, 'hop ned');

-- One legged around the globe med kettlebell: 12kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 19, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 12, 20, 'højre fod'),
       (@pe_id, 'LEFT', 2, 12, 20, 'højre fod'),
       (@pe_id, 'RIGHT', 3, 12, 20, 'venstre fod'),
       (@pe_id, 'LEFT', 4, 12, 20, 'venstre fod');

-- -----------------------------------------
-- WORKOUT #19: 1. December 2025
-- -----------------------------------------
INSERT INTO training_session (created_at, started_at, completed_at, note)
VALUES ('2025-12-01', '2025-12-01 11:30:00', '2025-12-01 12:30:00', 'Ofelia introducerede nogle potentielt nye øvelser. Kun hvis jeg føler for det og hvis jeg har tid og overskud. Ellers super træning. Kan mærke min højre akille og ankel mere end normalt. Ingen smerte.');
SET @session_id = LAST_INSERT_ID();

-- Hop ned fra step
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 18, 1);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, NULL, 10, NULL),
       (@pe_id, 'BOTH', 2, NULL, 10, NULL);

-- Hop på stepbænk med 2 ben / land på 1
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 20, 2);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, NULL, 8, NULL),
       (@pe_id, 'LEFT', 2, NULL, 8, NULL);

-- One legged around the globe med kettlebell (på pude)
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 19, 3);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 6, 10, 'højre fod'),
       (@pe_id, 'LEFT', 2, 6, 10, 'højre fod'),
       (@pe_id, 'RIGHT', 3, 10, 10, 'venstre fod'),
       (@pe_id, 'LEFT', 4, 10, 10, 'venstre fod');

-- Lateral Ball throw: 4kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 21, 4);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'RIGHT', 1, 4, 10, NULL),
       (@pe_id, 'LEFT', 2, 4, 10, NULL);

-- Single leg launch med calf raise: 45kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 1, 5);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'LEFT', 1, 45, 8, NULL),
       (@pe_id, 'RIGHT', 2, 45, 8, NULL),
       (@pe_id, 'LEFT', 3, 45, 10, NULL),
       (@pe_id, 'RIGHT', 4, 45, 10, NULL);

-- Calf raise begge ben (var: Smith machine): 95kg
INSERT INTO performed_exercise (training_session_id, exercise_id, order_number)
VALUES (@session_id, 10, 6);
SET @pe_id = LAST_INSERT_ID();
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps, note)
VALUES (@pe_id, 'BOTH', 1, 95, 9, NULL),
       (@pe_id, 'BOTH', 2, 95, 9, NULL);

-- =========================================
--  End of migration dataset
-- =========================================
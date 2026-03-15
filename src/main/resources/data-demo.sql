-- =========================================
--  Demo seed data - H2 compatible
--  Mock workout history for portfolio demo
-- =========================================

-- =========================================
-- EXERCISES
-- =========================================
INSERT INTO exercise (exercise_id, name, target_muscle_group, equipment, exercise_type) VALUES
(1, 'Ben press',                    'Lår, læg',                     'Leg press',                    'REP_BASED'),
(2, 'Leg Curl',                     'Lår',                          'Leg curl machine',              'REP_BASED'),
(3, 'Single leg calf raise',        'Læg og ankel',                  'Smith machine',                 'REP_BASED'),
(4, 'Hip raises',                   'Hofte, core, mobilitet',        'Yogamåtte',                    'REP_BASED'),
(5, 'Planke',                       'Core',                          'Yogamåtte',                    'DURATION_BASED'),
(6, 'Launches på Bosu',             'Lår, balance',                  'Bosu bold',                    'REP_BASED'),
(7, 'Side step launch',             'Lår, læg, ankel, mobilitet',    'Barbell',                      'REP_BASED'),
(8, 'Calf raise',                   'Læg',                           'Leg press',                    'REP_BASED'),
(9, 'Single leg launch med calf raise', 'Læg, lår og ankel',         'Squat rack',                   'REP_BASED'),
(10, 'Power sled',                  'Lår, eksplosivitet',            'Sled',                         'REP_BASED');

-- =========================================
-- SESSIONS (18 sessions over 6 months)
-- =========================================

-- Session 1 - Oct 1 (light)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(1, '2025-10-01', '2025-10-01 10:00:00', '2025-10-01 11:00:00', 'Første træning efter pause. Tager det roligt.');

-- Session 2 - Oct 8 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(2, '2025-10-08', '2025-10-08 09:30:00', '2025-10-08 11:00:00', NULL);

-- Session 3 - Oct 15 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(3, '2025-10-15', '2025-10-15 10:00:00', '2025-10-15 11:30:00', 'God session. Løftede mere på ben press.');

-- Session 4 - Oct 22 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(4, '2025-10-22', '2025-10-22 09:00:00', '2025-10-22 10:30:00', NULL);

-- Session 5 - Nov 3 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(5, '2025-11-03', '2025-11-03 10:00:00', '2025-11-03 11:15:00', NULL);

-- Session 6 - Nov 10 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(6, '2025-11-10', '2025-11-10 09:30:00', '2025-11-10 11:10:00', 'Ny personlig rekord på ben press - 90kg!');

-- Session 7 - Nov 17 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(7, '2025-11-17', '2025-11-17 10:00:00', '2025-11-17 11:20:00', NULL);

-- Session 8 - Nov 25 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(8, '2025-11-25', '2025-11-25 09:00:00', '2025-11-25 10:45:00', 'Super træning, ingen smerter under, føler mig stærk!');

-- Session 9 - Dec 2 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(9, '2025-12-02', '2025-12-02 10:00:00', '2025-12-02 11:30:00', NULL);

-- Session 10 - Dec 9 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(10, '2025-12-09', '2025-12-09 09:30:00', '2025-12-09 11:00:00', NULL);

-- Session 11 - Dec 16 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(11, '2025-12-16', '2025-12-16 10:00:00', '2025-12-16 11:10:00', 'Lidt træt men kom igennem det.');

-- Session 12 - Jan 7 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(12, '2026-01-07', '2026-01-07 10:00:00', '2026-01-07 11:15:00', 'Godt nytår. Klar til 2026!');

-- Session 13 - Jan 14 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(13, '2026-01-14', '2026-01-14 09:30:00', '2026-01-14 11:00:00', NULL);

-- Session 14 - Jan 21 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(14, '2026-01-21', '2026-01-21 10:00:00', '2026-01-21 11:20:00', NULL);

-- Session 15 - Feb 4 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(15, '2026-02-04', '2026-02-04 09:00:00', '2026-02-04 10:45:00', '105kg på ben press. Stærkeste session nogensinde.');

-- Session 16 - Feb 18 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(16, '2026-02-18', '2026-02-18 10:00:00', '2026-02-18 11:15:00', NULL);

-- Session 17 - Mar 4 (heavy)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(17, '2026-03-04', '2026-03-04 09:30:00', '2026-03-04 11:00:00', NULL);

-- Session 18 - Mar 10 (medium)
INSERT INTO training_session (training_session_id, created_at, started_at, completed_at, note) VALUES
(18, '2026-03-10', '2026-03-10 10:00:00', '2026-03-10 11:10:00', 'Fokus på teknik i dag.');

-- =========================================
-- PERFORMED EXERCISES
-- =========================================
-- Session 1: Ben press, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(1, 1, 1, 1),  -- Ben press
(2, 1, 5, 2);  -- Planke

-- Session 2: Ben press, Leg Curl, Hip raises
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(3, 2, 1, 1),  -- Ben press
(4, 2, 2, 2),  -- Leg Curl
(5, 2, 4, 3);  -- Hip raises

-- Session 3: Ben press, Leg Curl, Single leg calf raise, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(6, 3, 1, 1),  -- Ben press
(7, 3, 2, 2),  -- Leg Curl
(8, 3, 3, 3),  -- Single leg calf raise
(9, 3, 5, 4);  -- Planke

-- Session 4: Ben press, Hip raises, Calf raise
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(10, 4, 1, 1),  -- Ben press
(11, 4, 4, 2),  -- Hip raises
(12, 4, 8, 3);  -- Calf raise

-- Session 5: Ben press, Leg Curl, Launches på Bosu, Hip raises
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(13, 5, 1, 1),  -- Ben press
(14, 5, 2, 2),  -- Leg Curl
(15, 5, 6, 3),  -- Launches på Bosu
(16, 5, 4, 4);  -- Hip raises

-- Session 6: Ben press, Leg Curl, Single leg calf raise, Planke, Calf raise
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(17, 6, 1, 1),  -- Ben press
(18, 6, 2, 2),  -- Leg Curl
(19, 6, 3, 3),  -- Single leg calf raise
(20, 6, 5, 4),  -- Planke
(21, 6, 8, 5);  -- Calf raise

-- Session 7: Ben press, Side step launch, Hip raises, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(22, 7, 1, 1),  -- Ben press
(23, 7, 7, 2),  -- Side step launch
(24, 7, 4, 3),  -- Hip raises
(25, 7, 5, 4);  -- Planke

-- Session 8: Ben press, Leg Curl, Launches på Bosu, Single leg calf raise, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(26, 8, 1, 1),  -- Ben press
(27, 8, 2, 2),  -- Leg Curl
(28, 8, 6, 3),  -- Launches på Bosu
(29, 8, 3, 4),  -- Single leg calf raise
(30, 8, 5, 5);  -- Planke

-- Session 9: Ben press, Leg Curl, Calf raise, Hip raises
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(31, 9, 1, 1),  -- Ben press
(32, 9, 2, 2),  -- Leg Curl
(33, 9, 8, 3),  -- Calf raise
(34, 9, 4, 4);  -- Hip raises

-- Session 10: Ben press, Leg Curl, Single leg calf raise, Power sled, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(35, 10, 1, 1),  -- Ben press
(36, 10, 2, 2),  -- Leg Curl
(37, 10, 3, 3),  -- Single leg calf raise
(38, 10, 10, 4), -- Power sled
(39, 10, 5, 5);  -- Planke

-- Session 11: Ben press, Side step launch, Hip raises
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(40, 11, 1, 1),  -- Ben press
(41, 11, 7, 2),  -- Side step launch
(42, 11, 4, 3);  -- Hip raises

-- Session 12: Ben press, Leg Curl, Launches på Bosu, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(43, 12, 1, 1),  -- Ben press
(44, 12, 2, 2),  -- Leg Curl
(45, 12, 6, 3),  -- Launches på Bosu
(46, 12, 5, 4);  -- Planke

-- Session 13: Ben press, Leg Curl, Single leg calf raise, Power sled, Hip raises
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(47, 13, 1, 1),  -- Ben press
(48, 13, 2, 2),  -- Leg Curl
(49, 13, 3, 3),  -- Single leg calf raise
(50, 13, 10, 4), -- Power sled
(51, 13, 4, 5);  -- Hip raises

-- Session 14: Ben press, Leg Curl, Calf raise, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(52, 14, 1, 1),  -- Ben press
(53, 14, 2, 2),  -- Leg Curl
(54, 14, 8, 3),  -- Calf raise
(55, 14, 5, 4);  -- Planke

-- Session 15: Ben press, Leg Curl, Single leg calf raise, Power sled, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(56, 15, 1, 1),  -- Ben press
(57, 15, 2, 2),  -- Leg Curl
(58, 15, 3, 3),  -- Single leg calf raise
(59, 15, 10, 4), -- Power sled
(60, 15, 5, 5);  -- Planke

-- Session 16: Ben press, Launches på Bosu, Hip raises, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(61, 16, 1, 1),  -- Ben press
(62, 16, 6, 2),  -- Launches på Bosu
(63, 16, 4, 3),  -- Hip raises
(64, 16, 5, 4);  -- Planke

-- Session 17: Ben press, Leg Curl, Single leg calf raise, Power sled, Planke
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(65, 17, 1, 1),  -- Ben press
(66, 17, 2, 2),  -- Leg Curl
(67, 17, 3, 3),  -- Single leg calf raise
(68, 17, 10, 4), -- Power sled
(69, 17, 5, 5);  -- Planke

-- Session 18: Ben press, Leg Curl, Hip raises, Calf raise
INSERT INTO performed_exercise (performed_exercise_id, training_session_id, exercise_id, order_number) VALUES
(70, 18, 1, 1),  -- Ben press
(71, 18, 2, 2),  -- Leg Curl
(72, 18, 4, 3),  -- Hip raises
(73, 18, 8, 4);  -- Calf raise

-- =========================================
-- PERFORMED SETS
-- Ben press progression: 60→110kg over 6 months
-- =========================================

-- S1 PE1 - Ben press (60kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(1, 'BOTH', 1, 55.00, 12),
(1, 'BOTH', 2, 60.00, 10),
(1, 'BOTH', 3, 60.00, 8);

-- S1 PE2 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(2, 'BOTH', 1, 45),
(2, 'BOTH', 2, 45);

-- S2 PE3 - Ben press (65kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(3, 'BOTH', 1, 60.00, 12),
(3, 'BOTH', 2, 65.00, 10),
(3, 'BOTH', 3, 65.00, 8);

-- S2 PE4 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(4, 'BOTH', 1, 50.00, 12),
(4, 'BOTH', 2, 55.00, 10),
(4, 'BOTH', 3, 55.00, 8);

-- S2 PE5 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(5, 'BOTH', 1, 15),
(5, 'BOTH', 2, 15),
(5, 'BOTH', 3, 12);

-- S3 PE6 - Ben press (70kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(6, 'BOTH', 1, 65.00, 12),
(6, 'BOTH', 2, 70.00, 10),
(6, 'BOTH', 3, 70.00, 8);

-- S3 PE7 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(7, 'BOTH', 1, 55.00, 12),
(7, 'BOTH', 2, 60.00, 10),
(7, 'BOTH', 3, 60.00, 8);

-- S3 PE8 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(8, 'LEFT',  1, 10.00, 15),
(8, 'RIGHT', 2, 10.00, 15),
(8, 'LEFT',  3, 10.00, 12),
(8, 'RIGHT', 4, 10.00, 12);

-- S3 PE9 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(9, 'BOTH', 1, 60),
(9, 'BOTH', 2, 60);

-- S4 PE10 - Ben press (70kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(10, 'BOTH', 1, 70.00, 10),
(10, 'BOTH', 2, 70.00, 10),
(10, 'BOTH', 3, 72.50, 8);

-- S4 PE11 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(11, 'BOTH', 1, 15),
(11, 'BOTH', 2, 15),
(11, 'BOTH', 3, 15);

-- S4 PE12 - Calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(12, 'BOTH', 1, 40.00, 20),
(12, 'BOTH', 2, 40.00, 20),
(12, 'BOTH', 3, 45.00, 15);

-- S5 PE13 - Ben press (75kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(13, 'BOTH', 1, 70.00, 12),
(13, 'BOTH', 2, 75.00, 10),
(13, 'BOTH', 3, 75.00, 8);

-- S5 PE14 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(14, 'BOTH', 1, 60.00, 12),
(14, 'BOTH', 2, 65.00, 10),
(14, 'BOTH', 3, 65.00, 8);

-- S5 PE15 - Launches på Bosu
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(15, 'BOTH', 1, 12),
(15, 'BOTH', 2, 12),
(15, 'BOTH', 3, 10);

-- S5 PE16 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(16, 'BOTH', 1, 15),
(16, 'BOTH', 2, 15);

-- S6 PE17 - Ben press (80kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(17, 'BOTH', 1, 75.00, 12),
(17, 'BOTH', 2, 80.00, 10),
(17, 'BOTH', 3, 80.00, 8),
(17, 'BOTH', 4, 82.50, 6);

-- S6 PE18 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(18, 'BOTH', 1, 65.00, 12),
(18, 'BOTH', 2, 70.00, 10),
(18, 'BOTH', 3, 70.00, 8);

-- S6 PE19 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(19, 'LEFT',  1, 12.00, 15),
(19, 'RIGHT', 2, 12.00, 15),
(19, 'LEFT',  3, 12.00, 12),
(19, 'RIGHT', 4, 12.00, 12);

-- S6 PE20 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(20, 'BOTH', 1, 75),
(20, 'BOTH', 2, 75),
(20, 'BOTH', 3, 60);

-- S6 PE21 - Calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(21, 'BOTH', 1, 45.00, 20),
(21, 'BOTH', 2, 50.00, 15),
(21, 'BOTH', 3, 50.00, 15);

-- S7 PE22 - Ben press (80kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(22, 'BOTH', 1, 80.00, 10),
(22, 'BOTH', 2, 80.00, 10),
(22, 'BOTH', 3, 85.00, 6);

-- S7 PE23 - Side step launch
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(23, 'BOTH', 1, 10),
(23, 'BOTH', 2, 10),
(23, 'BOTH', 3, 10);

-- S7 PE24 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(24, 'BOTH', 1, 15),
(24, 'BOTH', 2, 15),
(24, 'BOTH', 3, 15);

-- S7 PE25 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(25, 'BOTH', 1, 75),
(25, 'BOTH', 2, 75);

-- S8 PE26 - Ben press (85kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(26, 'BOTH', 1, 80.00, 12),
(26, 'BOTH', 2, 85.00, 10),
(26, 'BOTH', 3, 85.00, 8),
(26, 'BOTH', 4, 87.50, 6);

-- S8 PE27 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(27, 'BOTH', 1, 70.00, 12),
(27, 'BOTH', 2, 75.00, 10),
(27, 'BOTH', 3, 75.00, 8);

-- S8 PE28 - Launches på Bosu
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(28, 'BOTH', 1, 12),
(28, 'BOTH', 2, 12),
(28, 'BOTH', 3, 12);

-- S8 PE29 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(29, 'LEFT',  1, 15.00, 15),
(29, 'RIGHT', 2, 15.00, 15),
(29, 'LEFT',  3, 15.00, 12),
(29, 'RIGHT', 4, 15.00, 12);

-- S8 PE30 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(30, 'BOTH', 1, 90),
(30, 'BOTH', 2, 75),
(30, 'BOTH', 3, 60);

-- S9 PE31 - Ben press (90kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(31, 'BOTH', 1, 85.00, 12),
(31, 'BOTH', 2, 90.00, 10),
(31, 'BOTH', 3, 90.00, 8);

-- S9 PE32 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(32, 'BOTH', 1, 75.00, 12),
(32, 'BOTH', 2, 75.00, 10),
(32, 'BOTH', 3, 80.00, 8);

-- S9 PE33 - Calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(33, 'BOTH', 1, 50.00, 20),
(33, 'BOTH', 2, 50.00, 20),
(33, 'BOTH', 3, 55.00, 15);

-- S9 PE34 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(34, 'BOTH', 1, 15),
(34, 'BOTH', 2, 15),
(34, 'BOTH', 3, 12);

-- S10 PE35 - Ben press (90kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(35, 'BOTH', 1, 90.00, 10),
(35, 'BOTH', 2, 90.00, 10),
(35, 'BOTH', 3, 92.50, 8),
(35, 'BOTH', 4, 95.00, 5);

-- S10 PE36 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(36, 'BOTH', 1, 80.00, 10),
(36, 'BOTH', 2, 80.00, 10),
(36, 'BOTH', 3, 85.00, 8);

-- S10 PE37 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(37, 'LEFT',  1, 17.50, 15),
(37, 'RIGHT', 2, 17.50, 15),
(37, 'LEFT',  3, 17.50, 12),
(37, 'RIGHT', 4, 17.50, 12);

-- S10 PE38 - Power sled
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(38, 'BOTH', 1, 40.00, 10),
(38, 'BOTH', 2, 40.00, 10),
(38, 'BOTH', 3, 45.00, 8);

-- S10 PE39 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(39, 'BOTH', 1, 90),
(39, 'BOTH', 2, 90);

-- S11 PE40 - Ben press (95kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(40, 'BOTH', 1, 90.00, 12),
(40, 'BOTH', 2, 95.00, 10),
(40, 'BOTH', 3, 95.00, 8);

-- S11 PE41 - Side step launch
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(41, 'BOTH', 1, 12),
(41, 'BOTH', 2, 12),
(41, 'BOTH', 3, 10);

-- S11 PE42 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(42, 'BOTH', 1, 15),
(42, 'BOTH', 2, 15),
(42, 'BOTH', 3, 15);

-- S12 PE43 - Ben press (95kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(43, 'BOTH', 1, 95.00, 10),
(43, 'BOTH', 2, 95.00, 10),
(43, 'BOTH', 3, 97.50, 8);

-- S12 PE44 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(44, 'BOTH', 1, 80.00, 12),
(44, 'BOTH', 2, 85.00, 10),
(44, 'BOTH', 3, 85.00, 8);

-- S12 PE45 - Launches på Bosu
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(45, 'BOTH', 1, 12),
(45, 'BOTH', 2, 12),
(45, 'BOTH', 3, 12);

-- S12 PE46 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(46, 'BOTH', 1, 90),
(46, 'BOTH', 2, 90),
(46, 'BOTH', 3, 75);

-- S13 PE47 - Ben press (100kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(47, 'BOTH', 1, 95.00, 12),
(47, 'BOTH', 2, 100.00, 10),
(47, 'BOTH', 3, 100.00, 8),
(47, 'BOTH', 4, 102.50, 5);

-- S13 PE48 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(48, 'BOTH', 1, 85.00, 10),
(48, 'BOTH', 2, 85.00, 10),
(48, 'BOTH', 3, 90.00, 8);

-- S13 PE49 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(49, 'LEFT',  1, 20.00, 15),
(49, 'RIGHT', 2, 20.00, 15),
(49, 'LEFT',  3, 20.00, 12),
(49, 'RIGHT', 4, 20.00, 12);

-- S13 PE50 - Power sled
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(50, 'BOTH', 1, 50.00, 10),
(50, 'BOTH', 2, 50.00, 10),
(50, 'BOTH', 3, 55.00, 8);

-- S13 PE51 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(51, 'BOTH', 1, 15),
(51, 'BOTH', 2, 15),
(51, 'BOTH', 3, 15);

-- S14 PE52 - Ben press (100kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(52, 'BOTH', 1, 100.00, 10),
(52, 'BOTH', 2, 100.00, 10),
(52, 'BOTH', 3, 100.00, 8);

-- S14 PE53 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(53, 'BOTH', 1, 85.00, 12),
(53, 'BOTH', 2, 90.00, 10),
(53, 'BOTH', 3, 90.00, 8);

-- S14 PE54 - Calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(54, 'BOTH', 1, 55.00, 20),
(54, 'BOTH', 2, 55.00, 20),
(54, 'BOTH', 3, 60.00, 15);

-- S14 PE55 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(55, 'BOTH', 1, 90),
(55, 'BOTH', 2, 90);

-- S15 PE56 - Ben press (105kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(56, 'BOTH', 1, 100.00, 12),
(56, 'BOTH', 2, 105.00, 10),
(56, 'BOTH', 3, 105.00, 8),
(56, 'BOTH', 4, 107.50, 5);

-- S15 PE57 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(57, 'BOTH', 1, 90.00, 12),
(57, 'BOTH', 2, 95.00, 10),
(57, 'BOTH', 3, 95.00, 8);

-- S15 PE58 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(58, 'LEFT',  1, 22.50, 15),
(58, 'RIGHT', 2, 22.50, 15),
(58, 'LEFT',  3, 22.50, 12),
(58, 'RIGHT', 4, 22.50, 12);

-- S15 PE59 - Power sled
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(59, 'BOTH', 1, 55.00, 10),
(59, 'BOTH', 2, 55.00, 10),
(59, 'BOTH', 3, 60.00, 8);

-- S15 PE60 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(60, 'BOTH', 1, 105),
(60, 'BOTH', 2, 90),
(60, 'BOTH', 3, 75);

-- S16 PE61 - Ben press (105kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(61, 'BOTH', 1, 105.00, 10),
(61, 'BOTH', 2, 105.00, 10),
(61, 'BOTH', 3, 107.50, 8);

-- S16 PE62 - Launches på Bosu
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(62, 'BOTH', 1, 12),
(62, 'BOTH', 2, 12),
(62, 'BOTH', 3, 12);

-- S16 PE63 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(63, 'BOTH', 1, 15),
(63, 'BOTH', 2, 15),
(63, 'BOTH', 3, 15);

-- S16 PE64 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(64, 'BOTH', 1, 105),
(64, 'BOTH', 2, 90);

-- S17 PE65 - Ben press (110kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(65, 'BOTH', 1, 105.00, 12),
(65, 'BOTH', 2, 110.00, 10),
(65, 'BOTH', 3, 110.00, 8),
(65, 'BOTH', 4, 112.50, 5);

-- S17 PE66 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(66, 'BOTH', 1, 95.00, 12),
(66, 'BOTH', 2, 100.00, 10),
(66, 'BOTH', 3, 100.00, 8);

-- S17 PE67 - Single leg calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(67, 'LEFT',  1, 25.00, 15),
(67, 'RIGHT', 2, 25.00, 15),
(67, 'LEFT',  3, 25.00, 12),
(67, 'RIGHT', 4, 25.00, 12);

-- S17 PE68 - Power sled
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(68, 'BOTH', 1, 60.00, 10),
(68, 'BOTH', 2, 60.00, 10),
(68, 'BOTH', 3, 65.00, 8);

-- S17 PE69 - Planke
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, duration_seconds) VALUES
(69, 'BOTH', 1, 120),
(69, 'BOTH', 2, 90),
(69, 'BOTH', 3, 75);

-- S18 PE70 - Ben press (110kg)
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(70, 'BOTH', 1, 110.00, 10),
(70, 'BOTH', 2, 110.00, 10),
(70, 'BOTH', 3, 112.50, 6);

-- S18 PE71 - Leg Curl
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(71, 'BOTH', 1, 95.00, 12),
(71, 'BOTH', 2, 100.00, 10),
(71, 'BOTH', 3, 100.00, 8);

-- S18 PE72 - Hip raises
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, reps) VALUES
(72, 'BOTH', 1, 15),
(72, 'BOTH', 2, 15),
(72, 'BOTH', 3, 15);

-- S18 PE73 - Calf raise
INSERT INTO performed_set (performed_exercise_id, side_of_body, set_number, weight, reps) VALUES
(73, 'BOTH', 1, 60.00, 20),
(73, 'BOTH', 2, 60.00, 20),
(73, 'BOTH', 3, 65.00, 15);

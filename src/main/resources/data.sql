-- =====================================================
-- DUMMY DATA FOR MEDICAL RECORD APPLICATION
-- =====================================================

-- Roles (for future security implementation)
INSERT IGNORE INTO roles (id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, authority) VALUES (2, 'ROLE_DOCTOR');
INSERT IGNORE INTO roles (id, authority) VALUES (3, 'ROLE_PATIENT');

-- Users (password is BCrypt encoded 'password123')
INSERT IGNORE INTO users (id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) 
VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqJL0kHHnK5T4Ks7x5x5x5x5x5x5x', true, true, true, true);
INSERT IGNORE INTO users (id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) 
VALUES (2, 'doctor1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqJL0kHHnK5T4Ks7x5x5x5x5x5x5x', true, true, true, true);
INSERT IGNORE INTO users (id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) 
VALUES (3, 'patient1', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqJL0kHHnK5T4Ks7x5x5x5x5x5x5x', true, true, true, true);

-- User-Role assignments
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (3, 3);

-- =====================================================
-- DOCTORS (General Practitioners and Specialists)
-- =====================================================
INSERT IGNORE INTO doctors (id, unique_identifier, name, specialty, is_general_practitioner) 
VALUES (1, 'DOC-001', 'Dr. Ivan Petrov', 'General Medicine', true);
INSERT IGNORE INTO doctors (id, unique_identifier, name, specialty, is_general_practitioner) 
VALUES (2, 'DOC-002', 'Dr. Maria Ivanova', 'Cardiology', false);
INSERT IGNORE INTO doctors (id, unique_identifier, name, specialty, is_general_practitioner) 
VALUES (3, 'DOC-003', 'Dr. Georgi Dimitrov', 'General Medicine', true);
INSERT IGNORE INTO doctors (id, unique_identifier, name, specialty, is_general_practitioner) 
VALUES (4, 'DOC-004', 'Dr. Elena Stoyanova', 'Neurology', false);
INSERT IGNORE INTO doctors (id, unique_identifier, name, specialty, is_general_practitioner) 
VALUES (5, 'DOC-005', 'Dr. Nikolay Kolev', 'Orthopedics', false);
INSERT IGNORE INTO doctors (id, unique_identifier, name, specialty, is_general_practitioner) 
VALUES (6, 'DOC-006', 'Dr. Anna Todorova', 'General Medicine', true);

-- =====================================================
-- DIAGNOSES (ICD-10 style codes)
-- =====================================================
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (1, 'J06.9', 'Acute upper respiratory infection', 'Common cold and similar respiratory infections');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (2, 'I10', 'Essential hypertension', 'Primary high blood pressure');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (3, 'E11', 'Type 2 diabetes mellitus', 'Non-insulin-dependent diabetes');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (4, 'M54.5', 'Low back pain', 'Lumbago and lower back problems');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (5, 'J45', 'Asthma', 'Bronchial asthma');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (6, 'K29', 'Gastritis', 'Inflammation of stomach lining');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (7, 'G43', 'Migraine', 'Chronic headache disorder');
INSERT IGNORE INTO diagnoses (id, code, name, description) 
VALUES (8, 'N39.0', 'Urinary tract infection', 'UTI without specified location');

-- =====================================================
-- MEDICINES
-- =====================================================
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (1, 'Paracetamol 500mg', 'Pain reliever and fever reducer', 'Sopharma');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (2, 'Ibuprofen 400mg', 'Anti-inflammatory pain reliever', 'Actavis');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (3, 'Amoxicillin 500mg', 'Antibiotic for bacterial infections', 'GlaxoSmithKline');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (4, 'Lisinopril 10mg', 'ACE inhibitor for hypertension', 'Teva');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (5, 'Metformin 500mg', 'Oral diabetes medication', 'Merck');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (6, 'Omeprazole 20mg', 'Proton pump inhibitor for gastritis', 'AstraZeneca');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (7, 'Salbutamol Inhaler', 'Bronchodilator for asthma', 'GlaxoSmithKline');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (8, 'Diclofenac 50mg', 'NSAID for pain and inflammation', 'Novartis');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (9, 'Sumatriptan 50mg', 'Migraine treatment', 'Pfizer');
INSERT IGNORE INTO medicines (id, name, description, manufacturer) 
VALUES (10, 'Ciprofloxacin 500mg', 'Antibiotic for UTI', 'Bayer');

-- =====================================================
-- PATIENTS (registered with GPs)
-- =====================================================
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (1, 'Petar Georgiev', '8501011234', true, '2025-12-15', 1);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (2, 'Stefka Marinova', '9003152345', true, '2025-11-20', 1);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (3, 'Dimitar Todorov', '7512283456', false, '2025-06-10', 3);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (4, 'Ivanka Petrova', '8207194567', true, '2026-01-05', 3);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (5, 'Yordan Ivanov', '9509085678', true, '2025-10-30', 6);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (6, 'Kalina Stoyanova', '8811226789', true, '2026-01-20', 6);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (7, 'Boris Nikolov', '6505157890', false, '2025-05-15', 1);
INSERT IGNORE INTO patients (id, name, egn, health_insurance_paid, last_insurance_payment_date, general_practitioner_id) 
VALUES (8, 'Radka Dimitrova', '9201038901', true, '2025-12-01', 3);

-- =====================================================
-- EXAMINATIONS (patient visits to doctors)
-- =====================================================
-- Patient 1 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (1, 1, 1, '2025-11-15', 1);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (2, 1, 2, '2025-12-01', 2);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (3, 1, 1, '2026-01-10', 1);

-- Patient 2 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (4, 2, 1, '2025-10-20', 6);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (5, 2, 4, '2025-11-05', 7);

-- Patient 3 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (6, 3, 3, '2025-09-15', 3);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (7, 3, 3, '2025-12-15', 3);

-- Patient 4 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (8, 4, 3, '2025-11-25', 4);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (9, 4, 5, '2025-12-10', 4);

-- Patient 5 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (10, 5, 6, '2025-10-05', 5);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (11, 5, 6, '2026-01-15', 5);

-- Patient 6 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (12, 6, 6, '2025-11-10', 8);
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (13, 6, 1, '2026-01-20', 1);

-- Patient 7 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (14, 7, 1, '2025-08-20', 2);

-- Patient 8 visits
INSERT IGNORE INTO examinations (id, patient_id, doctor_id, examination_date, diagnosis_id) 
VALUES (15, 8, 3, '2025-12-05', 1);

-- =====================================================
-- PRESCRIPTIONS (medicines prescribed during examinations)
-- =====================================================
-- Examination 1 prescriptions (cold)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (1, 1, 1, '500mg', '3 times daily', 5, 'Take with food');
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (2, 1, 2, '400mg', '2 times daily', 3, 'Take after meals');

-- Examination 2 prescriptions (hypertension)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (3, 2, 4, '10mg', 'Once daily', 30, 'Take in the morning');

-- Examination 4 prescriptions (gastritis)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (4, 4, 6, '20mg', 'Once daily', 14, 'Take before breakfast');

-- Examination 5 prescriptions (migraine)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (5, 5, 9, '50mg', 'As needed', 10, 'Take at onset of migraine');
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (6, 5, 1, '500mg', 'As needed', 10, 'Alternative for mild headache');

-- Examination 6 prescriptions (diabetes)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (7, 6, 5, '500mg', '2 times daily', 30, 'Take with meals');

-- Examination 8 prescriptions (back pain)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (8, 8, 8, '50mg', '3 times daily', 7, 'Take after meals, avoid alcohol');
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (9, 8, 2, '400mg', '2 times daily', 5, 'Take with food');

-- Examination 10 prescriptions (asthma)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (10, 10, 7, '2 puffs', 'As needed', 30, 'Use when experiencing breathing difficulty');

-- Examination 12 prescriptions (UTI)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (11, 12, 10, '500mg', '2 times daily', 7, 'Complete full course');

-- Examination 13 prescriptions (cold)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (12, 13, 1, '500mg', '3 times daily', 5, 'Take with water');

-- Examination 15 prescriptions (cold)
INSERT IGNORE INTO prescriptions (id, examination_id, medicine_id, dosage, frequency, duration_days, instructions) 
VALUES (13, 15, 3, '500mg', '3 times daily', 7, 'Take with food, complete full course');

-- =====================================================
-- SICK LEAVES (issued for some examinations)
-- =====================================================
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (1, 1, '2025-11-15', 5, '2025-11-15');
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (2, 5, '2025-11-05', 3, '2025-11-05');
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (3, 8, '2025-11-25', 7, '2025-11-25');
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (4, 9, '2025-12-10', 14, '2025-12-10');
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (5, 12, '2025-11-10', 5, '2025-11-10');
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (6, 13, '2026-01-20', 3, '2026-01-20');
INSERT IGNORE INTO sick_leaves (id, examination_id, start_date, duration_days, issue_date) 
VALUES (7, 15, '2025-12-05', 5, '2025-12-05');

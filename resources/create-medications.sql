CREATE TABLE IF NOT EXISTS MEDICATIONS (
    medication_code INT PRIMARY KEY,
    medication_name VARCHAR(100) NOT NULL,
    medication_comment VARCHAR(255)
);
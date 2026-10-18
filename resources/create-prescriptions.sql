CREATE TABLE IF NOT EXISTS PRESCRIPTIONS (
    presc_id INT PRIMARY KEY,
    presc_ref_pat VARCHAR(13) NOT NULL,
    presc_code INT NOT NULL,
    presc_days INT NOT NULL,
    FOREIGN KEY (presc_ref_pat) REFERENCES PATIENTS(pat_num_HC),
    FOREIGN KEY (presc_code) REFERENCES MEDICATIONS(medication_code)
);
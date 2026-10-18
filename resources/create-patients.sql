CREATE TABLE IF NOT EXISTS PATIENTS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pat_num_HC VARCHAR(13) NOT NULL UNIQUE,
    pat_lastname VARCHAR(100) NOT NULL,
    pat_firstname VARCHAR(100) NOT NULL,
    pat_address VARCHAR(255),
    pat_tel VARCHAR(20),
    pat_insurance_id INT,
    pat_subscription_date DATE,
    FOREIGN KEY (pat_insurance_id) REFERENCES INSURANCES(insurance_id)
);
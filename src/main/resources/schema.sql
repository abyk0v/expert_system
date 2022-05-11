CREATE TABLE IF NOT EXISTS patient (
   id INT AUTO_INCREMENT  PRIMARY KEY,
   name VARCHAR(250) NOT NULL,
   surname VARCHAR(250),
   diagnosis INT,
   age INT
);

CREATE TABLE IF NOT EXISTS diagnosis (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(250)
--     две связи многие ко многим
);

CREATE TABLE IF NOT EXISTS symptom (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    description VARCHAR(250) NOT NULL
--     две связи многие ко многим
);

-- CREATE TABLE IF NOT EXISTS patient_diagnosis (
--     id_p INT,
--     id_d INT,
--     FOREIGN KEY(id_p) REFERENCES patient,
--     FOREIGN KEY(id_d) REFERENCES diagnosis
-- );

CREATE TABLE IF NOT EXISTS patient_symptom (
    id_p INT,
    id_s INT,
    FOREIGN KEY(id_p) REFERENCES patient,
    FOREIGN KEY(id_s) REFERENCES symptom
);

CREATE TABLE IF NOT EXISTS diagnosis_symptom (
    id_d INT,
    id_s INT,
    FOREIGN KEY(id_d) REFERENCES diagnosis,
    FOREIGN KEY(id_s) REFERENCES symptom
);
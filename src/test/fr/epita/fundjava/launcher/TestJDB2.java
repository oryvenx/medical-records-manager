package fr.epita.fundjava.launcher;

import fr.epita.fundjava.dao.PatientDAO;
import fr.epita.fundjava.datamodel.Patient;
import fr.epita.fundjava.reader.PatientReader;

import java.util.List;

public class TestJDB2 {

    public static void main(String[] args) {
        // The in-memory database is empty at startup: create the PATIENTS table first
        TestJDB1.test();

        PatientDAO patientDAO = new PatientDAO();

        // 1. Read the patients from the CSV file
        List<Patient> patients = new PatientReader().readAll();
        System.out.println("Loaded " + patients.size() + " patients from the CSV file");

        // 2. Save them in the database with create()
        for (Patient patient : patients) {
            patientDAO.create(patient);
        }
        System.out.println("Created " + patients.size() + " patients in the database");

        // 3. Search one precise patient by first name and last name (no id)
        Patient criteria = new Patient(null, "Solti", "Anna", null, null, 0, null);
        List<Patient> found = patientDAO.search(criteria);
        System.out.println("Found: " + found);

        Patient anna = found.get(0);

        // 4. Update the patient that was found
        anna.setAddress("Nantes");
        patientDAO.update(anna);
        System.out.println("Updated: " + patientDAO.search(criteria));

        // 5. Delete this patient
        patientDAO.delete(anna);
        System.out.println("Deleted. Search now returns: " + patientDAO.search(criteria));
        System.out.println("Patients remaining: " + patientDAO.search(new Patient(null, null, null, null, null, 0, null)).size());
    }
}
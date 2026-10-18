package fr.epita.fundjava.launcher;

import fr.epita.fundjava.datamodel.Patient;
import fr.epita.fundjava.reader.PatientReader;
import fr.epita.fundjava.service.PatientBLService;

import java.util.List;

public class TestBLI1 {

    public static void main(String[] args) {
        // Load all patients from the CSV file
        List<Patient> patients = new PatientReader().readAll();

        Patient sixthPatient = patients.get(5);

        Integer seniority = PatientBLService.computeSeniority(sixthPatient);
        System.out.println("Patient " + sixthPatient.getFirstName() + " " + sixthPatient.getLastName()
                + " has a seniority of " + seniority + " year(s)");
    }
}
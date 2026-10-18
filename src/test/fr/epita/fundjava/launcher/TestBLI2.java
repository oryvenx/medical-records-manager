package fr.epita.fundjava.launcher;

import fr.epita.fundjava.datamodel.Patient;
import fr.epita.fundjava.reader.PatientReader;
import fr.epita.fundjava.service.PatientBLService;

import java.util.List;
import java.util.Map;

public class TestBLI2 {

    public static void main(String[] args) {
        // load the patients from the CSV file
        List<Patient> patients = new PatientReader().readAll();

        // compute the seniority of each patient
        Map<String, Integer> seniorityByPatient = PatientBLService.computeSeniorityByPatient(patients);

        System.out.println(seniorityByPatient);
    }
}
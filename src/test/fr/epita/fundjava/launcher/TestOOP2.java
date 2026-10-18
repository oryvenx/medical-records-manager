package fr.epita.fundjava.launcher;

import fr.epita.fundjava.datamodel.Insurance;
import fr.epita.fundjava.datamodel.Patient;
import fr.epita.fundjava.reader.InsuranceReader;
import fr.epita.fundjava.reader.PatientReader;

import java.util.List;

public class TestOOP2 {

    public static void main(String[] args) {
        // Load both datasets from the CSV files
        List<Patient> patients = new PatientReader().readAll();
        List<Insurance> insurances = new InsuranceReader().readAll();

        // Display the lists (uses the toString() of each class)
        System.out.println("=== Patients ===");
        patients.forEach(System.out::println);

        System.out.println("=== Insurances ===");
        insurances.forEach(System.out::println);
    }
}
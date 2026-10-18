package fr.epita.fundjava.service;

import fr.epita.fundjava.datamodel.Patient;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Patient Business Logic Service: computations based on patient data.
 * Nothing computed here is persisted, it is only projected for a potential UI.
 */
public class PatientBLService {

    /**
     * Computes the seniority of a patient, in full years, as of today.
     * Example: subscribed on 01/06/2017, today 18/10/2026 -> 9 years.
     */
    public static Integer computeSeniority(Patient patient) {
        LocalDate subscriptionDate = patient.getSubscriptionDate();
        return (int) ChronoUnit.YEARS.between(subscriptionDate, LocalDate.now());
    }

    /**
     * Computes the seniority of every patient.
     * The key is the patient identifier (pat_num_HC), the value is his seniority in years.
     * A LinkedHashMap keeps the reading order of the file.
     */
    public static Map<String, Integer> computeSeniorityByPatient(List<Patient> patients) {
        Map<String, Integer> seniorityByPatient = new LinkedHashMap<>();
        for (Patient patient : patients) {
            // Reuse the single-patient method to avoid duplicating the computation
            seniorityByPatient.put(patient.getNumHC(), computeSeniority(patient));
        }
        return seniorityByPatient;
    }
}
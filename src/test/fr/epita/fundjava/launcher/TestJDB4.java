package fr.epita.fundjava.launcher;

import fr.epita.fundjava.dao.ConnectionProvider;
import fr.epita.fundjava.dao.InsuranceDAO;
import fr.epita.fundjava.dao.MedicationDAO;
import fr.epita.fundjava.dao.PatientDAO;
import fr.epita.fundjava.dao.PrescriptionDAO;
import fr.epita.fundjava.reader.InsuranceReader;
import fr.epita.fundjava.reader.MedicationReader;
import fr.epita.fundjava.reader.PatientReader;
import fr.epita.fundjava.reader.PrescriptionReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDB4 {

    public static void main(String[] args) throws SQLException {
        // 1. Create the 4 tables
        TestJDB3.initDatabase();

        // 2. Load the CSV data, parents first (foreign keys)
        InsuranceDAO insuranceDAO = new InsuranceDAO();
        new InsuranceReader().readAll().forEach(insuranceDAO::create);

        MedicationDAO medicationDAO = new MedicationDAO();
        new MedicationReader().readAll().forEach(medicationDAO::create);

        PatientDAO patientDAO = new PatientDAO();
        new PatientReader().readAll().forEach(patientDAO::create);

        PrescriptionDAO prescriptionDAO = new PrescriptionDAO();
        new PrescriptionReader().readAll().forEach(prescriptionDAO::create);

        // 3. Each patient with his treatment and the number of days
        //    PATIENTS -> PRESCRIPTIONS through presc_ref_pat
        //    PRESCRIPTIONS -> MEDICATIONS through presc_code
        String sql = "SELECT p.pat_firstname, p.pat_lastname, m.medication_name, pr.presc_days "
                + "FROM PRESCRIPTIONS pr "
                + "JOIN PATIENTS p ON pr.presc_ref_pat = p.pat_num_HC "
                + "JOIN MEDICATIONS m ON pr.presc_code = m.medication_code "
                + "ORDER BY pr.presc_id";

        try (Statement statement = ConnectionProvider.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("pat_firstname") + " " + rs.getString("pat_lastname")
                        + " -> " + rs.getString("medication_name")
                        + " for " + rs.getInt("presc_days") + " day(s)");
            }
        }
    }
}
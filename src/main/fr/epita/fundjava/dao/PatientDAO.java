package fr.epita.fundjava.dao;

import fr.epita.fundjava.datamodel.Patient;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the PATIENTS table.
 * The patient is identified by his health-care number (pat_num_HC).
 */
public class PatientDAO {

    /** Inserts a patient in the database. */
    public void create(Patient patient) {
        String sql = "INSERT INTO PATIENTS (pat_num_HC, pat_lastname, pat_firstname, pat_address, "
                + "pat_tel, pat_insurance_id, pat_subscription_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = ConnectionProvider.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, patient.getNumHC());
                ps.setString(2, patient.getLastName());
                ps.setString(3, patient.getFirstName());
                ps.setString(4, patient.getAddress());
                ps.setString(5, patient.getTel());
                ps.setInt(6, patient.getInsuranceId());
                ps.setDate(7, Date.valueOf(patient.getSubscriptionDate()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot create patient " + patient.getNumHC(), e);
        }
    }

    /** Updates every column of the patient identified by his numHC. */
    public void update(Patient patient) {
        String sql = "UPDATE PATIENTS SET pat_lastname = ?, pat_firstname = ?, pat_address = ?, "
                + "pat_tel = ?, pat_insurance_id = ?, pat_subscription_date = ? WHERE pat_num_HC = ?";
        try {
            Connection connection = ConnectionProvider.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, patient.getLastName());
                ps.setString(2, patient.getFirstName());
                ps.setString(3, patient.getAddress());
                ps.setString(4, patient.getTel());
                ps.setInt(5, patient.getInsuranceId());
                ps.setDate(6, Date.valueOf(patient.getSubscriptionDate()));
                ps.setString(7, patient.getNumHC());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot update patient " + patient.getNumHC(), e);
        }
    }

    /** Deletes the patient identified by his numHC. */
    public void delete(Patient patient) {
        String sql = "DELETE FROM PATIENTS WHERE pat_num_HC = ?";
        try {
            Connection connection = ConnectionProvider.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, patient.getNumHC());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot delete patient " + patient.getNumHC(), e);
        }
    }

    /**
     * Searches patients using a criteria template: every non-null text field
     * (numHC, lastName, firstName, address, tel) becomes an equality condition.
     * A criteria with only null fields returns all the patients.
     */
    public List<Patient> search(Patient criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM PATIENTS WHERE 1 = 1");
        List<String> parameters = new ArrayList<>();

        addCriterion(sql, parameters, "pat_num_HC", criteria.getNumHC());
        addCriterion(sql, parameters, "pat_lastname", criteria.getLastName());
        addCriterion(sql, parameters, "pat_firstname", criteria.getFirstName());
        addCriterion(sql, parameters, "pat_address", criteria.getAddress());
        addCriterion(sql, parameters, "pat_tel", criteria.getTel());

        List<Patient> result = new ArrayList<>();
        try {
            Connection connection = ConnectionProvider.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    ps.setString(i + 1, parameters.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(mapRow(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot search patients", e);
        }
        return result;
    }

    /** Adds " AND column = ?" to the query when the value is not null. */
    private void addCriterion(StringBuilder sql, List<String> parameters, String column, String value) {
        if (value != null) {
            sql.append(" AND ").append(column).append(" = ?");
            parameters.add(value);
        }
    }

    /** Converts the current row of the ResultSet into a Patient. */
    private Patient mapRow(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("pat_num_HC"),
                rs.getString("pat_lastname"),
                rs.getString("pat_firstname"),
                rs.getString("pat_address"),
                rs.getString("pat_tel"),
                rs.getInt("pat_insurance_id"),
                rs.getDate("pat_subscription_date").toLocalDate());
    }
}
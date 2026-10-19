package fr.epita.fundjava.dao;

import fr.epita.fundjava.datamodel.Patient;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/** DAO for the PATIENTS table. A patient is identified by his pat_num_HC. */
public class PatientDAO extends AbstractDAO<Patient> {

    @Override
    protected String getTableName() {
        return "PATIENTS";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO PATIENTS (pat_num_HC, pat_lastname, pat_firstname, pat_address, "
                + "pat_tel, pat_insurance_id, pat_subscription_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void fillInsert(PreparedStatement ps, Patient p) throws SQLException {
        ps.setString(1, p.getNumHC());
        ps.setString(2, p.getLastName());
        ps.setString(3, p.getFirstName());
        ps.setString(4, p.getAddress());
        ps.setString(5, p.getTel());
        ps.setInt(6, p.getInsuranceId());
        ps.setDate(7, Date.valueOf(p.getSubscriptionDate()));
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE PATIENTS SET pat_lastname = ?, pat_firstname = ?, pat_address = ?, "
                + "pat_tel = ?, pat_insurance_id = ?, pat_subscription_date = ? WHERE pat_num_HC = ?";
    }

    @Override
    protected void fillUpdate(PreparedStatement ps, Patient p) throws SQLException {
        ps.setString(1, p.getLastName());
        ps.setString(2, p.getFirstName());
        ps.setString(3, p.getAddress());
        ps.setString(4, p.getTel());
        ps.setInt(5, p.getInsuranceId());
        ps.setDate(6, Date.valueOf(p.getSubscriptionDate()));
        ps.setString(7, p.getNumHC());
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM PATIENTS WHERE pat_num_HC = ?";
    }

    @Override
    protected void fillDelete(PreparedStatement ps, Patient p) throws SQLException {
        ps.setString(1, p.getNumHC());
    }

    @Override
    protected Map<String, Object> getSearchCriteria(Patient c) {
        Map<String, Object> criteria = criteria();
        addIfSet(criteria, "pat_num_HC", c.getNumHC());
        addIfSet(criteria, "pat_lastname", c.getLastName());
        addIfSet(criteria, "pat_firstname", c.getFirstName());
        addIfSet(criteria, "pat_address", c.getAddress());
        addIfSet(criteria, "pat_tel", c.getTel());
        addIfPositive(criteria, "pat_insurance_id", c.getInsuranceId());
        return criteria;
    }

    @Override
    protected Patient mapRow(ResultSet rs) throws SQLException {
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
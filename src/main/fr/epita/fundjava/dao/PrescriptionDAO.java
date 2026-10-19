package fr.epita.fundjava.dao;

import fr.epita.fundjava.datamodel.Prescription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PrescriptionDAO extends AbstractDAO<Prescription> {

    @Override
    protected String getTableName() {
        return "PRESCRIPTIONS";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO PRESCRIPTIONS (presc_id, presc_ref_pat, presc_code, presc_days) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected void fillInsert(PreparedStatement ps, Prescription p) throws SQLException {
        ps.setInt(1, p.getId());
        ps.setString(2, p.getPatientNumHC());
        ps.setInt(3, p.getMedicationCode());
        ps.setInt(4, p.getDays());
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE PRESCRIPTIONS SET presc_ref_pat = ?, presc_code = ?, presc_days = ? WHERE presc_id = ?";
    }

    @Override
    protected void fillUpdate(PreparedStatement ps, Prescription p) throws SQLException {
        ps.setString(1, p.getPatientNumHC());
        ps.setInt(2, p.getMedicationCode());
        ps.setInt(3, p.getDays());
        ps.setInt(4, p.getId());
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM PRESCRIPTIONS WHERE presc_id = ?";
    }

    @Override
    protected void fillDelete(PreparedStatement ps, Prescription p) throws SQLException {
        ps.setInt(1, p.getId());
    }

    @Override
    protected Map<String, Object> getSearchCriteria(Prescription c) {
        Map<String, Object> criteria = criteria();
        addIfPositive(criteria, "presc_id", c.getId());
        addIfSet(criteria, "presc_ref_pat", c.getPatientNumHC());
        addIfPositive(criteria, "presc_code", c.getMedicationCode());
        addIfPositive(criteria, "presc_days", c.getDays());
        return criteria;
    }

    @Override
    protected Prescription mapRow(ResultSet rs) throws SQLException {
        return new Prescription(
                rs.getInt("presc_id"),
                rs.getString("presc_ref_pat"),
                rs.getInt("presc_code"),
                rs.getInt("presc_days"));
    }
}
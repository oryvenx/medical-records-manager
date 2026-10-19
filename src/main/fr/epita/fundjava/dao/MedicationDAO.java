package fr.epita.fundjava.dao;

import fr.epita.fundjava.datamodel.Medication;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MedicationDAO extends AbstractDAO<Medication> {

    @Override
    protected String getTableName() {
        return "MEDICATIONS";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO MEDICATIONS (medication_code, medication_name, medication_comment) VALUES (?, ?, ?)";
    }

    @Override
    protected void fillInsert(PreparedStatement ps, Medication m) throws SQLException {
        ps.setInt(1, m.getCode());
        ps.setString(2, m.getName());
        ps.setString(3, m.getComment());
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE MEDICATIONS SET medication_name = ?, medication_comment = ? WHERE medication_code = ?";
    }

    @Override
    protected void fillUpdate(PreparedStatement ps, Medication m) throws SQLException {
        ps.setString(1, m.getName());
        ps.setString(2, m.getComment());
        ps.setInt(3, m.getCode());
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM MEDICATIONS WHERE medication_code = ?";
    }

    @Override
    protected void fillDelete(PreparedStatement ps, Medication m) throws SQLException {
        ps.setInt(1, m.getCode());
    }

    @Override
    protected Map<String, Object> getSearchCriteria(Medication c) {
        Map<String, Object> criteria = criteria();
        addIfPositive(criteria, "medication_code", c.getCode());
        addIfSet(criteria, "medication_name", c.getName());
        addIfSet(criteria, "medication_comment", c.getComment());
        return criteria;
    }

    @Override
    protected Medication mapRow(ResultSet rs) throws SQLException {
        return new Medication(
                rs.getInt("medication_code"),
                rs.getString("medication_name"),
                rs.getString("medication_comment"));
    }
}
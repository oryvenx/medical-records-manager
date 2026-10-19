package fr.epita.fundjava.dao;

import fr.epita.fundjava.datamodel.Insurance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class InsuranceDAO extends AbstractDAO<Insurance> {

    @Override
    protected String getTableName() {
        return "INSURANCES";
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO INSURANCES (insurance_id, insurance_name) VALUES (?, ?)";
    }

    @Override
    protected void fillInsert(PreparedStatement ps, Insurance i) throws SQLException {
        ps.setInt(1, i.getId());
        ps.setString(2, i.getName());
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE INSURANCES SET insurance_name = ? WHERE insurance_id = ?";
    }

    @Override
    protected void fillUpdate(PreparedStatement ps, Insurance i) throws SQLException {
        ps.setString(1, i.getName());
        ps.setInt(2, i.getId());
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM INSURANCES WHERE insurance_id = ?";
    }

    @Override
    protected void fillDelete(PreparedStatement ps, Insurance i) throws SQLException {
        ps.setInt(1, i.getId());
    }

    @Override
    protected Map<String, Object> getSearchCriteria(Insurance c) {
        Map<String, Object> criteria = criteria();
        addIfPositive(criteria, "insurance_id", c.getId());
        addIfSet(criteria, "insurance_name", c.getName());
        return criteria;
    }

    @Override
    protected Insurance mapRow(ResultSet rs) throws SQLException {
        return new Insurance(rs.getInt("insurance_id"), rs.getString("insurance_name"));
    }
}
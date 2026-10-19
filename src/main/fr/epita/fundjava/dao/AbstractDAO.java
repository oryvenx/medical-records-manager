package fr.epita.fundjava.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO holding the JDBC plumbing shared by every table.
 * Subclasses only provide their SQL, how to fill the parameters and how to map a row.
 */
public abstract class AbstractDAO<T> {

    /** Fills the parameters of a prepared statement. */
    @FunctionalInterface
    private interface StatementFiller {
        void fill(PreparedStatement ps) throws SQLException;
    }

    // ----- To be provided by each concrete DAO -----

    protected abstract String getTableName();

    protected abstract String getInsertSql();
    protected abstract void fillInsert(PreparedStatement ps, T entity) throws SQLException;

    protected abstract String getUpdateSql();
    protected abstract void fillUpdate(PreparedStatement ps, T entity) throws SQLException;

    protected abstract String getDeleteSql();
    protected abstract void fillDelete(PreparedStatement ps, T entity) throws SQLException;

    /** Column -> value for every search criterion that is set in the template object. */
    protected abstract Map<String, Object> getSearchCriteria(T criteria);

    /** Converts the current row of the ResultSet into an entity. */
    protected abstract T mapRow(ResultSet rs) throws SQLException;

    // ----- The 4 actions, written once for all DAOs -----

    public void create(T entity) {
        execute(getInsertSql(), ps -> fillInsert(ps, entity));
    }

    public void update(T entity) {
        execute(getUpdateSql(), ps -> fillUpdate(ps, entity));
    }

    public void delete(T entity) {
        execute(getDeleteSql(), ps -> fillDelete(ps, entity));
    }

    /**
     * Searches with a criteria template: each criterion that is set becomes an
     * equality condition. A template with nothing set returns every row.
     */
    public List<T> search(T criteria) {
        Map<String, Object> conditions = getSearchCriteria(criteria);

        StringBuilder sql = new StringBuilder("SELECT * FROM ")
                .append(getTableName()).append(" WHERE 1 = 1");
        for (String column : conditions.keySet()) {
            sql.append(" AND ").append(column).append(" = ?");
        }

        List<T> result = new ArrayList<>();
        try {
            Connection connection = ConnectionProvider.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
                int index = 1;
                for (Object value : conditions.values()) {
                    ps.setObject(index++, value);
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(mapRow(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Search failed on " + getTableName(), e);
        }
        return result;
    }

    // ----- Helpers -----

    private void execute(String sql, StatementFiller filler) {
        try {
            Connection connection = ConnectionProvider.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                filler.fill(ps);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Data access failed on " + getTableName(), e);
        }
    }

    /** Builds the criteria map: adds the criterion only when the value is not null. */
    protected static Map<String, Object> criteria() {
        return new LinkedHashMap<>();
    }

    protected static void addIfSet(Map<String, Object> criteria, String column, Object value) {
        if (value != null) {
            criteria.put(column, value);
        }
    }

    /** For int fields, which cannot be null: a value of 0 means "not set". */
    protected static void addIfPositive(Map<String, Object> criteria, String column, int value) {
        if (value > 0) {
            criteria.put(column, value);
        }
    }
}
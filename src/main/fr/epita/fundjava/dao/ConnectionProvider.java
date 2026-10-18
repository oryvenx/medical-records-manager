package fr.epita.fundjava.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Single access point to the in-memory H2 database.
 * The same connection is reused so the in-memory data is never lost.
 */
public class ConnectionProvider {

    // DB_CLOSE_DELAY=-1 keeps the database alive as long as the JVM runs
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection connection;

    private ConnectionProvider() {
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
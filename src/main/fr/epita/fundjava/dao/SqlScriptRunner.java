package fr.epita.fundjava.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/** Executes a .sql file stored in the resources folder. */
public class SqlScriptRunner {

    public static void run(String resourceName) {
        try (InputStream in = SqlScriptRunner.class.getResourceAsStream("/" + resourceName)) {
            if (in == null) {
                throw new IllegalStateException("Script not found on classpath: " + resourceName);
            }
            String script = new String(in.readAllBytes(), StandardCharsets.UTF_8);

            Connection connection = ConnectionProvider.getConnection();
            try (Statement statement = connection.createStatement()) {
                // One statement per ';' (our scripts contain no ';' inside values)
                for (String sql : script.split(";")) {
                    if (!sql.isBlank()) {
                        statement.execute(sql);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new IllegalStateException("Cannot run script " + resourceName, e);
        }
    }
}
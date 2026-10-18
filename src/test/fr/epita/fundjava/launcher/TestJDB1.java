package fr.epita.fundjava.launcher;

import fr.epita.fundjava.dao.ConnectionProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDB1 {

    public static void test() {
        try {
            Connection connection = ConnectionProvider.getConnection();

            // Create the PATIENTS table (id is generated automatically)
            String createTable = "CREATE TABLE IF NOT EXISTS PATIENTS ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "pat_num_HC VARCHAR(13) NOT NULL UNIQUE, "
                    + "pat_lastname VARCHAR(100) NOT NULL, "
                    + "pat_firstname VARCHAR(100) NOT NULL, "
                    + "pat_address VARCHAR(255), "
                    + "pat_tel VARCHAR(20), "
                    + "pat_insurance_id INT, "
                    + "pat_subscription_date DATE"
                    + ")";

            try (Statement statement = connection.createStatement()) {
                statement.execute(createTable);
                System.out.println("Table PATIENTS created");

                // the table exists and is empty
                try (ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM PATIENTS")) {
                    rs.next();
                    System.out.println("Rows in PATIENTS: " + rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("JDB1 failed", e);
        }
    }
}
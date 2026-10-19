package fr.epita.fundjava.launcher;

import fr.epita.fundjava.dao.ConnectionProvider;
import fr.epita.fundjava.dao.SqlScriptRunner;
import fr.epita.fundjava.reader.MedicationReader;
import fr.epita.fundjava.reader.PrescriptionReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDB3 {

    /** Creates the 4 tables. The order matters because of the foreign keys. */
    public static void initDatabase() {
        SqlScriptRunner.run("create-insurances.sql");
        SqlScriptRunner.run("create-medications.sql");
        SqlScriptRunner.run("create-patients.sql");
        SqlScriptRunner.run("create-prescriptions.sql");
    }

    public static void main(String[] args) throws SQLException {
        initDatabase();

        // Check that the 4 tables now exist
        try (Statement statement = ConnectionProvider.getConnection().createStatement();
             ResultSet rs = statement.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES "
                     + "WHERE TABLE_SCHEMA = 'PUBLIC' ORDER BY TABLE_NAME")) {
            System.out.println("Tables created:");
            while (rs.next()) {
                System.out.println(" - " + rs.getString(1));
            }
        }

        // Check that the 2 new readers work
        System.out.println("Medications: " + new MedicationReader().readAll());
        System.out.println("Prescriptions: " + new PrescriptionReader().readAll());
    }
}
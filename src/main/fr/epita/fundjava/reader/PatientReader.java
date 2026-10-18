package fr.epita.fundjava.reader;

import fr.epita.fundjava.datamodel.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientReader extends CsvReader<Patient> {

    // Dates in the CSV look like 01/10/2010 (day/month/year)
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PatientReader() {
        super("patients.csv");
    }

    @Override
    protected Patient parseLine(String[] fields) {
        return new Patient(
                clean(fields[0]),                               // pat_num_HC
                clean(fields[1]),                               // lastname
                clean(fields[2]),                               // firstname
                clean(fields[3]),                               // address
                clean(fields[4]),                               // tel
                Integer.parseInt(clean(fields[5])),             // insurance id
                LocalDate.parse(clean(fields[6]), DATE_FORMAT)  // subscription date
        );
    }
}
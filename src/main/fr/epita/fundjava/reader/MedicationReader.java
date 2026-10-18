package fr.epita.fundjava.reader;

import fr.epita.fundjava.datamodel.Medication;

public class MedicationReader extends CsvReader<Medication> {

    public MedicationReader() {
        super("medications.csv");
    }

    @Override
    protected Medication parseLine(String[] fields) {
        return new Medication(
                Integer.parseInt(clean(fields[0])), // medication_code
                clean(fields[1]),                   // medication_name
                clean(fields[2]));                  // medication_comment
    }
}
package fr.epita.fundjava.reader;

import fr.epita.fundjava.datamodel.Prescription;

public class PrescriptionReader extends CsvReader<Prescription> {

    public PrescriptionReader() {
        super("prescriptions.csv");
    }

    @Override
    protected Prescription parseLine(String[] fields) {
        return new Prescription(
                Integer.parseInt(clean(fields[0])), // presc_id
                clean(fields[1]),                   // presc_ref_pat
                Integer.parseInt(clean(fields[2])), // presc_code
                Integer.parseInt(clean(fields[3]))); // presc_days
    }
}
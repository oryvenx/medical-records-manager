package fr.epita.fundjava.reader;

import fr.epita.fundjava.datamodel.Insurance;

public class InsuranceReader extends CsvReader<Insurance> {

    public InsuranceReader() {
        super("insurances.csv");
    }

    @Override
    protected Insurance parseLine(String[] fields) {
        return new Insurance(Integer.parseInt(clean(fields[0])), clean(fields[1]));
    }
}
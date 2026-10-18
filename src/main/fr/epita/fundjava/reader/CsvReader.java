package fr.epita.fundjava.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic reader for ';' separated CSV files stored in the resources folder.
 * Subclasses only describe how to convert one line into an object of type T.
 */
public abstract class CsvReader<T> {

    private static final String SEPARATOR = ";";

    private final String resourceName;

    protected CsvReader(String resourceName) {
        this.resourceName = resourceName;
    }

    /** Reads the whole file and returns one object per data line (header is skipped). */
    public List<T> readAll() {
        try (InputStream in = CsvReader.class.getResourceAsStream("/" + resourceName)) {
            if (in == null) {
                throw new IllegalStateException("Resource not found on classpath: " + resourceName
                        + " (is the 'resources' folder marked as Resources Root?)");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            List<T> result = new ArrayList<>();
            reader.readLine(); // skip the header line
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue; // ignore empty lines
                }
                result.add(parseLine(line.split(SEPARATOR)));
            }
            return result;
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read " + resourceName, e);
        }
    }

    /** Converts the fields of one CSV line into an object. */
    protected abstract T parseLine(String[] fields);

    /** Removes surrounding spaces and double quotes, e.g. "0106060606" -> 0106060606. */
    protected static String clean(String field) {
        return field.replace("\"", "").trim();
    }
}
package fr.epita.fundjava.datamodel;

public class Prescription {

    private int id;                 // presc_id
    private String patientNumHC;    // presc_ref_pat (references PATIENTS.pat_num_HC)
    private int medicationCode;     // presc_code (references MEDICATIONS.medication_code)
    private int days;               // presc_days

    public Prescription(int id, String patientNumHC, int medicationCode, int days) {
        this.id = id;
        this.patientNumHC = patientNumHC;
        this.medicationCode = medicationCode;
        this.days = days;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatientNumHC() { return patientNumHC; }
    public void setPatientNumHC(String patientNumHC) { this.patientNumHC = patientNumHC; }

    public int getMedicationCode() { return medicationCode; }
    public void setMedicationCode(int medicationCode) { this.medicationCode = medicationCode; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    @Override
    public String toString() {
        return "Prescription{id=" + id + ", patientNumHC='" + patientNumHC
                + "', medicationCode=" + medicationCode + ", days=" + days + "}";
    }
}
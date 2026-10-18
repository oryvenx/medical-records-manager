package fr.epita.fundjava.datamodel;

public class Medication {

    private int code;        // medication_code
    private String name;     // medication_name
    private String comment;  // medication_comment

    public Medication(int code, String name, String comment) {
        this.code = code;
        this.name = name;
        this.comment = comment;
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    @Override
    public String toString() {
        return "Medication{code=" + code + ", name='" + name + "', comment='" + comment + "'}";
    }
}
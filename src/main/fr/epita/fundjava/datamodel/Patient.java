package fr.epita.fundjava.datamodel;
import java.time.LocalDate;

public class Patient {

    private String numHC;            // pat_num_HC (health-care number)
    private String lastName;         // pat_lastname
    private String firstName;        // pat_firstname
    private String address;          // pat_address
    private String tel;              // pat_tel
    private int insuranceId;         // pat_insurance_id
    private LocalDate subscriptionDate; // pat_subscription_date

    public Patient(String numHC, String lastName, String firstName, String address, String tel, int insuranceId, LocalDate subscriptionDate) {
        this.numHC = numHC;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.tel = tel;
        this.insuranceId = insuranceId;
        this.subscriptionDate = subscriptionDate;
    }

    public String getNumHC() {
        return numHC;
    }

    public void setNumHC(String numHC) {
        this.numHC = numHC;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "numHC='" + numHC + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", insuranceId=" + insuranceId +
                ", subscriptionDate=" + subscriptionDate +
                '}';
    }
}

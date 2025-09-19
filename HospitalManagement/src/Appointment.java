public class Appointment {
    private String id;
    private Patient patient;
    private Doctor doctor;

    public Appointment(String id, Patient patient, Doctor doctor) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return id + " | Patient: " + patient + " | Doctor: " + doctor;
    }
}

package Review;

public class Doctor {
    private String name;
    private String specialty;

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    public String toString() {
        return "Doctor: " + name + " [Specialty: " + specialty + "]";
    }
}

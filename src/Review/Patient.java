package Review;

public class Patient {
    private String name;
    private int age;

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "Patient: " + name + " (" + age + " tuoi)";
    }
}

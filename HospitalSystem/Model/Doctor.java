package HospitalSystem.Model;

public class Doctor extends Employee{
    
    private String specialization; 

    public Doctor() {
        super();
        this.options = new Option[] {}; 
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

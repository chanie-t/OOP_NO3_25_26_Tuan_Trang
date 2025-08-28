package HospitalSystem.Model;

import java.util.ArrayList;

public class Patient extends User{

    private String bloodGroup;
    private ArrayList<Operation> operations;
    private ArrayList<Report> reports;
    private ArrayList<Operation> unpaidOperations;
    private ArrayList<Report> unpaidReports;

    public Patient() {
        super();
        this.options = new Option[] {};
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    } 
}

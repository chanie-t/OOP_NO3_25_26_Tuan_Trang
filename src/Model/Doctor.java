package Model;

import View.AddNewOperation;
import View.AddNewReport;
import View.ChangePassword;
import View.ViewPatientData;
import View.ViewPatientOperations;

public class Doctor extends Employee {

    private String specialization;

    public Doctor() {
        super();
        this.options = new Option[] {
                new AddNewOperation(),
                new AddNewReport(),
                new ViewPatientData(),
                new ViewPatientOperations(),
                new ChangePassword()
        };
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public int getJob() {
        return 0;
    }

    @Override
    public String getJobToString() {
        return "Doctor";
    }
}

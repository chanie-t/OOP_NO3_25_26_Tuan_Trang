package Model;

import View.AddNewEmployee;
import View.ViewEmployees;

public class Receptionist extends Employee {

    public Receptionist() {
        super();
        this.options = new Option[] {
            new AddNewEmployee(),
            new ViewEmployees()
        };

    }

    @Override
    public int getJob() {
        return 2;
    }

}

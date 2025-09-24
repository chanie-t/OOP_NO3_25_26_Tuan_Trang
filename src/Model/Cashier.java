package Model;

import View.ChangePassword;

public class Cashier extends Employee {

    public Cashier() {
        super();
        this.options = new Option[] {
                new ChangePassword()
        };
    }

    @Override
    public int getJob() {
        return 1;
    }

    @Override
    public String getJobToString() {
        return "Cashier";
    }
}

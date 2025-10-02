package Model;

import View.AddNewReceipt;
import View.ChangePassword;

public class Cashier extends Employee {

    public Cashier() {
        super();
        this.options = new Option[] {
                new AddNewReceipt(), // thêm hoá đơn mới
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

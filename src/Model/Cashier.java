package Model;

import java.util.Scanner;

public class Cashier extends Employee{

    public Cashier() {
        super();
        this.options = new Option[] {};
    }

    @Override
    public int getJob() {
        return 1;
    }

    @Override
    public void showList(Scanner s, Database database) {

    }
}

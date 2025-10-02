package View;

import java.util.ArrayList;
import java.util.Scanner;

import Controller.ReadPatientOperations;
import Model.Database;
import Model.Operation;
import Model.Option;
import Model.Patient;
import Model.User;

public class ViewPatientUnpaidOperations implements Option {

    @Override
    public void operation(Scanner s, Database database, User u) {
        ArrayList<Operation> operations = new ReadPatientOperations((Patient) u, database).getOperations();

        for (int i = 0; i < operations.size(); i++) { // duyệt qua từng ca phẫu thuật
            if (operations.get(i).isPaid())           // nếu đã thanh toán
                operations.remove(i);                 // xoá khỏi danh sách
        }

        System.out.println("-------------------------------------");
        for (Operation o : operations) { // duyệt qua các ca chưa thanh toán
            System.out.println("ID:\t\t" + o.getID());
            System.out.println("Doctor:\t\t" + o.getDoctor().getName());
            System.out.println("Patient:\t" + o.getPatient().getName());
            System.out.println("Date time:\t" + o.getDateTime());
            System.out.println("Paid:\t\t" + o.isPaid());
            System.out.println("Diagnosis:\t" + o.getDiagnosis());
            System.out.println("-------------------------------------");
        }
    }

    @Override
    public String getName() {
        return "View My Unpaid Operation";
    }

}

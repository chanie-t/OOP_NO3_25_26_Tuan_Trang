package View;

import java.util.Scanner;

import Controller.CreateReceipt;
import Controller.ReadPatient;
import Model.Cashier;
import Model.Database;
import Model.Option;
import Model.Patient;
import Model.Receipt;
import Model.User;

public class AddNewReceipt implements Option {

    @Override
    public void operation(Scanner s, Database database, User u) {
        Receipt r = new Receipt();
        r.setCashier((Cashier) u);
        System.out.println("Select type:\n0. Operation\n1. Report");
        r.setType(s.nextInt());

        System.out.println("Enter patient ID (-1 to show all patients): ");
        int patientID = s.nextInt();
        while (patientID < 0) {
            new ViewAllPatients().operation(s, database, u);
            System.out.println("Enter patient ID (-1 to show all patients): ");
            patientID = s.nextInt();
        }
        Patient patient = new ReadPatient(patientID, database).getPatient(); // lấy thông tin bệnh nhân từ db
        r.setPatient(patient); // gắn bệnh nhân cho hoá đơn

        int itemID;
        if (r.getType() == 0) {
            System.out.println("Enter Operation ID (-1 to show all operations): ");
            itemID = s.nextInt();
            while (itemID < 0) { // danh sách báo cáo chưa thanh toán
                new ViewPatientUnpaidOperations().operation(s, database, patient);
                System.out.println("Enter Operation ID (-1 to show all operations): ");
                itemID = s.nextInt();
            }
        } else if (r.getType() == 1) {
            System.out.println("Enter Report ID (-1 to show all reports): ");
            itemID = s.nextInt();
            while (itemID < 0) {
                new ViewPatientUnpaidReports().operation(s, database, patient);
                System.out.println("Enter Report ID (-1 to show all reports): ");
                itemID = s.nextInt();
            }
        } else {
            System.out.println("Invalid type");
            return;
        }
        r.setItemID(itemID); // gắn id mục cần thanh toán cho hoá đơn

        System.out.println("Enter amount (double): ");
        r.setAmount(s.nextDouble()); // nhập số tiền và gắn cho hoá đơn

        System.out.println("Are you sure that you want to continue?");
        System.out.println("Cashier:\t" + r.getCashier().getName());
        System.out.println("Patient:\t" + r.getPatient().getName());
        System.out.println("Type:\t\t" + r.getTypeToString());
        System.out.println("Amount:\t\t" + r.getAmount());
        System.out.println("Item ID:\t" + r.getItemID());
        System.out.println("These data cannot be edited later\n1. Continue");

        if (s.nextInt() == 1) {
            if (new CreateReceipt(r, database).isCreated()) { // tạo hoá đơn và cập nhật trạng thái thanh toán
                System.out.println("Receipt paid succesfully");
            }
        }
    }

    @Override
    public String getName() {
        return "Add New Receipt";
    }

}

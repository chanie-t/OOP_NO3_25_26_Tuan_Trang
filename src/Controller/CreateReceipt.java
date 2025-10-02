package Controller;

import java.sql.SQLException;

import Model.Database;
import Model.Receipt;

public class CreateReceipt {

    private Receipt r; //lưu thông tin hoá đơn
    private Database database;

    public CreateReceipt(Receipt r, Database database) {
        this.r = r;
        this.database = database;
    }

    public boolean isCreated() { //ktra tạo hoá đơn có thành công ko
        boolean created = false;
        String update = "";
        if (r.getType() == 0) { // cập nhập trạng thái đã thanh toán cho ca phẫu thuật
            // Operation
            update = "UPDATE `operations` SET `Paid`='true' WHERE `ID` = " + r.getItemID() + " ;";
        } else if (r.getType() == 1) { // cập nhập trạng thái đã thanh toán cho báo cáo
            // Report
            update = "UPDATE `reports` SET `Paid`='true' WHERE `ID` = " + r.getItemID() + " ;";
        }
        String insert = "INSERT INTO `receipts`(`Cashier`, `Patient`, `Amount`,"
                + " `Type`, `ItemID`) VALUES ('" + r.getCashier().getID() + "','"
                + r.getPatient().getID() + "','" + r.getAmount() + "','" + r.getType() + "','"
                + r.getItemID() + "') ;";
        try {
            database.getStatement().execute(insert);
            database.getStatement().execute(update);
            created = true;
        } catch (SQLException e) {
            e.printStackTrace();
            created = false;
        }
        return created;
    }

}

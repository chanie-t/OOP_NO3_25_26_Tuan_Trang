package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Database;
import Model.Doctor;
import Model.Operation;
import Model.Patient;

// Lấy danh sách ca phẩu thuật của một bệnh nhân
public class ReadPatientOperations {

    private ArrayList<Operation> operations;

    public ReadPatientOperations(Patient p, Database database) { 
        String select = "SELECT * FROM `operations` WHERE `Patient` = " + p.getID() + " ;"; // lấy ca phẫu thuật của bệnh nhân có ID
        operations = new ArrayList<>(); // lưu các ca phẫu thuật
        ArrayList<Integer> docotrsIDs = new ArrayList<>(); //lưu tạm các ID của bsi thực hiện ca phẫu thuật
        try {
            ResultSet rs = database.getStatement().executeQuery(select);
            while (rs.next()) {
                Operation o = new Operation();
                o.setID(rs.getInt("ID"));
                docotrsIDs.add(rs.getInt("Doctor"));
                o.setDateTime(rs.getString("DateTime"));
                o.setPatient(p);
                o.setPaid(Boolean.parseBoolean(rs.getString("Paid")));
                o.setDiagnosis(rs.getString("Diagnosis"));
                operations.add(o);
            }
            for (int i = 0; i < operations.size(); i++) {
                operations.get(i).setDoctor((Doctor) new ReadEmployee(docotrsIDs.get(i), database).getEmployee());
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }
}
